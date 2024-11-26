package com.tellingus.tellingme.presentation.ui.feature.home.tellercard

import com.tellingus.tellingme.data.model.home.Badge
import com.tellingus.tellingme.data.model.home.Color
import com.tellingus.tellingme.data.model.home.LevelDto
import com.tellingus.tellingme.data.model.home.LevelInfo
import com.tellingus.tellingme.data.model.home.MobileTellerCardData
import com.tellingus.tellingme.data.model.home.TellerCard
import com.tellingus.tellingme.data.model.home.UserInfo
import com.tellingus.tellingme.presentation.ui.common.base.UiEffect
import com.tellingus.tellingme.presentation.ui.common.base.UiEvent
import com.tellingus.tellingme.presentation.ui.common.base.UiState

class TellerCardContract {
    data class State(
        val badges: List<Badge> = emptyList(),
        val colors: List<Color> = emptyList(),
        val userInfo: UserInfo = UserInfo(
            nickname = "",
            cheeseBalance = 0,
            tellerCard = TellerCard(
                badgeCode = "",
                badgeName = "",
                badgeMiddleName = "",
                colorCode = ""
            )
        ),
        val levelInfo: LevelInfo = LevelInfo(
            level_dto = LevelDto(
                level = 0,
                current_exp = 0,
                required_exp = 0
            ),
            days_to_level_up = 0
        ),

        val cheeseBalance: Int = 0,
    ) : UiState

    sealed class Event : UiEvent {

    }

    sealed class Effect : UiEffect {

    }
}