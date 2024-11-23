package com.tellingus.tellingme.presentation.ui.feature.otherspace.list

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.tellingus.tellingme.presentation.ui.common.navigation.OtherSpaceDestinations
import com.tellingus.tellingme.presentation.ui.theme.Background100
import com.tellingus.tellingme.presentation.ui.theme.Gray500
import com.tellingus.tellingme.presentation.ui.theme.Typography
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OtherSpaceListScreenContent(
    navController: NavController,
    uiState: OtherSpaceListContract.State,
    viewModel: OtherSpaceListViewModel,
    date: String,
) {
    var isSelected by remember { mutableStateOf("recently") }

    val lazyListState = rememberLazyListState() // LazyListState를 사용하여 스크롤 상태를 관리
    val communicationListData =
        uiState.communicationListData // ViewModel에서 communicationListData를 관찰
    var isLoading by remember { mutableStateOf(false) } // 로딩 상태 관리
    var isLastPage by remember { mutableStateOf(false) } // 마지막 페이지 상태 관리
    val questionData = uiState.questionData



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
        ) {}


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
                contentPadding = PaddingValues(
                    top = 0.dp, bottom = 24.dp
                )
            ) {
                item {
                    QuestionSection(
                        title = "${questionData.title}",
                        description = "${questionData.phrase}",
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
                        ChoiceChip(selected = isSelected == "recently", text = "최신순", onClick = {
                            isSelected = "recently"
                            viewModel.processEvent(OtherSpaceListContract.Event.OnClickRecently(date))
                        })

                        Spacer(modifier = Modifier.width(8.dp))

                        ChoiceChip(selected = isSelected == "related", text = "관련순", onClick = {
                            isSelected = "related"
                            viewModel.processEvent(OtherSpaceListContract.Event.OnClickRelative(date))
                        })

                        Spacer(modifier = Modifier.width(8.dp))

                        ChoiceChip(selected = isSelected == "sympathy", text = "공감순", onClick = {
                            isSelected = "sympathy"
                            viewModel.processEvent(OtherSpaceListContract.Event.OnClickEmpathy(date))
                        })
                    }
                }
                items(items = communicationListData.content) {
                    OpinionCard(
                        heartCount = it.likeCount,
                        buttonState = if (it.isLiked) ButtonState.ENABLED else ButtonState.DISABLED,
                        emotion = it.emotion,
                        description = it.content,
                        onClick = {
                            navController.navigate("${OtherSpaceDestinations.OTHER_SPACE}/detail/${it.answerId}")
                        }
                    )
                }
                // 로딩 중일 때 로딩 표시
                item {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
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

data class OpinionItem(
    val id: Int,
    val heartCount: Int,
    val buttonState: ButtonState,
    val feeling: String,
    val description: String
)
