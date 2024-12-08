package com.tellingus.tellingme.presentation.ui.feature.home.mytellerbadge

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.tellingus.tellingme.R
import com.tellingus.tellingme.presentation.ui.common.component.appbar.BasicAppBar
import com.tellingus.tellingme.presentation.ui.common.component.badge.CheeseBadge
import com.tellingus.tellingme.presentation.ui.common.component.badge.TellerBadge
import com.tellingus.tellingme.presentation.ui.common.component.layout.MainLayout
import com.tellingus.tellingme.presentation.ui.theme.Background100
import com.tellingus.tellingme.presentation.ui.theme.Gray600
import com.tellingus.tellingme.presentation.ui.theme.TellingmeTheme
import com.tellingus.tellingme.presentation.ui.theme.Typography

@Composable
fun MyTellerBadgeScreen(
    navController: NavController, myTellerBadgeViewModel: MyTellerBadgeViewModel = hiltViewModel()
) {
    val uiState by myTellerBadgeViewModel.uiState.collectAsStateWithLifecycle()
    MainLayout(isScrollable = false,
        header = { MyTellerBadgeScreenHeader(navController)},
        content = { MyTellerBadgeScreenContent(uiState) })
}

@Composable
fun MyTellerBadgeScreenContent(uiState: MyTellerBadgeContract.State) {
    val userBadgeList = uiState.userBadgeList


    Column(modifier = Modifier.fillMaxHeight()) {
        if (userBadgeList.isEmpty()) {
            EmptyContent()
        } else {
            Row(modifier = Modifier.padding(top = 9.dp, start = 20.dp)) {
                Text(text = "내가 받은", style = TellingmeTheme.typography.head2Regular)
                Spacer(modifier = Modifier.size(4.dp))
                Text(text = "텔러 배지", style = TellingmeTheme.typography.head2Bold)
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // 2열 그리드
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(14.dp), // 그리드의 전체 패딩
                verticalArrangement = Arrangement.spacedBy(14.dp), // 아이템 간의 세로 간격
                horizontalArrangement = Arrangement.spacedBy(14.dp) // 아이템 간의 가로 간격
            ) {
                items(userBadgeList) { badge ->
                    TellerBadge(
                        title = badge.badgeName,
                        content = badge.badgeMiddleName,
                        badgeCode = badge.badgeCode
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(painter = painterResource(R.drawable.my_teller_badge_empty), contentDescription = "")
        Text(text = "아직 받은 배지가 없어요.", style = Typography.body1Bold, color = Gray600)
        Text(text = "첫 글을 작성하면 받을지도 몰라요!", style = Typography.body1Regular, color = Gray600)
    }
}

@Composable
fun MyTellerBadgeScreenHeader(navController: NavController) {
    BasicAppBar(modifier = Modifier
        .background(Background100)
        .height(48.dp)
        .padding(start = 20.dp, end = 20.dp)
        .fillMaxWidth(), leftSlot = {
        Icon(
            painter = painterResource(R.drawable.icon_caret_left),
            contentDescription = "tellingme_logo",
            modifier = Modifier.clickable(onClick = { navController.popBackStack() })
        )
    })
}