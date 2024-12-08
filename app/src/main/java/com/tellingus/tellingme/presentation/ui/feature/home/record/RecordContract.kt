package com.tellingus.tellingme.presentation.ui.feature.home.record

import com.tellingus.tellingme.presentation.ui.common.base.UiEffect
import com.tellingus.tellingme.presentation.ui.common.base.UiEvent
import com.tellingus.tellingme.presentation.ui.common.base.UiState

class RecordContract {
    data class State(
        val selectedEmotion: Int = -1,
        val isCompleteWriteAnswer: Boolean = false,
        val answer: String = "",
        val today: String = "",
        val isPublic: Boolean = true,
        val cheeseCount: Int = 0,
        val usableEmotionList: List<Int> = emptyList(),

    ) : UiState

    sealed class Event : UiEvent {
        object OnClickRecordButton : Event()
        object OnClickOpenSwitch : Event()
        object RecordAnswer: Event()
    }

    sealed class Effect : UiEffect {
        object ShowRecordDialog: Effect()
        data class ShowToastMessage(
            val text: String,
            val icon: Int
        ): Effect()

        data class MoveToMoreScreen(
            val date: String
        ): Effect()
    }
}
