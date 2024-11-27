package com.tellingus.tellingme.presentation.ui.feature.home.tellercardtuning


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.tellingus.tellingme.R
import com.tellingus.tellingme.presentation.ui.common.base.UiState
import com.tellingus.tellingme.presentation.ui.common.component.appbar.BasicAppBar
import com.tellingus.tellingme.presentation.ui.common.component.badge.CheeseBadge
import com.tellingus.tellingme.presentation.ui.common.component.badge.DiagonalHalfCircleBadge
import com.tellingus.tellingme.presentation.ui.common.component.button.PrimaryButton
import com.tellingus.tellingme.presentation.ui.common.component.button.PrimaryLightButton
import com.tellingus.tellingme.presentation.ui.common.component.layout.MainLayout
import com.tellingus.tellingme.presentation.ui.common.component.widget.ProfileCard
import com.tellingus.tellingme.presentation.ui.common.component.widget.ProfileCardResponse
import com.tellingus.tellingme.presentation.ui.common.const.EmotionBadge
import com.tellingus.tellingme.presentation.ui.common.const.LargeEmotionBadgeList
import com.tellingus.tellingme.presentation.ui.common.const.getColorByColorCode
import com.tellingus.tellingme.presentation.ui.common.const.getLargeEmotionBadge
import com.tellingus.tellingme.presentation.ui.common.const.getMediumEmotionBadge
import com.tellingus.tellingme.presentation.ui.common.model.ButtonSize
import com.tellingus.tellingme.presentation.ui.theme.Background100
import com.tellingus.tellingme.presentation.ui.theme.Base0
import com.tellingus.tellingme.presentation.ui.theme.Gray300
import com.tellingus.tellingme.presentation.ui.theme.Gray50
import com.tellingus.tellingme.presentation.ui.theme.Gray600
import com.tellingus.tellingme.presentation.ui.theme.Primary400
import com.tellingus.tellingme.presentation.ui.theme.Profile100
import com.tellingus.tellingme.presentation.ui.theme.Profile100_Bottom
import com.tellingus.tellingme.presentation.ui.theme.Profile200
import com.tellingus.tellingme.presentation.ui.theme.Profile200_Bottom
import com.tellingus.tellingme.presentation.ui.theme.Profile300
import com.tellingus.tellingme.presentation.ui.theme.Profile300_Bottom
import com.tellingus.tellingme.presentation.ui.theme.Profile400
import com.tellingus.tellingme.presentation.ui.theme.Profile400_Bottom
import com.tellingus.tellingme.presentation.ui.theme.Profile500
import com.tellingus.tellingme.presentation.ui.theme.Profile500_Bottom
import com.tellingus.tellingme.presentation.ui.theme.Profile600
import com.tellingus.tellingme.presentation.ui.theme.Profile600_Bottom
import com.tellingus.tellingme.presentation.ui.theme.Profile700
import com.tellingus.tellingme.presentation.ui.theme.Profile700_Bottom
import com.tellingus.tellingme.presentation.ui.theme.Profile800
import com.tellingus.tellingme.presentation.ui.theme.Profile800_Bottom
import com.tellingus.tellingme.presentation.ui.theme.Profile900
import com.tellingus.tellingme.presentation.ui.theme.Profile900_Bottom
import com.tellingus.tellingme.presentation.ui.theme.Typography
import kotlinx.coroutines.launch
import androidx.compose.material3.Icon as Icon1


@Composable
fun TellerCardTuningScreen(
    navController: NavController,
    viewModel: TellerCardTuningViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val cheeseBalance = uiState.cheeseBalance
    MainLayout(header = {
        TellerCardTuningScreenHeader(
            navController = navController,
            cheeseBalance
        )
    },
        content = { TellerCardTuningScreenContent(uiState) })
}

@Composable
fun TellerCardTuningScreenContent(uiState: TellerCardTuningContract.State) {

    val userInfo = uiState.userInfo
    val levelInfo = uiState.levelInfo

    val profileCardResponse: ProfileCardResponse = ProfileCardResponse(
        nickname = userInfo.nickname,
        description = userInfo.tellerCard.badgeName,
        level = "LV. ${levelInfo.level_dto.level}",
        consecutiveWritingDate = "${levelInfo.days_to_level_up}일째",
        profileIcon = "R.drawable.icon_profile_sample",
        badgeCode = userInfo.tellerCard.badgeCode,
        colorCode = userInfo.tellerCard.colorCode,
    )

    Column(modifier = Modifier.padding(top = 22.dp)) {
        Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
            Text(text = "내 마음대로", style = Typography.head2Regular, color = Gray600)
            Row {
                Text(text = "꾸미는", style = Typography.head2Regular, color = Gray600)
                Text(text = "텔러카드", style = Typography.head2Bold, color = Gray600)
            }
        }

        Box(modifier = Modifier.padding(top = 26.dp, start = 20.dp, end = 20.dp)) {
            ProfileCard(
                response = profileCardResponse,
                backgroundColor = getColorByColorCode(profileCardResponse.colorCode)
            )
        }

        Box(modifier = Modifier.padding(top = 40.dp)) {
            BadgeChangeSheet()
        }
    }
}

