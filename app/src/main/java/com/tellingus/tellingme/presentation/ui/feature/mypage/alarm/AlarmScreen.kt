package com.tellingus.tellingme.presentation.ui.feature.mypage.alarm

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tellingus.tellingme.R
import com.tellingus.tellingme.presentation.ui.common.component.appbar.BasicAppBar
import com.tellingus.tellingme.presentation.ui.common.component.button.TellingmeIconButton
import com.tellingus.tellingme.presentation.ui.common.component.chip.ActionChip
import com.tellingus.tellingme.presentation.ui.common.component.layout.MainLayout
import com.tellingus.tellingme.presentation.ui.common.model.ButtonSize
import com.tellingus.tellingme.presentation.ui.theme.Gray500
import com.tellingus.tellingme.presentation.ui.theme.Gray600
import com.tellingus.tellingme.presentation.ui.theme.Primary100
import com.tellingus.tellingme.presentation.ui.theme.Error600
import com.tellingus.tellingme.presentation.ui.theme.TellingmeTheme
import com.tellingus.tellingme.presentation.ui.theme.Typography
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun AlarmScreen(
    navController: NavController,
    viewModel: AlarmViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MainLayout(
        header = { AlarmScreenHeader { navController.popBackStack() } },
        content = { AlarmScreenContent(uiState = uiState, viewModel = viewModel) },
        isScrollable = false,
        background = Color.White
    )
}

@Composable
fun AlarmScreenHeader(navigateToPreviousScreen: () -> Unit) {
    BasicAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, top = 5.dp, bottom = 5.dp, end = 10.dp),
        leftSlot = {
            TellingmeIconButton(iconRes = R.drawable.icon_caret_left,
                size = ButtonSize.MEDIUM,
                color = Gray500,
                onClick = { navigateToPreviousScreen() })
        },
    )
}

@Composable
fun AlarmScreenContent(
    uiState: AlarmContract.State, viewModel: AlarmViewModel
) {
    val isLoading = uiState.isLoading
    val list = uiState.list;

    val TAG: String = "로그"
    Log.d(TAG, " - AlarmScreenContent() called $list")

    val hasUnread = list.any { !it.isRead }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "알림")

            ActionChip(
                onClick = {
                    if (hasUnread) {
                        viewModel.processEvent(AlarmContract.Event.OnClickTotalRead)
                    }
                },
                text = "전체 읽음",
                hasArrow = false
            )

        }
        if (list.isEmpty()) {
            // 알림이 없을 경우
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.my_teller_badge_empty),
                    contentDescription = ""
                )
                Text(text = "아직 받은 알림이 없어요", style = Typography.body2Bold)
            }
        } else {
            // 알림이 있을 경우
            LazyColumn {
                items(items = list) {it->
                    val date = "${it.createdAt[0]}-${it.createdAt[1]}-${it.createdAt[2]}";
                    val parsedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-M-d")).format(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    AlarmCard(
                        alarmType = "",
                        title = it.title,
                        content = it.content ?: "",
                        date = parsedDate,
                        isRead = it.isRead,
                        onClick = {
                            viewModel.processEvent(AlarmContract.Event.OnClickItemRead(it.noticeId))
                        },
                        onDelete = {
                            viewModel.processEvent(AlarmContract.Event.OnClickItemDelete(it.noticeId))
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun AlarmCard(
    alarmType: String,
    title: String,
    content: String = "",
    date: String,
    isRead: Boolean = false,
    onClick: () -> Unit = {},
    onDelete: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val swipeableState = rememberSwipeableState(initialValue = 0)
    val coroutineScope = rememberCoroutineScope()
    val squareSize = 80.dp
    val sizePx = with(LocalDensity.current) { squareSize.toPx() }
    val anchors = mapOf(0f to 0, -sizePx to 1)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .background(Color.White)
            .swipeable(
                state = swipeableState,
                orientation = Orientation.Horizontal,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                velocityThreshold = 1000.dp
            )
    ) {
        Box(
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            TextButton(
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .background(Error600),
                onClick = {
                    coroutineScope.launch {
                        swipeableState.animateTo(0, tween(600, 0))
                        onDelete()
                    }
                }) {
                Text(text = "삭제", color = Color.White)
            }
        }


        Box(modifier = Modifier
            .offset {
                IntOffset(
                    swipeableState.offset.value.roundToInt(), 0
                )
            }
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White)

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 12.dp, start = 20.dp, end = 20.dp)
                    .alpha(if (isRead === true || isPressed === true) 0.5f else 1f)
                    .clickable(
                        interactionSource = interactionSource, indication = null, onClick = onClick
                    )
            ) {
                Text(
                    text = getAlarmCardTypeText("alarm"),
                    color = Primary100,
                    style = TellingmeTheme.typography.caption1Bold,
                )
                Text(
                    text = title,
                    color = Gray600,
                    style = TellingmeTheme.typography.body2Bold,
                    modifier = Modifier.padding(top = 4.dp)
                )
                if (content !== "") {
                    Text(
                        text = content,
                        color = Gray600,
                        style = TellingmeTheme.typography.body2Regular,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                Text(
                    text = date,
                    color = Gray600,
                    style = TellingmeTheme.typography.caption1Regular,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }

}

fun getAlarmCardTypeText(alarmType: String): String {
    val value = mapOf(
        "alarm" to "알림", "event" to "이벤트", "notice" to "공지"
    )
    return value[alarmType] ?: alarmType
}

data class AlarmItem(
    val id: Int,
    val alarmType: String,
    val title: String,
    val content: String = "",
    val date: String,
    val isRead: Boolean = false,
)

@Preview
@Composable
fun AlarmScreenPreview() {
    AlarmScreen(navController = rememberNavController())
}