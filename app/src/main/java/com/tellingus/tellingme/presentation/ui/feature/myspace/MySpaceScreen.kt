package com.tellingus.tellingme.presentation.ui.feature.myspace

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.holix.android.bottomsheetdialog.compose.BottomSheetBehaviorProperties
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialog
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties
import com.holix.android.bottomsheetdialog.compose.NavigationBarProperties
import com.tellingus.tellingme.R
import com.tellingus.tellingme.presentation.ui.common.component.button.FloatingButton
import com.tellingus.tellingme.presentation.ui.common.component.button.PrimaryButton
import com.tellingus.tellingme.presentation.ui.common.component.card.CalendarCardView
import com.tellingus.tellingme.presentation.ui.common.const.getEmotionText
import com.tellingus.tellingme.presentation.ui.common.const.getMediumEmotion
import com.tellingus.tellingme.presentation.ui.common.model.ButtonSize
import com.tellingus.tellingme.presentation.ui.common.navigation.HomeDestinations
import com.tellingus.tellingme.presentation.ui.common.navigation.MySpaceDestinations
import com.tellingus.tellingme.presentation.ui.common.navigation.OtherSpaceDestinations
import com.tellingus.tellingme.presentation.ui.theme.Background200
import com.tellingus.tellingme.presentation.ui.theme.Base0
import com.tellingus.tellingme.presentation.ui.theme.Error400
import com.tellingus.tellingme.presentation.ui.theme.Gray300
import com.tellingus.tellingme.presentation.ui.theme.Gray500
import com.tellingus.tellingme.presentation.ui.theme.Gray600
import com.tellingus.tellingme.presentation.ui.theme.Primary400
import com.tellingus.tellingme.presentation.ui.theme.TellingmeTheme
import com.tellingus.tellingme.util.AppUtils.OnLifecycleEvent
import com.tellingus.tellingme.util.collectWithLifecycle
import com.tellingus.tellingme.util.noRippleClickable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("NewApi")
@Composable
fun MySpaceScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: MySpaceViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val initialPage = (uiState.today.year - CALENDAR_RANGE.startYear) * 12 + uiState.today.monthValue - 1
    val pageCount = (CALENDAR_RANGE.lastYear - CALENDAR_RANGE.startYear) * 12
    val calendarPagerState = rememberPagerState(pageCount = {pageCount}, initialPage = initialPage)
    var currentPage by remember { mutableIntStateOf(initialPage) }

    var isShowDatePicker by remember { mutableStateOf(false) }
    var isShowAnswerListPagerDialog by remember { mutableStateOf(false) }
    var isShowEmptyDialog by remember { mutableStateOf(false) }
    var isShowShareBottomSheet by remember { mutableStateOf(false) }


    LaunchedEffect(key1 = calendarPagerState.currentPage) {
        val swipe = (calendarPagerState.currentPage - currentPage).toLong()
        currentPage = calendarPagerState.currentPage
        viewModel.processEvent(MySpaceContract.Event.UpdateCurrentDate(swipe))
    }

    Box(
        modifier = modifier
    ) {
        Column {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp, horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = modifier
                        .clickable(
                            onClick = {
                                isShowDatePicker = !isShowDatePicker
                            },
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${uiState.currentDate.year}년",
                        style = TellingmeTheme.typography.head3Bold.copy(
                            color = Gray500,
                            fontSize = 18.sp
                        )
                    )
                    Image(
                        modifier = modifier.padding(start = 2.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.icon_caret_down),
                        colorFilter = ColorFilter.tint(Gray500),
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f))
                Box(
                    modifier = modifier
                        .background(
                            shape = RoundedCornerShape(48.dp),
                            color = Background200
                        )
                        .padding(horizontal = 16.dp, vertical = 7.5.dp)
                ) {
                    Text(
                        modifier = modifier
                            .clickable {
                                viewModel.processEvent(MySpaceContract.Event.OnClickTodayButton)
                            },
                        text = "오늘",
                        style = TellingmeTheme.typography.body2Bold.copy(
                            color = Gray600,
                            fontSize = 14.sp
                        )
                    )
                }
            }

            Box(
                modifier = modifier
                    .padding(vertical = 10.dp, horizontal = 20.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "${uiState.currentDate.monthValue}월",
                    style = TellingmeTheme.typography.head2Bold.copy(
                        color = Gray600,
                        fontSize = 20.sp
                    )
                )
            }
            Spacer(modifier = Modifier.size(4.dp))

            HorizontalPager(
                state = calendarPagerState
            ) { page ->
                Column(
                    modifier = modifier.padding(horizontal = 12.5.dp)
                ) {
                    Row(modifier) {
                        val dayOfWeek = listOf("일","월","화","수","목","금","토")
                        dayOfWeek.forEach { day ->
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 14.5.dp, horizontal = 18.dp)
                                    .weight(1f),
                                text = day,
                                textAlign = TextAlign.Center,
                                style = TellingmeTheme.typography.body2Regular.copy(
                                    color = if (day == "일") Error400 else Gray500,
                                    fontSize = 14.sp
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.size(4.dp))

                    val date = LocalDate.of(
                        CALENDAR_RANGE.startYear + page / 12,
                        page % 12 + 1,
                        1
                    )

                    val lastDay by remember { mutableIntStateOf(date.lengthOfMonth()) }    // 해당 달의 마지막 날
                    val firstDayOfWeek by remember { mutableIntStateOf(date.dayOfWeek.value) }  // 1일
                    val days by remember { mutableStateOf(IntRange(1, lastDay).toList()) }    // 해당 달의 총 일수 리스트

                    Column(modifier = modifier.fillMaxWidth()) {
                        LazyVerticalGrid(
                            modifier = modifier.fillMaxWidth(),
                            columns = GridCells.Fixed(7),
//                            verticalArrangement = Arrangement.spacedBy(24.dp)
                        ) {
                            var emptyCount = 0
                            // 1일이 시작하는 요일 전까지 공백 생성, 일요일부터 시작할 수 있도록 +1
                            for (i in 1 until firstDayOfWeek +1) {
                                item {
                                    Box(modifier = Modifier.fillMaxWidth())
                                }
                                emptyCount++
                            }

                            itemsIndexed(items = days) { index, day ->
                                val date = date.withDayOfMonth(day)

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable(
                                            onClick = {
                                                viewModel.processEvent(
                                                    MySpaceContract.Event.OnClickCalendarDate(
                                                        year = date.year,
                                                        month = date.monthValue,
                                                        day = date.dayOfMonth
                                                    )
                                                )
                                            },
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = null
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (date.monthValue == uiState.today.monthValue
                                        && date.dayOfMonth == uiState.today.dayOfMonth) {
                                        Box(
                                            modifier = modifier
                                                .background(
                                                    shape = RoundedCornerShape(48.dp),
                                                    color = Primary400
                                                )
                                                .size(6.dp)
                                                .align(
                                                    if (uiState.isAnsweredDateList.contains(uiState.today)) {
                                                        Alignment.TopEnd
                                                    } else {
                                                        Alignment.TopCenter
                                                    }
                                                )
                                        )
                                    }

                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        if (uiState.isAnsweredDateList.contains(date)) {
                                            Image(
                                                modifier = modifier.size(50.dp),
                                                imageVector = ImageVector.vectorResource(
                                                    getMediumEmotion(
                                                        index = uiState.answerList[uiState.isAnsweredDateList.indexOf(date)].emotion
                                                    )
                                                ),
                                                contentDescription = null
                                            )
                                        } else {
                                            Text(
                                                modifier = modifier
                                                    .padding(vertical = 13.dp),
                                                text = date.dayOfMonth.toString(),
                                                style = TellingmeTheme.typography.body1Bold.copy(
                                                    color = if ((index + emptyCount) % 7 == 0) Error400 else Gray500,
                                                    fontSize = 16.sp
                                                ),
                                                textAlign = TextAlign.Center
                                            )
                                        }

                                        Spacer(modifier = Modifier.height(24.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (!uiState.isTodayAnswer) {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp, end = 20.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                FloatingButton(
                    onClick = {
                        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                        val formattedDate = LocalDate.now().format(formatter)
                        navController.navigate(
                            "${MySpaceDestinations.RECORD}/${formattedDate}/1"
                        )
                    }
                )

            }
        }
    }

    OnLifecycleEvent { owner, event ->
        // do stuff on event
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                viewModel.getAnswerList()
            }
            else -> { /* other stuff */ }
        }
    }

    if (isShowDatePicker) {
        var datePickerState by remember { mutableStateOf(Pair(0, 0)) }

        BottomSheetDialog(
            onDismissRequest = { isShowDatePicker = false },
            properties = BottomSheetDialogProperties(
                navigationBarProperties = NavigationBarProperties(navigationBarContrastEnforced = false),
                dismissOnClickOutside = false,
                behaviorProperties = BottomSheetBehaviorProperties(isDraggable = false)
            )
        ) {
            Column(
                modifier = modifier
                    .background(
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                        color = Base0
                    )
                    .padding(bottom = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = modifier.padding(vertical = 16.dp)) {
                    Box(
                        modifier = modifier
                            .background(
                                color = Gray300,
                                shape = RoundedCornerShape(100.dp)
                            )
                            .size(width = 32.dp, height = 4.dp)
                    )
                }

                Box(
                    modifier = modifier
                        .padding(top = 4.dp, bottom = 8.dp, start = 8.5.dp, end = 8.5.dp)
                ) {
                    CustomDatePicker(
                        startYear = uiState.currentDate.year,
                        startMonth = uiState.currentDate.monthValue,
                        selectedDate = { year, month ->
                                if (year.isNotEmpty() && month.isNotEmpty()) {
                                datePickerState = Pair(year.toInt(), month.toInt())
                            }
                        }
                    )
                }

                PrimaryButton(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    size = ButtonSize.LARGE,
                    text = "확인",
                    onClick = {
                        isShowDatePicker = false
                        viewModel.processEvent(
                            MySpaceContract.Event.OnClickDatePickButton(
                                year = datePickerState.first,
                                month = datePickerState.second
                            )
                        )
                    }
                )
            }
        }
    }

    var selectedIndex by remember { mutableStateOf(0) }

    if (isShowAnswerListPagerDialog) {
        val pagerState = rememberPagerState(
            initialPage = uiState.initialAnswerPageIndex,
            pageCount = { uiState.answerList.size }
        )

        Dialog(
            onDismissRequest = {
                isShowAnswerListPagerDialog = false
            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                usePlatformDefaultWidth = false
            )
        ) {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .clickable(
                        onClick = { isShowAnswerListPagerDialog = false },
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    )
            ) {
                HorizontalPager(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 130.dp, bottom = 88.dp),
                    state = pagerState,
                    contentPadding = PaddingValues(horizontal = 25.5.dp),
                    pageSpacing = 14.dp
                ) { index ->
                    Box(
                        modifier = modifier
                            .clickable(
                                onClick = {
                                    navController.navigate("${MySpaceDestinations.CARD_DETAIL}/$index")
                                },
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            )
                    ) {
                        Column(
                            //                        modifier = modifier
                            //                            .padding(top = 130.dp, bottom = 88.dp, start = 25.dp, end = 25.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CalendarCardView(
                                modifier = modifier.weight(1f),
                                title = uiState.answerList[index].title,
                                subTitle = uiState.answerList[index].phrase,
                                emotion = getMediumEmotion(index = uiState.answerList[index].emotion),
                                emotionDesc = getEmotionText(index = uiState.answerList[index].emotion),
                                date = LocalDate.of(
                                    uiState.answerList[index].date[0],
                                    uiState.answerList[index].date[1],
                                    uiState.answerList[index].date[2]
                                ),
                                contents = uiState.answerList[index].content,
                            )
                            Spacer(modifier = Modifier.size(16.dp))

                            Row(
                                modifier = modifier
                                    .background(shape = RoundedCornerShape(100.dp), color = Gray500)
                                    .padding(vertical = 16.dp, horizontal = 9.5.dp)
                                    .clickable(
                                        onClick = {
                                            isShowShareBottomSheet = true
                                            selectedIndex = pagerState.currentPage
                                        },
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "수정/삭제",
                                    style = TellingmeTheme.typography.body2Bold.copy(
                                        color = Color.White,
                                        fontSize = 14.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (isShowShareBottomSheet) {
        BottomSheetDialog(
            onDismissRequest = { isShowShareBottomSheet = false },
            properties = BottomSheetDialogProperties(
                navigationBarProperties = NavigationBarProperties(navigationBarContrastEnforced = false),
                dismissOnClickOutside = false,
                behaviorProperties = BottomSheetBehaviorProperties(isDraggable = true)
            )
        ) {
            Column(
                modifier = modifier
                    .background(
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                        color = Base0
                    )
                    .padding(bottom = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = modifier.padding(vertical = 16.dp)) {
                    Box(
                        modifier = modifier
                            .background(
                                color = Gray300,
                                shape = RoundedCornerShape(100.dp)
                            )
                            .size(width = 32.dp, height = 4.dp)
                    )
                }

                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp, start = 4.dp, end = 4.dp, bottom = 8.dp)
                ) {
                    Text(
                        modifier = modifier
                            .fillMaxWidth()
                            .noRippleClickable {
                                val date = uiState.isAnsweredDateList[selectedIndex].toString()
                                if (uiState.isTodayAnswer) {
                                    navController.navigate(("${HomeDestinations.RECORD}/${date}/2"))   // 수정
                                } else {
                                    navController.navigate(("${HomeDestinations.RECORD}/${date}/1"))   // 쓰기
                                }
                            }
                            .padding(horizontal = 12.dp, vertical = 16.dp),
                        text = "수정하기",
                        style = TellingmeTheme.typography.body2Regular.copy(
                            color = Gray600,
                            fontSize = 14.sp
                        )
                    )
                    Text(
                        modifier = modifier
                            .fillMaxWidth()
                            .noRippleClickable {
                                viewModel.deleteAnswer(uiState.isAnsweredDateList[selectedIndex].toString())
                                isShowShareBottomSheet = false
                                isShowAnswerListPagerDialog = false
                            }
                            .padding(horizontal = 12.dp, vertical = 16.dp),
                        text = "삭제하기",
                        style = TellingmeTheme.typography.body2Regular.copy(
                            color = Gray600,
                            fontSize = 14.sp
                        )
                    )
                }
            }
        }
    }

    if (isShowEmptyDialog) {
        Dialog(
            onDismissRequest = {
                isShowEmptyDialog = false
            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                usePlatformDefaultWidth = false
            )
        ) {
            Surface(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 26.dp),
                shape = RoundedCornerShape(12.dp),
                color = Color.White
            ) {
                Column(
                    modifier = modifier
                        .padding(top = 130.dp, bottom = 144.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.image_answer_empty),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.size(24.dp))
                    Text(
                        modifier = modifier.padding(horizontal = 12.dp, vertical = 16.dp),
                        text = "이 날 작성한 답변이 없어요!",
                        style = TellingmeTheme.typography.body2Regular.copy(
                            color = Gray500,
                            fontSize = 14.sp
                        )
                    )
                }
            }
        }
    }

    viewModel.effect.collectWithLifecycle { effect ->
        when(effect) {
            is MySpaceContract.Effect.ScrollToToday -> {
                calendarPagerState.animateScrollToPage(
                    page = (uiState.today.year - CALENDAR_RANGE.startYear) * 12 + uiState.today.monthValue - 1,
                    animationSpec = spring(stiffness = 1000f)
                )
            }

            is MySpaceContract.Effect.ScrollToDate -> {
                calendarPagerState.animateScrollToPage(
                    page = (effect.year - CALENDAR_RANGE.startYear) * 12 + effect.month - 1,
                    animationSpec = spring(stiffness = 1000f)
                )
            }

            is MySpaceContract.Effect.ShowAnswerListPagerDialog -> {
                isShowAnswerListPagerDialog = true
            }

            is MySpaceContract.Effect.ShowAnswerEmptyDialog -> {
                isShowEmptyDialog = true
            }

        }
    }
}

object CALENDAR_RANGE {
    const val startYear = 2000
    const val lastYear = 2030
}

@Preview(showBackground = true)
@Composable
fun MySpaceScreenPreview() {
    MySpaceScreen(
        navController = rememberNavController()
    )
}

