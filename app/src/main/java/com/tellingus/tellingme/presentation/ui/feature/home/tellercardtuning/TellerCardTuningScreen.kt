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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.tellingus.tellingme.R
import com.tellingus.tellingme.data.model.home.Badge
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
import com.tellingus.tellingme.presentation.ui.common.const.colorCodeList
import com.tellingus.tellingme.presentation.ui.common.const.getColorByColorCode
import com.tellingus.tellingme.presentation.ui.common.const.getMediumEmotionBadge
import com.tellingus.tellingme.presentation.ui.common.model.ButtonSize
import com.tellingus.tellingme.presentation.ui.theme.Background100
import com.tellingus.tellingme.presentation.ui.theme.Base0
import com.tellingus.tellingme.presentation.ui.theme.Gray300
import com.tellingus.tellingme.presentation.ui.theme.Gray50
import com.tellingus.tellingme.presentation.ui.theme.Gray600
import com.tellingus.tellingme.presentation.ui.theme.Primary400
import com.tellingus.tellingme.presentation.ui.theme.TellingmeTheme
import com.tellingus.tellingme.presentation.ui.theme.Typography
import kotlinx.coroutines.launch
import androidx.compose.material3.Icon as Icon1


@Composable
fun TellerCardTuningScreen(
    navController: NavController, viewModel: TellerCardTuningViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val cheeseBalance = uiState.cheeseBalance

    MainLayout(header = {
        TellerCardTuningScreenHeader(
            navController = navController, cheeseBalance
        )
    }, content = {
        TellerCardTuningScreenContent(
            navController = navController, uiState = uiState, viewModel = viewModel
        )
    })
}

@Composable
fun TellerCardTuningScreenContent(
    navController: NavController,
    uiState: TellerCardTuningContract.State,
    viewModel: TellerCardTuningViewModel
) {
    val userInfo = uiState.userInfo
    val levelInfo = uiState.levelInfo
    val recordCount = uiState.recordCount
    val cheeseBalance = uiState.cheeseBalance
    val myColors = uiState.colors
    val badges = uiState.badges

    var selectedBadgeCode by remember { mutableStateOf(userInfo.tellerCard.badgeCode) }
    var selectedColorCode by remember { mutableStateOf(userInfo.tellerCard.colorCode) }

    var profileCardResponse by remember {
        mutableStateOf(
            ProfileCardResponse(
                nickname = userInfo.nickname,
                description = userInfo.tellerCard.badgeMiddleName + " " + userInfo.tellerCard.badgeName,
                level = "LV. ${levelInfo.levelDto.level}",
                consecutiveWritingDate = "${recordCount}일째",
                profileIcon = "R.drawable.icon_profile_sample",
                badgeCode = selectedBadgeCode,
                colorCode = selectedColorCode,
            )
        )
    }
    LaunchedEffect(userInfo.tellerCard.badgeCode) {
        selectedBadgeCode = userInfo.tellerCard.badgeCode
    }
    LaunchedEffect(userInfo.tellerCard.colorCode) {
        selectedColorCode = userInfo.tellerCard.colorCode
    }

    LaunchedEffect(profileCardResponse) {
        profileCardResponse = ProfileCardResponse(
            nickname = userInfo.nickname,
            description = userInfo.tellerCard.badgeMiddleName + " " + userInfo.tellerCard.badgeName,
            level = "LV. ${levelInfo.levelDto.level}",
            consecutiveWritingDate = "${recordCount}일째",
            profileIcon = "R.drawable.icon_profile_sample",
            badgeCode = selectedBadgeCode,
            colorCode = selectedColorCode,
        )
    }

    LaunchedEffect(selectedBadgeCode, selectedColorCode) {
        profileCardResponse = profileCardResponse.copy(
            badgeCode = selectedBadgeCode, colorCode = selectedColorCode
        )
    }

    Column(modifier = Modifier.padding(top = 22.dp)) {
        Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
            Text(text = "내 마음대로", style = Typography.head2Regular, color = Gray600)
            Row {
                Text(text = "꾸미는 ", style = Typography.head2Regular, color = Gray600)
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
            BadgeChangeSheet(
                myColors = myColors,
                cheeseBalance = cheeseBalance,
                badges = badges,
                selectedBadgeCode,
                selectedColorCode,
                onBadgeCodeSelected = { badgeCode ->
                    selectedBadgeCode = badgeCode;
                },
                onColorCodeSelected = { colorCode ->
                    selectedColorCode = colorCode;
                },
                onReset = {
                    selectedBadgeCode = userInfo.tellerCard.badgeCode
                    selectedColorCode = userInfo.tellerCard.colorCode
                    profileCardResponse = ProfileCardResponse(
                        nickname = userInfo.nickname,
                        description = userInfo.tellerCard.badgeName,
                        level = "LV. ${levelInfo.levelDto.level}",
                        consecutiveWritingDate = "${recordCount}일째",
                        profileIcon = "R.drawable.icon_profile_sample",
                        badgeCode = selectedBadgeCode,
                        colorCode = selectedColorCode,
                    )
                },
                onConfirm = {
                    if (selectedBadgeCode.isEmpty() || selectedColorCode.isEmpty()) return@BadgeChangeSheet
                    viewModel.processEvent(
                        TellerCardTuningContract.Event.OnClickPatchTellerCard(
                            colorCode = selectedColorCode, badgeCode = selectedBadgeCode
                        )
                    )
                    navController.popBackStack()
                })
        }
    }
}

