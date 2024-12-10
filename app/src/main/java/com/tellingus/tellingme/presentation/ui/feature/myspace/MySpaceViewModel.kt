package com.tellingus.tellingme.presentation.ui.feature.myspace

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.tellingus.tellingme.data.model.home.UpdateAnswerRequest
import com.tellingus.tellingme.data.network.adapter.onFailure
import com.tellingus.tellingme.data.network.adapter.onNetworkError
import com.tellingus.tellingme.data.network.adapter.onSuccess
import com.tellingus.tellingme.domain.repository.DataStoreRepository
import com.tellingus.tellingme.domain.usecase.DeleteAnswerUseCase
import com.tellingus.tellingme.domain.usecase.GetAnswerListUseCase
import com.tellingus.tellingme.domain.usecase.GetQuestionUseCase
import com.tellingus.tellingme.domain.usecase.UpdateAnswerUseCase
import com.tellingus.tellingme.domain.usecase.user.GetCheeseUseCase
import com.tellingus.tellingme.presentation.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MySpaceViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val getAnswerListUseCase: GetAnswerListUseCase,
    private val getQuestionUseCase: GetQuestionUseCase,
    private val deleteAnswerUseCase: DeleteAnswerUseCase,
    private val updateAnswerUseCase: UpdateAnswerUseCase
): BaseViewModel<MySpaceContract.State, MySpaceContract.Event, MySpaceContract.Effect>(
    initialState = MySpaceContract.State()
) {

    init {
        val today: LocalDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formattedDate = today.format(formatter)

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

    fun updateAnswer(
        date: String,
        content: String,
        isPublic: Boolean
    ) {
        viewModelScope.launch {
            updateAnswerUseCase(UpdateAnswerRequest(date, content, isPublic)).onSuccess {
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
            }
        }
    }

    fun deleteAnswer(date: String) {
        viewModelScope.launch {
            deleteAnswerUseCase(date).onSuccess {
                Log.d("taag", "1")
                getAnswerListUseCase().onSuccess {
                    Log.d("taag", "2")
                    updateState(
                        currentState.copy(
                            answerList = it.data.reversed(),
                            isAnsweredDateList = it.data.map {
                                LocalDate.of(it.date[0], it.date[1], it.date[2])
                            }.reversed()
                        )
                    )
                    Log.d("taag", it.data.toString())
                }.onFailure { s, i ->
                    Log.d("taag", s)
                }.onNetworkError {
                    Log.d("taag", "네트웤 에러")
                }
            }.onFailure {s, i ->
                Log.d("taag", s)
            }.onNetworkError {
                Log.d("taag", it.message.toString())
            }
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

            is MySpaceContract.Event.OnClickDatePickButton -> {
                postEffect(
                    MySpaceContract.Effect.ScrollToDate(
                        year = event.year,
                        month = event.month
                    )
                )
            }
        }
    }


}
