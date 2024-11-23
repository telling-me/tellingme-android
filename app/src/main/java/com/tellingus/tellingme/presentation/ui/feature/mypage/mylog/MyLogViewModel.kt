package com.tellingus.tellingme.presentation.ui.feature.mypage.mylog

import androidx.lifecycle.viewModelScope
import com.tellingus.tellingme.data.network.adapter.onFailure
import com.tellingus.tellingme.data.network.adapter.onNetworkError
import com.tellingus.tellingme.data.network.adapter.onSuccess
import com.tellingus.tellingme.domain.usecase.GetAnswerListUseCase
import com.tellingus.tellingme.presentation.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MyLogViewModel @Inject constructor(
    private val getAnswerListUseCase: GetAnswerListUseCase
) : BaseViewModel<MyLogContract.State, MyLogContract.Event, MyLogContract.Effect>(
    initialState = MyLogContract.State()
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

    override fun reduceState(event: MyLogContract.Event) {
        TODO("Not yet implemented")
    }

}