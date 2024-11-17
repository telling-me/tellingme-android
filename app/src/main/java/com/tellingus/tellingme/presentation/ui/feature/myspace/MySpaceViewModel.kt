package com.tellingus.tellingme.presentation.ui.feature.myspace

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.tellingus.tellingme.data.network.adapter.onFailure
import com.tellingus.tellingme.data.network.adapter.onNetworkError
import com.tellingus.tellingme.data.network.adapter.onSuccess
import com.tellingus.tellingme.domain.repository.DataStoreRepository
import com.tellingus.tellingme.domain.repository.MySpaceRepository
import com.tellingus.tellingme.domain.usecase.GetAnswerListUseCase
import com.tellingus.tellingme.presentation.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MySpaceViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val getAnswerListUseCase: GetAnswerListUseCase
): BaseViewModel<MySpaceContract.State, MySpaceContract.Event, MySpaceContract.Effect>(
    initialState = MySpaceContract.State()
) {

    init {
        viewModelScope.launch {
            getAnswerListUseCase()
                .onSuccess {
                    updateState(
                        currentState.copy(
                            answerList = it.data.reversed(),
                            isAnsweredDateList = it.data.map {
                                LocalDate.of(it.date[0], it.date[1], it.date[2])
                            }.reversed()
                        )
                    )
                }
                .onFailure { s, i -> }
                .onNetworkError {}
        }
    }

    override fun reduceState(event: MySpaceContract.Event) {
        when(event) {
            is MySpaceContract.Event.OnClickTodayButton -> {
                postEffect(MySpaceContract.Effect.ScrollToToday)
            }

            is MySpaceContract.Event.UpdateCurrentDate -> {
                updateState(
                    currentState.copy(
                        currentDate = currentState.currentDate.plusMonths(event.swipe)
                    )
                )
            }

            is MySpaceContract.Event.OnClickCalendarDate -> {
                // 해당 날짜에 답변 있는지 없는지 확인해야댐
                if (currentState.isAnsweredDateList.contains(LocalDate.of(event.year, event.month, event.day))) {
                    updateState(
                        currentState.copy(
                            initialAnswerPageIndex = currentState.isAnsweredDateList.indexOf(LocalDate.of(event.year, event.month, event.day))
                        )
                    )
                    postEffect(MySpaceContract.Effect.ShowAnswerListPagerDialog)
                } else {
                    postEffect(MySpaceContract.Effect.ShowAnswerEmptyDialog)
                }
            }
        }
    }


}
