package com.tellingus.tellingme.presentation.ui.feature.home

import com.tellingus.tellingme.data.model.home.CommunicationData
import com.tellingus.tellingme.data.model.home.HomeData
import com.tellingus.tellingme.data.model.home.HomeResponse
import com.tellingus.tellingme.presentation.ui.common.base.UiEffect
import com.tellingus.tellingme.presentation.ui.common.base.UiEvent
import com.tellingus.tellingme.presentation.ui.common.base.UiState
import com.tellingus.tellingme.presentation.ui.feature.otherspace.list.OtherSpaceListContract

class HomeContract {
    data class State(
        val isLoading: Boolean = false,
        val todayQuestionCardInfo: TodayQuestionCardInfo = TodayQuestionCardInfo(),
        val recordCount: Int = 0,
        val todayAnswerCount: Int = 0,
        val communicationList: List<CommunicationData> = listOf(),
        val unreadNoticeStatus: Boolean = false,
        val questionTitle: String = "",
        val questionPhrase: String = "",
        val userNickname: String = "",
        val userLevel: Int = 0,
        val userExp: Int = 0,

        val denyPushNoti: Boolean = true,
        val requiredExp: Int = 0,
        val daysToLevelUp: Int = 0,
        val todayAnswer: Boolean = false,

        val badgeCode: String = "",
        val colorCode: String = ""
    ) : UiState {
        data class TodayQuestionCardInfo(val title: String = "", val content: String = "")
    }

    sealed class Event : UiEvent {
        object RecordButtonClicked : Event()
        object MoreButtonClicked : Event()

        data class OnClickHeart(val answerId: Int) : Event()
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
