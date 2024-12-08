package com.tellingus.tellingme.presentation.ui.feature.home.record

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.tellingus.tellingme.R
import com.tellingus.tellingme.data.model.home.AnswerRequest
import com.tellingus.tellingme.data.network.adapter.onSuccess
import com.tellingus.tellingme.domain.usecase.GetUsableEmotionUseCase
import com.tellingus.tellingme.domain.usecase.PurchaseEmotionUseCase
import com.tellingus.tellingme.domain.usecase.WriteAnswerUseCase
import com.tellingus.tellingme.domain.usecase.user.GetCheeseUseCase
import com.tellingus.tellingme.presentation.ui.common.base.BaseViewModel
import com.tellingus.tellingme.util.getToday
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val writeAnswerUseCase: WriteAnswerUseCase,
    private val getCheeseUseCase: GetCheeseUseCase,
    private val getUsableEmotionUseCase: GetUsableEmotionUseCase,
    private val purchaseEmotionUseCase: PurchaseEmotionUseCase
) : BaseViewModel<RecordContract.State, RecordContract.Event, RecordContract.Effect>(
    initialState = RecordContract.State()
) {

    init {
        updateState(
            currentState.copy(
                today = getToday()
            )
        )

        viewModelScope.launch {
            getCheeseUseCase().onSuccess {
                updateState(currentState.copy(cheeseCount = it.data.cheeseBalance))
            }

            getUsableEmotionUseCase().onSuccess {
                updateState(currentState.copy(usableEmotionList = it.data.emotionList))
            }
        }
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

    fun purchaseEmotion(index: Int) {
        var code = ""
        viewModelScope.launch {
            when(index) {
                7 -> code = "PD_EM_EXCITED"
                8 -> code = "PD_EM_FUN"
                9 -> code = "PD_EM_RELAXED"
                10 -> code = "PD_EM_APATHETIC"
                11 -> code = "PD_EM_LONELY"
                12 -> code = "PD_EM_COMPLEX"
            }
            purchaseEmotionUseCase(code).onSuccess {
                getUsableEmotionUseCase().onSuccess {
                    updateState(currentState.copy(usableEmotionList = it.data.emotionList))
                    postEffect(RecordContract.Effect.CompletePurchaseEmotion)
                    postEffect(RecordContract.Effect.ShowToastMessage(text = "감정이 오픈되었어요!", icon = R.drawable.icon_unlock))
                }
            }
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