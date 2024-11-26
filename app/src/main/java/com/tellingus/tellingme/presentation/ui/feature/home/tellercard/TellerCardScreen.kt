package com.tellingus.tellingme.presentation.ui.feature.home.tellercard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tellingus.tellingme.R
import com.tellingus.tellingme.presentation.ui.common.component.appbar.BasicAppBar
import com.tellingus.tellingme.presentation.ui.common.component.badge.CheeseBadge
import com.tellingus.tellingme.presentation.ui.common.component.chip.ActionChip
import com.tellingus.tellingme.presentation.ui.common.component.layout.MainLayout
import com.tellingus.tellingme.presentation.ui.common.component.widget.LevelSection
import com.tellingus.tellingme.presentation.ui.common.component.widget.ProfileCardResponse
import com.tellingus.tellingme.presentation.ui.common.navigation.HomeDestinations
import com.tellingus.tellingme.presentation.ui.theme.Background100

@Composable
fun TellerCardScreen(
    navController: NavController,
    viewModel: TellerCardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MainLayout(header = { TellerScreenHeader(navController, uiState) },
        content = { TellerScreenContent(navController, uiState) })
}


@Composable
fun TellerScreenContent(navController: NavController, uiState: TellerCardContract.State) {
    val badges = uiState.badges
    val colors = uiState.colors
    val levelInfo = uiState.levelInfo
    val userInfo = uiState.userInfo

    Column {
        Box(modifier = Modifier.padding(20.dp)) {
            MyTellerCard(
                navController = navController,
                profileCardResponse = ProfileCardResponse(
                    nickname = userInfo.nickname,
                    description = userInfo.tellerCard.badgeName,
                    level = "LV. ${levelInfo.level_dto.level}",
                    consecutiveWritingDate = "${levelInfo.days_to_level_up}일째",
                    profileIcon = "R.drawable.icon_profile_sample",
                    badgeCode = userInfo.tellerCard.badgeCode,
                    colorCode = userInfo.tellerCard.colorCode,
                )
            )
        }
        Box(modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 16.dp)) {
            LevelSection(
                level = levelInfo.level_dto.level, percent = levelInfo.level_dto.required_exp,
                levelDescription = "연속 ${levelInfo.days_to_level_up}일만 작성하면 LV.${levelInfo.level_dto.level + 1} 달성!",
            )
        }
        Column(modifier = Modifier.padding(top = 40.dp)) {
            TellerBadgeList(badges = badges)
            Column(
                modifier = Modifier
                    .padding(top = 14.dp, bottom = 30.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ActionChip(text = "더보기",
                    onClick = { navController.navigate(HomeDestinations.MY_TELLER_BADGE) })
            }
        }
    }
}

@Composable
fun TellerScreenHeader(navController: NavController, uiState: TellerCardContract.State) {
    BasicAppBar(modifier = Modifier
        .background(Background100)
        .height(48.dp)
        .padding(start = 20.dp, end = 20.dp)
        .fillMaxWidth(), leftSlot = {
        Icon(
            painter = painterResource(R.drawable.icon_caret_left),
            contentDescription = "caret_left",
            modifier = Modifier.clickable(onClick = { navController.popBackStack() })
        )
    }, rightSlot = { CheeseBadge(cheeseBalance = uiState.cheeseBalance) })
}