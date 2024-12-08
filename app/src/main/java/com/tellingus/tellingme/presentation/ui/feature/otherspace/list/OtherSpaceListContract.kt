package com.tellingus.tellingme.presentation.ui.feature.otherspace.list

import com.tellingus.tellingme.data.model.home.QuestionData
import com.tellingus.tellingme.data.model.otherspace.CommunicationListData
import com.tellingus.tellingme.presentation.ui.common.base.UiEffect
import com.tellingus.tellingme.presentation.ui.common.base.UiEvent
import com.tellingus.tellingme.presentation.ui.common.base.UiState

class OtherSpaceListContract {

    data class State(
        val communicationListData: CommunicationListData,
        val questionData: QuestionData,
        val currentPage: Int = 0, // 현재 페이지 0부터 시작
        val isLastPage: Boolean = false // 마지막 페이지인지 여부

    ) : UiState

    sealed class Event : UiEvent {
        data class OnClickRecently(
            val date: String,
        ) : Event()

        data class OnClickRelative(val date:String) : Event()
        data class OnClickEmpathy(val date: String) : Event()
        data class OnClickHeart(val answerId: Int) : Event()


    }

    sealed class Effect : UiEffect {
        object ScrollToTop : Effect()
    }
}