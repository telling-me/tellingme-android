package com.tellingus.tellingme.presentation.ui.feature.otherspace.list

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tellingus.tellingme.R
import com.tellingus.tellingme.presentation.ui.common.component.appbar.BasicAppBar
import com.tellingus.tellingme.presentation.ui.common.component.button.FloatingButton
import com.tellingus.tellingme.presentation.ui.common.component.button.TellingmeIconButton
import com.tellingus.tellingme.presentation.ui.common.component.card.OpinionCard
import com.tellingus.tellingme.presentation.ui.common.component.chip.ChoiceChip
import com.tellingus.tellingme.presentation.ui.common.component.layout.MainLayout
import com.tellingus.tellingme.presentation.ui.common.component.section.QuestionSection
import com.tellingus.tellingme.presentation.ui.common.model.ButtonSize
import com.tellingus.tellingme.presentation.ui.common.model.ButtonState
import com.tellingus.tellingme.presentation.ui.common.navigation.MySpaceDestinations
import com.tellingus.tellingme.presentation.ui.common.navigation.OtherSpaceDestinations
import com.tellingus.tellingme.presentation.ui.theme.Background100
import com.tellingus.tellingme.presentation.ui.theme.Gray500
import com.tellingus.tellingme.presentation.ui.theme.Typography
import com.tellingus.tellingme.util.collectWithLifecycle
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun OtherSpaceListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    otherSpaceListViewModel: OtherSpaceListViewModel = hiltViewModel(),
    date: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
) {
    val uiState by otherSpaceListViewModel.uiState.collectAsStateWithLifecycle()

    MainLayout(
        header = {
            BasicAppBar(modifier = modifier
                .fillMaxWidth()
                .padding(start = 12.dp, top = 12.dp, bottom = 12.dp, end = 12.dp),
                leftSlot = {
                    TellingmeIconButton(iconRes = R.drawable.icon_caret_left,
                        size = ButtonSize.MEDIUM,
                        color = Gray500,
                        onClick = {
                            navController.popBackStack()
                        })
                })
        },
        content = {
            OtherSpaceListScreenContent(
                navController = navController,
                uiState,
                viewModel = otherSpaceListViewModel,
                date,

                )
        },
        isScrollable = false,
    )
}

private val buffer = 1

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OtherSpaceListScreenContent(
    navController: NavController,
    uiState: OtherSpaceListContract.State,
    viewModel: OtherSpaceListViewModel,
    date: String,
) {
    val communicationListData = uiState.communicationListData
    val questionData = uiState.questionData
    var isSelected by remember { mutableStateOf("recently") }

    val scope = rememberCoroutineScope()

    val lazyListState = rememberLazyListState()
    // observe list scrolling
    val reachedBottom: Boolean by remember {
        derivedStateOf {
            val lastVisibleItem = lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem?.index != 0 && lastVisibleItem?.index == lazyListState.layoutInfo.totalItemsCount - buffer
        }
    }

    // load more if scrolled to bottom
    LaunchedEffect(reachedBottom) {
        val TAG: String = "로그"
        Log.d(TAG, "LaunchedEffect date: $date")
        if (reachedBottom)  {
            var sort = ""
            if(isSelected == "recently") {
                sort = "최신순"
            }
            if(isSelected == "related") {
                sort = "관련순"
            }
            if(isSelected == "sympathy") {
                sort = "공감순"
            }
            viewModel.loadMoreDataIfNeeded(date = date, sort = sort)
        }
    }

    viewModel.effect.collectWithLifecycle {effect ->
            when(effect) {
                is OtherSpaceListContract.Effect.ScrollToTop -> {
                    lazyListState.scrollToItem(0)
                }
            }
    }

    Box(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 0.dp)
            .fillMaxHeight()
    ) {
        FloatingButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 0.dp, bottom = 20.dp)
                .zIndex(1f)
        ) {
            navController.navigate(
                ("${MySpaceDestinations.RECORD}/${uiState.questionData.title}/${uiState.questionData.phrase}")
            )
        }

        if (communicationListData.content.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "내용이 없어요", style = Typography.body2Bold)
            }
        } else {
            LazyColumn(
                state = lazyListState,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(top = 0.dp, bottom = 24.dp),
            ) {
                item {
                    QuestionSection(
                        title = questionData.title,
                        description = questionData.phrase,
                        isButtonVisible = false,
                        bgColor = Background100
                    )
                }

                stickyHeader {
                    Row(
                        modifier = Modifier
                            .padding(top = 0.dp, bottom = 16.dp)
                            .background(Background100)
                            .fillMaxWidth()
                    ) {
                        ChoiceChip(
                            selected = isSelected == "recently",
                            text = "최신순",
                            onClick = {

                                isSelected = "recently"
                                viewModel.processEvent(OtherSpaceListContract.Event.OnClickRecently(date))
                            }
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        ChoiceChip(
                            selected = isSelected == "related",
                            text = "관련순",
                            onClick = {
                                isSelected = "related"
                                viewModel.processEvent(OtherSpaceListContract.Event.OnClickRelative(date))
                            }
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        ChoiceChip(
                            selected = isSelected == "sympathy",
                            text = "공감순",
                            onClick = {
                                isSelected = "sympathy"
                                viewModel.processEvent(OtherSpaceListContract.Event.OnClickEmpathy(date))
                            }
                        )
                    }
                }

                items(items = communicationListData.content) { item ->
                    OpinionCard(
                        heartCount = item.likeCount,
                        buttonState = if (item.isLiked) ButtonState.ENABLED else ButtonState.DISABLED,
                        emotion = item.emotion,
                        description = item.content,
                        onClick = {
                            navController.navigate("${OtherSpaceDestinations.OTHER_SPACE}/detail/${item.answerId}?date=${date}")
                        },
                        onClickHeart = {
                            viewModel.processEvent(OtherSpaceListContract.Event.OnClickHeart(item.answerId))
                        }
                    )
                }

                // 로딩 중일 때 표시할 로딩 인디케이터
                item {
                    if (!uiState.isLastPage) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun OtherSpaceDetailScreenPreview() {
    OtherSpaceListScreen(navController = rememberNavController())
}