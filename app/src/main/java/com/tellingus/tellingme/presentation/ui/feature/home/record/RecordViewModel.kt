package com.tellingus.tellingme.presentation.ui.feature.home.record

import androidx.lifecycle.viewModelScope
import com.tellingus.tellingme.R
import com.tellingus.tellingme.data.model.home.AnswerRequest
import com.tellingus.tellingme.data.network.adapter.onSuccess
import com.tellingus.tellingme.domain.usecase.WriteAnswerUseCase
import com.tellingus.tellingme.presentation.ui.common.base.BaseViewModel
import com.tellingus.tellingme.util.getToday
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val writeAnswerUseCase: WriteAnswerUseCase
) : BaseViewModel<RecordContract.State, RecordContract.Event, RecordContract.Effect>(
    initialState = RecordContract.State()
) {

    init {
        updateState(
            currentState.copy(
                today = getToday()
            )
        )
    }

    override fun reduceState(event: RecordContract.Event) {
        when(event) {
            is RecordContract.Event.OnClickRecordButton -> {
                if (currentState.answer.length < 2 && currentState.selectedEmotion == -1) {
                    postEffect(RecordContract.Effect.ShowToastMessage(text = "감정과 글 모두 기록해주세요.", icon = R.drawable.icon_warn))
                } else if (currentState.answer.length < 2) {
                    postEffect(RecordContract.Effect.ShowToastMessage(text = "2글자 이상 작성해주세요.", icon = R.drawable.icon_warn))
                } else if (currentState.selectedEmotion == -1) {
                    postEffect(RecordContract.Effect.ShowToastMessage(text = "감정을 선택해주세요.", icon = R.drawable.icon_warn))
                } else {
                    postEffect(RecordContract.Effect.ShowRecordDialog)
                }
            }

            is RecordContract.Event.OnClickOpenSwitch -> {
                updateState(currentState.copy(isPublic = !currentState.isPublic))
            }

            is RecordContract.Event.RecordAnswer -> {
                viewModelScope.launch {
                    writeAnswerUseCase(
                        answerRequest = AnswerRequest(
                            content = currentState.answer,
                            emotion = currentState.selectedEmotion + 1,
                            date = currentState.today.replace(".", "-"),
                            isPublic = currentState.isPublic,
                            isSpare = false
                        )
                    ).onSuccess {
                        updateState(currentState.copy(isCompleteWriteAnswer = true))
                    }
                }
            }

            else -> {}
        }
    }

    fun updateSelectedEmotion(emotion: Int) {
        updateState(currentState.copy(selectedEmotion = emotion))
    }

    fun updateAnswer(answer: String) {
        updateState(currentState.copy(answer = answer))
    }

    fun getNotice() {

    }
}