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
            levelDto = LevelDto(
                level = 0,
                currentExp = 0,
                requiredExp = 0
            ),
            daysToLevelUp = 0
        ),

        val recordCount : Int = 0,

        val cheeseBalance: Int = 0,
    ) : UiState

    sealed class Event : UiEvent {

    }

    sealed class Effect : UiEffect {

    }
}