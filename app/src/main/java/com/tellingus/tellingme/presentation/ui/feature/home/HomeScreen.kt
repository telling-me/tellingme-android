package com.tellingus.tellingme.presentation.ui.feature.home

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tellingus.tellingme.R
import com.tellingus.tellingme.data.model.home.HomeRequest
import com.tellingus.tellingme.presentation.ui.common.component.appbar.BasicAppBar
import com.tellingus.tellingme.presentation.ui.common.component.card.OpinionCard
import com.tellingus.tellingme.presentation.ui.common.component.chip.ActionChip
import com.tellingus.tellingme.presentation.ui.common.component.layout.MainLayout
import com.tellingus.tellingme.presentation.ui.common.component.section.QuestionSection
import com.tellingus.tellingme.presentation.ui.common.component.widget.LevelSection
import com.tellingus.tellingme.presentation.ui.common.component.widget.ProfileWidget
import com.tellingus.tellingme.presentation.ui.common.model.ButtonState
import com.tellingus.tellingme.presentation.ui.common.navigation.HomeDestinations
import com.tellingus.tellingme.presentation.ui.common.navigation.MyPageDestinations
import com.tellingus.tellingme.presentation.ui.theme.Background100
import com.tellingus.tellingme.presentation.ui.theme.Gray200
import com.tellingus.tellingme.presentation.ui.theme.Gray600
import com.tellingus.tellingme.presentation.ui.theme.Primary400
import com.tellingus.tellingme.presentation.ui.theme.TellingmeTheme
import com.tellingus.tellingme.presentation.ui.theme.Typography
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun HomeScreen(
    navController: NavController, viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()



    MainLayout(header = {
        HomeScreenHeader(navController, unreadNoticeStatus = uiState.unreadNoticeStatus)
    }, content = {
        HomeScreenContent(
            navController = navController, uiState = uiState
        )
    })

}

@Composable
fun HomeScreenHeader(navController: NavController, unreadNoticeStatus: Boolean) {
    BasicAppBar(
        modifier = Modifier
            .background(Background100)
            .height(48.dp)
            .padding(start = 20.dp, end = 20.dp)
            .fillMaxWidth(),
        leftSlot = {
            Icon(
                painter = painterResource(R.drawable.tellingme_logo),
                contentDescription = "tellingme_logo",
                tint = Primary400
            )
        },
        rightSlot = {
            Box {
                Icon(
                    painter = painterResource(R.drawable.icon_notice),
                    contentDescription = "icon_notice",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(onClick = { navController.navigate(MyPageDestinations.ALARM) }),
                    tint = Gray200
                )
                if (unreadNoticeStatus) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(Color.Red, shape = CircleShape)
                            .align(Alignment.TopEnd) // 아이콘의 오른쪽 상단에 위치
                            .offset(x = 2.dp, y = (-2).dp) // 약간 더 정확한 위치 조정
                    )
                }
            }
        }
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreenContent(
    navController: NavController, uiState: HomeContract.State
) {
    val recordCount = uiState.recordCount
    val todayAnswerCount = uiState.todayAnswerCount
    val communicationList = uiState.communicationList
    val unreadNoticeStatus = uiState.unreadNoticeStatus
    val questionTitle = uiState.questionTitle
    val questionPhrase = uiState.questionPhrase
    val userNickname = uiState.userNickname
    val userLevel = uiState.userLevel
    val userExp = uiState.userExp

    Column(
    ) {
        Box(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {

            ProfileWidget(nickname = userNickname,
                description = "연속 ${recordCount} 일째 기록",
                modifier = Modifier.clickable { navController.navigate(HomeDestinations.TELLER_CARD) })

        }
        Box(modifier = Modifier.padding(top = 10.dp, start = 20.dp, end = 20.dp)) {
            LevelSection(
                level = userLevel, percent = userExp
            )

        }
        Column(modifier = Modifier.padding(top = 32.dp, start = 20.dp, end = 20.dp)) {
            Text(text = "오늘의 질문", style = TellingmeTheme.typography.body1Bold)
            Text(
                modifier = Modifier.padding(top = 5.dp),
                text = "${todayAnswerCount} 명이 대답했어요!",
                style = TellingmeTheme.typography.caption1Regular
            )

            QuestionSection(modifier = Modifier.padding(top = 12.dp),
                title = "${questionTitle}",
                description = "${questionPhrase}",
                onClickButton = {
                    navController.navigate(HomeDestinations.RECORD)
                })

        }

        Column(modifier = Modifier.padding(start = 20.dp, top = 32.dp)) {
            Text(text = "나와 비슷한 텔러들의 이야기", style = TellingmeTheme.typography.body1Bold)


            if (communicationList.isNotEmpty()) {
                val pagerState = rememberPagerState { communicationList.size }

                HorizontalPager(
                    modifier = Modifier.padding(top = 12.dp),
                    state = pagerState,
                    contentPadding = PaddingValues(end = 32.dp),
                ) { page ->
                    val item = communicationList[page]
                    OpinionCard(
                        modifier = Modifier.padding(end = 12.dp),
                        heartCount = item.likeCount,
                        buttonState = if (item.isLiked) ButtonState.SELECTED else ButtonState.ENABLED,
                        description = item.content,
                        emotion = item.emotion,
                    )
                }
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 20.dp, top = 20.dp)

                ) {
                    Image(
                        painter = painterResource(id = R.drawable.empty_character),
                        contentDescription = "empty_character"
                    )
                    Text(
                        text = "나와 비슷한 텔러들의 이야기가 없어요",
                        style = Typography.body2Bold,
                        color = Gray600
                    )
                }

            }

        }
        if (communicationList.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .padding(top = 14.dp, bottom = 30.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ActionChip(text = "더보기", onClick = {})
            }
        }


    } //    Column END


}


@Preview(showBackground = true)
@Composable
fun HomeScreenHeaderPreview() {
    HomeScreenHeader(navController = rememberNavController(), hiltViewModel())
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}

