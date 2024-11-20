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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val TAG: String = "로그"
    val context = LocalContext.current

    val mainData = viewModel.currentState.mainData

    mainData?.let {
        MainLayout(
            header = {
                HomeScreenHeader(navController, viewModel = viewModel)
            },
            content = {
                HomeScreenContent(
                    navController = navController,
                    viewModel = viewModel
                )
            }
        )
    }
}

@Composable
fun HomeScreenHeader(navController: NavController, viewModel: HomeViewModel) {
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
            Icon(
                painter = painterResource(R.drawable.icon_notice),
                contentDescription = "icon_notice",
                modifier = Modifier
                    .size(24.dp)
                    .clickable(onClick = { navController.navigate(MyPageDestinations.ALARM) }),
                tint = Gray200
            )
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreenContent(
    navController: NavController,
    viewModel: HomeViewModel,
) {
    val cardList = listOf(
        "happy",
        "excited",
        "happy",
        "excited",
        "happy",
        "excited",
        "happy",
        "excited",
        "happy",
        "excited",
        "happy",
        "excited",
    )


    val TAG: String = "로그"
    Log.d(
        TAG,
        "HomeScreenContent viewModel.currentState.mainData: ${viewModel.currentState.mainData}"
    )

    val mainData = viewModel.currentState.mainData

    mainData?.let {
        Column(
        ) {
            Box(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
                ProfileWidget(
                    nickname = mainData.userNickname,
                    description = "연속 ${mainData.recordCount} 일째 기록",
                    modifier = Modifier.clickable { navController.navigate(HomeDestinations.TELLER_CARD) })
            }
            Box(modifier = Modifier.padding(top = 10.dp, start = 20.dp, end = 20.dp)) {
                mainData?.userLevel?.let {
                    LevelSection(
                        level = mainData.userLevel,
                        percent = mainData.userExp
                    )
                }
            }
            Column(modifier = Modifier.padding(top = 32.dp, start = 20.dp, end = 20.dp)) {
                Text(text = "오늘의 질문", style = TellingmeTheme.typography.body1Bold)
                Text(
                    modifier = Modifier.padding(top = 5.dp),
                    text = "${mainData?.todayAnswerCount} 명이 대답했어요!",
                    style = TellingmeTheme.typography.caption1Regular
                )
                QuestionSection(
                    modifier = Modifier.padding(top = 12.dp),
                    title = "${mainData.questionTitle}",
                    description = "${mainData.questionPhrase}",
                    onClickButton = {
                        navController.navigate(HomeDestinations.RECORD)
                    }
                )
            }

            Column(modifier = Modifier.padding(start = 20.dp, top = 32.dp)) {
                Text(text = "나와 비슷한 텔러들의 이야기", style = TellingmeTheme.typography.body1Bold)

                if (mainData.communicationList.isNotEmpty()) {
                    val pagerState = rememberPagerState { mainData.communicationList.size }

                    HorizontalPager(
                        modifier = Modifier.padding(top = 12.dp),
                        state = pagerState,
                        contentPadding = PaddingValues(end = 32.dp),
                    ) { page ->
                        val item = mainData.communicationList[page]
                        OpinionCard(
                            modifier = Modifier.padding(end = 12.dp),
                            heartCount = item.likeCount,
                            buttonState = if (item.isLiked) ButtonState.SELECTED else ButtonState.ENABLED,
                            description = item.content,
                            emotion = item.emotion,
                        )
                    }
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
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

            if (mainData.communicationList.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .padding(top = 14.dp, bottom = 30.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ActionChip(
                        text = "더보기",
                        onClick = {}
                    )
                }
            }

        } //    Column END
    }

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