@Composable
fun BadgeChangeSheet(
    myColors: List<com.tellingus.tellingme.data.model.home.Color>,
    cheeseBalance: Int,
    badges: List<Badge>,
    selectedBadgeCode: String,
    selectedColorCode: String,
    onBadgeCodeSelected: (badgeCode: String) -> Unit,
    onColorCodeSelected: (colorCode: String) -> Unit,
    onReset: () -> Unit,
    onConfirm: () -> Unit
) {
    var selectedOption by remember { mutableStateOf("badge") }
    var showBuyColorCodeDialog by remember { mutableStateOf(false) }

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
                    BadgeContent(
                        badges = badges,
                        selectedBadgeCode,
                        onBadgeCodeSelected = { badgeCode ->
                            onBadgeCodeSelected(badgeCode)
                        })

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
                            onClick = {
                                onReset()
                            })
                        Spacer(modifier = Modifier.width(8.dp))
                        PrimaryButton(modifier = Modifier
                            .width(163.dp)
                            .height(50.dp),
                            size = ButtonSize.LARGE,
                            text = "완료",
                            onClick = {
                                onConfirm()
                            })
                    }
                }
            }

            "bgColor" -> {
                Column(modifier = Modifier.padding(top = 40.dp)) {
                    BgColorContent(myColors, selectedColorCode, onColorCodeSelected)

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
                            onClick = {
                                onReset()
                            })
                        Spacer(modifier = Modifier.width(8.dp))
                        PrimaryButton(modifier = Modifier
                            .width(163.dp)
                            .height(50.dp),
                            size = ButtonSize.LARGE,
                            text = "완료",
                            onClick = {
                                if (isCheeseNeeded(myColors, selectedColorCode)) {
                                    if (cheeseBalance < 20) {

                                    } else {
                                        showBuyColorCodeDialog = true
                                    }
                                } else {
                                    onConfirm()
                                }
                            })
                    }
                }
            }
        }
    }


    if (showBuyColorCodeDialog) {
        Dialog(
            onDismissRequest = {
                showBuyColorCodeDialog = false
            },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = false
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .background(
                        shape = RoundedCornerShape(20.dp),
                        color = Base0
                    )
                    .padding(top = 30.dp, start = 16.dp, end = 16.dp, bottom = 20.dp)
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "치즈 20개로 구매하시겠어요?",
                    style = TellingmeTheme.typography.body1Bold.copy(
                        color = Gray600
                    )
                )
                Spacer(modifier = Modifier.size(20.dp))
                Image(
                    modifier = Modifier.size(120.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.icon_cheese_box),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.size(20.dp))
                Row {
                    PrimaryLightButton(
                        modifier = Modifier.weight(1f),
                        size = ButtonSize.LARGE,
                        text = "취소",
                        onClick = {
                            showBuyColorCodeDialog = false
                        }
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    PrimaryButton(
                        modifier = Modifier.weight(1f),
                        size = ButtonSize.LARGE,
                        text = "구매하기",
                        onClick = {
                            onConfirm()
                            showBuyColorCodeDialog = false
                        }
                    )
                }
            }
        }
    }
}

