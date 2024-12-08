package com.tellingus.tellingme.presentation.ui.feature.mypage

import com.tellingus.tellingme.data.model.myspace.Answer
import com.tellingus.tellingme.data.model.oauth.User
import com.tellingus.tellingme.presentation.ui.common.base.UiEffect
import com.tellingus.tellingme.presentation.ui.common.base.UiEvent
import com.tellingus.tellingme.presentation.ui.common.base.UiState

class MyPageContract {
    data class State(
        val isLoading: Boolean = false,
        val userInfo: User = User(),

        val nickname: String = "",
        val badgeCode: String = "",
        val cheeseBalance: Int = 0,
        val badgeCount: Int = 0,
        val answerCount: Int = 0,
        val premium: Boolean = false,
        val days_to_level_up: Int = 0,
        val level: Int = 0,
        val current_exp: Int = 0,
        val required_exp: Int = 0,
        val answerList: List<Answer> = emptyList(),

        val allowNotification: Boolean = false,
    ) : UiState

    sealed class Event : UiEvent {
        object OnClickSignOutButton : Event()
        data class OnToggleNotificationSwitch(
            val notificationStatus: Boolean
        ) : Event()
    }

    sealed class Effect : UiEffect {
        object MoveToLoginScreen : Effect()
    }
}
