package com.tellingus.tellingme.presentation.ui.feature.myspace

import com.tellingus.tellingme.data.model.home.Question
import com.tellingus.tellingme.data.model.myspace.Answer
import com.tellingus.tellingme.presentation.ui.common.base.UiEffect
import com.tellingus.tellingme.presentation.ui.common.base.UiEvent
import com.tellingus.tellingme.presentation.ui.common.base.UiState
import java.time.LocalDate

class MySpaceContract {
    data class State(
        val isLoading: Boolean = false,
        val today: LocalDate = LocalDate.now(),
        val currentDate: LocalDate = LocalDate.now(),
        val answerList: List<Answer> = emptyList(),
        val isAnsweredDateList: List<LocalDate> = emptyList(),
        val initialAnswerPageIndex: Int = 0,
        val todayTitle: String = "",
        val todayPhrase: String = "",
    ): UiState

    sealed class Event: UiEvent {
        object OnClickTodayButton: Event()
        data class UpdateCurrentDate(
            val swipe: Long
        ): Event()

        data class OnClickCalendarDate(
            val year: Int,
            val month: Int,
            val day: Int
        ): Event()

        data class OnClickDatePickButton(
            val year: Int,
            val month: Int
        ): Event()
    }

    sealed class Effect: UiEffect {
        object ScrollToToday: Effect()
        data class ScrollToDate(
            val year: Int,
            val month: Int
        ): Effect()
        object ShowAnswerListPagerDialog: Effect()
        object ShowAnswerEmptyDialog: Effect()
    }
}
