package com.tellingus.tellingme.presentation.ui.feature.home.record

import com.tellingus.tellingme.data.model.home.QuestionData
import com.tellingus.tellingme.data.model.home.Answer
import com.tellingus.tellingme.presentation.ui.common.base.UiEffect
import com.tellingus.tellingme.presentation.ui.common.base.UiEvent
import com.tellingus.tellingme.presentation.ui.common.base.UiState

class RecordContract {
    data class State(
        val selectedEmotion: Int = -1,
        val answer: String = "",
        val today: String = "",
        val isPublic: Boolean = true,
        val cheeseCount: Int = 0,
        val usableEmotionList: List<Int> = emptyList(),

        val isAnswered: Boolean = false,
        val questionResponse: QuestionData = QuestionData(),
        val answerResponse: Answer = Answer()
    ) : UiState

    sealed class Event : UiEvent {
        object OnClickRecordButton : Event()
        object OnClickOpenSwitch : Event()
        object RecordAnswer: Event()
    }

    sealed class Effect : UiEffect {
        object ShowRecordDialog: Effect()
        object CompletePurchaseEmotion: Effect()
        object CompleteRecord: Effect()
        object CompleteUpdate: Effect()
        data class ShowToastMessage(
            val text: String,
            val icon: Int
        ): Effect()

        data class MoveToMoreScreen(
            val date: String
        ): Effect()
    }
}
