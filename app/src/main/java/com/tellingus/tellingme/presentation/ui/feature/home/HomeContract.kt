package com.tellingus.tellingme.presentation.ui.feature.home

import com.tellingus.tellingme.data.model.home.HomeData
import com.tellingus.tellingme.data.model.home.HomeResponse
import com.tellingus.tellingme.presentation.ui.common.base.UiEffect
import com.tellingus.tellingme.presentation.ui.common.base.UiEvent
import com.tellingus.tellingme.presentation.ui.common.base.UiState

class HomeContract {
    data class State(
        val isLoading: Boolean = false,
        val todayQuestionCardInfo: TodayQuestionCardInfo = TodayQuestionCardInfo(),
        val mainData: HomeData? = null,
    ) : UiState {
        data class TodayQuestionCardInfo(val title: String = "", val content: String = "")
    }

    sealed class Event : UiEvent {
        object RecordButtonClicked : Event()
        object MoreButtonClicked : Event()
    }

    sealed class Effect : UiEffect {
        data class MoveToRecordScreen(
            val questionId: Int
        ) : Effect()

        data class MoveToMoreScreen(
            val date: String
        ) : Effect()
    }
}
