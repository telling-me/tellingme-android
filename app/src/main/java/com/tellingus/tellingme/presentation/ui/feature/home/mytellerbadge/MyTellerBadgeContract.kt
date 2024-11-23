package com.tellingus.tellingme.presentation.ui.feature.home.mytellerbadge

import com.tellingus.tellingme.data.model.user.UserBadgeData
import com.tellingus.tellingme.presentation.ui.common.base.UiEffect
import com.tellingus.tellingme.presentation.ui.common.base.UiEvent
import com.tellingus.tellingme.presentation.ui.common.base.UiState

class MyTellerBadgeContract {

    data class State(
        val userBadgeList: List<UserBadgeData> = emptyList()
    ) : UiState

    sealed class Event : UiEvent {

    }

    sealed class Effect : UiEffect {

    }
}