@Composable
fun BadgeChangeSheet() {
    var selectedOption by remember { mutableStateOf("badge") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            .fillMaxHeight()
            .heightIn(360.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.padding(top = 20.dp),
        ) {
            BadgeToggle(selectedOption = selectedOption, onOptionSelected = { newOption ->
                selectedOption = newOption
            })
        }
        when (selectedOption) {
            "badge" -> {
                Column(modifier = Modifier.padding(top = 40.dp)) {
                    BadgeContent()

                    Row(
                        modifier = Modifier
                            .padding(top = 34.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        PrimaryLightButton(modifier = Modifier
                            .width(163.dp)
                            .height(50.dp),
                            size = ButtonSize.LARGE,
                            text = "되돌리기",
                            onClick = { /*TODO*/ })
                        Spacer(modifier = Modifier.width(8.dp))
                        PrimaryButton(modifier = Modifier
                            .width(163.dp)
                            .height(50.dp),
                            size = ButtonSize.LARGE,
                            text = "완료",
                            onClick = { /*TODO*/ })
                    }
                }
            }

            "bgColor" -> {
                Column(modifier = Modifier.padding(top = 40.dp)) {
                    BgColorContent()

                    Row(
                        modifier = Modifier
                            .padding(top = 34.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        PrimaryLightButton(modifier = Modifier
                            .width(163.dp)
                            .height(50.dp),
                            size = ButtonSize.LARGE,
                            text = "되돌리기",
                            onClick = { /*TODO*/ })
                        Spacer(modifier = Modifier.width(8.dp))
                        PrimaryButton(modifier = Modifier
                            .width(163.dp)
                            .height(50.dp),
                            size = ButtonSize.LARGE,
                            text = "완료",
                            onClick = { /*TODO*/ })
                    }
                }
            }
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BadgeContent() {
//    val cardList = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
    val cardList = LargeEmotionBadgeList
    val pagerState = rememberPagerState(pageCount = {
        cardList.size
    })
    var selectedCardIndex by remember { mutableStateOf(-1) } // 선택된 카드의 인덱스
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
        HorizontalPager(
            state = pagerState, pageSpacing = 28.dp, pageSize = PageSize.Fixed(106.dp)
        ) { page ->
            BadgeCard(
                index = page,
                isChecked = selectedCardIndex == page,
                onCheckChange = { index ->
                    selectedCardIndex = index
                },
                emotionBadge = EmotionBadge(
                    badgeName = cardList[page].badgeName,
                    badgeCode = cardList[page].badgeCode,
                    badgeMiddleName = cardList[page].badgeMiddleName,
                    badgeCondition = cardList[page].badgeCondition,
                    emotionRes = cardList[page].emotionRes
                )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 62.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Image(painter = painterResource(R.drawable.icon_paging_button_left),
                contentDescription = "",
                modifier = Modifier.clickable {
                    coroutineScope.launch {
                        if (pagerState.currentPage > 0) {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }
                })
            Image(painter = painterResource(R.drawable.icon_paging_button_right),
                contentDescription = "",
                modifier = Modifier.clickable {
                    coroutineScope.launch {
                        if (pagerState.currentPage < pagerState.pageCount - 1) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BadgeCard(
    index: Int, isChecked: Boolean, onCheckChange: (index: Int) -> Unit,
    emotionBadge: EmotionBadge
) {
    Card(modifier = Modifier.height(124.dp),
        colors = CardDefaults.cardColors(Base0),
        onClick = { onCheckChange(index) }) {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center // 세로 방향 중앙 정렬
            ) {
                Image(
                    painter = painterResource(getMediumEmotionBadge(emotionBadge.badgeCode)),
                    contentDescription = "",
                )
                Text(
                    text = "${emotionBadge.badgeMiddleName}",
                    style = Typography.caption2Regular,
                    color = Gray600
                )
                Text(
                    text = "${emotionBadge.badgeName}",
                    style = Typography.caption1Bold,
                    color = Gray600
                )
            }
            Image(
                painter = if (isChecked) painterResource(R.drawable.icon_check_circle_on_white) else painterResource(
                    R.drawable.icon_check_circle_off_white
                ),
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 6.dp, end = 16.dp)
                    .zIndex(1f)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BgColorContent() {
    val items = List(9) { "Item $it" } // 9개의 아이템 생성
    val pagerState = rememberPagerState(pageCount = { (items.size + 7) / 8 }) // 각 페이지당 8개 아이템
    val coroutineScope = rememberCoroutineScope()
    var selectedCardIndex by remember { mutableStateOf(-1) } // 선택된 카드의 인덱스

    val profileColors = listOf(
        Pair(Profile100, Profile100_Bottom),
        Pair(Profile200, Profile200_Bottom),
        Pair(Profile300, Profile300_Bottom),
        Pair(Profile400, Profile400_Bottom),
        Pair(Profile500, Profile500_Bottom),
        Pair(Profile600, Profile600_Bottom),
        Pair(Profile700, Profile700_Bottom),
        Pair(Profile800, Profile800_Bottom),
        Pair(Profile900, Profile900_Bottom),
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 22.dp, end = 22.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.heightIn(138.dp),
            verticalAlignment = Alignment.Top
        ) { page ->
            // 각 페이지의 아이템 표시
            val startIndex = page * 8
            val endIndex = minOf(startIndex + 8, items.size)

            Column() {
                for (i in startIndex until endIndex step 4) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .then(if (i + 4 >= endIndex) Modifier else Modifier.padding(bottom = 20.dp)),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        for (j in 0 until 4) {
                            val currentIndex = i + j
                            if (currentIndex < endIndex) {

                                val colorPair = profileColors[currentIndex % profileColors.size]
                                val topColor = colorPair.first
                                val bottomColor = colorPair.second

                                DiagonalHalfCircleBadge(bottomColor = bottomColor,
                                    topColor = topColor,
                                    index = currentIndex,
                                    isChecked = selectedCardIndex == currentIndex,
                                    onCheckChange = { index ->
                                        selectedCardIndex = index
                                    })
                            }
                        }
                    }
                }
            }
        }




        Box {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(top = 62.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Image(painter = painterResource(R.drawable.icon_paging_button_left),
                    contentDescription = "",
                    modifier = Modifier.clickable {
                        coroutineScope.launch {
                            if (pagerState.currentPage > 0) {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        }
                    })
                Image(painter = painterResource(R.drawable.icon_paging_button_right),
                    contentDescription = "",
                    modifier = Modifier.clickable {
                        coroutineScope.launch {
                            if (pagerState.currentPage < pagerState.pageCount - 1) {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    })
            }
        }
    }

}

@Composable
fun BadgeToggle(selectedOption: String = "badge", onOptionSelected: (String) -> Unit) {
    Row(
        modifier = Modifier
            .background(Gray50, shape = RoundedCornerShape(8.dp))
            .width(152.dp)
            .height(40.dp)
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .width(70.dp)
                .height(40.dp)
                .background(
                    if (selectedOption == "badge") Color.White else Gray50,
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable { onOptionSelected("badge") }, contentAlignment = Alignment.Center
        ) {
            Text(
                text = "배지", style = Typography.body2Bold, color = if (selectedOption == "badge") {
                    Primary400
                } else {
                    Gray300
                }
            )
        }
        Box(
            modifier = Modifier
                .width(70.dp)
                .height(40.dp)
                .background(
                    if (selectedOption == "bgColor") Color.White else Gray50,
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable { onOptionSelected("bgColor") }, contentAlignment = Alignment.Center
        ) {
            Text(
                text = "배경색",
                style = Typography.body2Bold,
                color = if (selectedOption == "bgColor") {
                    Primary400
                } else {
                    Gray300
                }
            )
        }
    }
}

@Composable
fun TellerCardTuningScreenHeader(navController: NavController, cheeseBalance: Int = 0) {
    BasicAppBar(modifier = Modifier
        .background(Background100)
        .height(48.dp)
        .padding(start = 20.dp, end = 20.dp)
        .fillMaxWidth(), leftSlot = {
        Icon1(
            painter = painterResource(R.drawable.icon_caret_left),
            contentDescription = "caret_left",
            modifier = Modifier.clickable(onClick = { navController.popBackStack() })
        )
    }, rightSlot = { CheeseBadge(cheeseBalance = cheeseBalance) })
}