// 뱃지 코드에 따른 이미지 리소스 매핑 함수
private fun getEmotionResourceForBadge(badgeCode: String): Int {
    return when (badgeCode) {
        "BG_AGAIN_001" -> R.drawable.teller_emotion_badge_connexion_large
        "BG_CHRISTMAS_2024" -> R.drawable.teller_emotion_badge_christmas_large
        "BG_FIRST" -> R.drawable.teller_emotion_badge_traveler_large
        "BG_MUCH_001" -> R.drawable.teller_emotion_badge_toomuch_large
        "BG_NEW" -> R.drawable.teller_emotion_badge_mystery_large
        "BG_NIGHT_001" -> R.drawable.teller_emotion_badge_owl_large
        "BG_SAVE_001" -> R.drawable.teller_emotion_badge_savings_large
        else -> R.drawable.teller_emotion_badge_mystery_large // 기본 이미지
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BadgeContent(
    badges: List<Badge>,
    selectedBadgeCode: String,
    onBadgeCodeSelected: (badgeCode: String) -> Unit
) {
    val cardList = LargeEmotionBadgeList
    val pagerState = rememberPagerState(pageCount = {
        badges.size
    })
    val coroutineScope = rememberCoroutineScope()

    // badges가 비어있으면 early return
    if (badges.isEmpty()) {
        Column(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
                .fillMaxWidth()
                .height(138.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.empty_character),
                contentDescription = "empty_character"
            )
            Text(text = "아직 획득한 배지가 없어요.", style = TellingmeTheme.typography.body2Regular)
        }
        return
    }

    Box(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
        HorizontalPager(
            state = pagerState,
            pageSpacing = 28.dp,
            pageSize = PageSize.Fixed(106.dp)
        ) { page ->
            val currentBadge = badges[page]
            BadgeCard(
                index = page,
                isChecked = selectedBadgeCode == currentBadge.badgeCode,
                onCheckChange = { index, badgeCode ->
                    onBadgeCodeSelected(badgeCode)
                },
                emotionBadge = EmotionBadge(
                    badgeName = currentBadge.badgeName,
                    badgeCode = currentBadge.badgeCode,
                    badgeMiddleName = currentBadge.badgeMiddleName,
                    badgeCondition = currentBadge.badgeCondition,
                    emotionRes = getEmotionResourceForBadge(currentBadge.badgeCode) // 뱃지 코드에 따른 이미지 리소스 매핑 함수 필요
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
    index: Int,
    isChecked: Boolean,
    onCheckChange: (index: Int, badgeCode: String) -> Unit,
    emotionBadge: EmotionBadge
) {
    Card(modifier = Modifier.height(124.dp),
        colors = CardDefaults.cardColors(Base0),
        onClick = { onCheckChange(index, emotionBadge.badgeCode) }) {
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
fun BgColorContent(
    myColors: List<com.tellingus.tellingme.data.model.home.Color>,
    selectedColorCode: String, onColorCodeSelected: (colorCode: String) -> Unit
) {
    val items = List(colorCodeList.size) { "Item $it" } // 9개의 아이템 생성
    val pagerState = rememberPagerState(pageCount = { (items.size + 7) / 8 }) // 각 페이지당 8개 아이템
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp)
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
                                val colorItem = colorCodeList[currentIndex % colorCodeList.size]
                                val topColor = colorItem.topColor
                                val bottomColor = colorItem.bottomColor
                                DiagonalHalfCircleBadge(
                                    hasNeedCheese = isCheeseNeeded(
                                        myColors,
                                        colorItem.colorCode
                                    ),
                                    colorCode = colorItem.colorCode,
                                    bottomColor = bottomColor,
                                    topColor = topColor,
                                    index = currentIndex,
                                    isChecked = selectedColorCode == colorItem.colorCode,
                                    onCheckChange = { index, colorCode ->
                                        onColorCodeSelected(colorCode)
                                    },
                                )
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

fun isCheeseNeeded(
    userColorList: List<com.tellingus.tellingme.data.model.home.Color>,
    colorCode: String
): Boolean {
    // 치즈가 필요 없는 기본 색상 목록
    val excludedColors = setOf("CL_DEFAULT", "CL_BLUE_001", "CL_ORANGE_001", "CL_RED_001")

    // colorCode가 excludedColors에 포함되어 있으면 치즈가 필요 없음 (false)
    if (colorCode in excludedColors) {
        return false
    }

    // colorCode가 userColorList에 포함되어 있으면 치즈가 필요 없음 (false)
    if (userColorList.any { it.colorCode == colorCode }) {
        return false
    }

    // 위 조건에 해당하지 않으면 치즈가 필요함 (true)
    return true
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
