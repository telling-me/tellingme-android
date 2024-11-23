package com.tellingus.tellingme.presentation.ui.feature.mypage.mylog

import com.tellingus.tellingme.data.model.myspace.Answer
import com.tellingus.tellingme.presentation.ui.common.base.UiEffect
import com.tellingus.tellingme.presentation.ui.common.base.UiEvent
import com.tellingus.tellingme.presentation.ui.common.base.UiState
import java.time.LocalDate

class MyLogContract {
    data class State(
        val isLoading: Boolean = false,
        val answerList: List<Answer> = emptyList(),
        val isAnsweredDateList: List<LocalDate> = emptyList(),
    ) : UiState

    sealed class Event : UiEvent {
    }

    sealed class Effect : UiEffect {
    }
}
