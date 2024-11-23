package com.tellingus.tellingme.presentation.ui.feature.mypage.mylog

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.tellingus.tellingme.R
import com.tellingus.tellingme.presentation.ui.common.component.appbar.BasicAppBar
import com.tellingus.tellingme.presentation.ui.common.component.button.TellingmeIconButton
import com.tellingus.tellingme.presentation.ui.common.component.layout.MainLayout
import com.tellingus.tellingme.presentation.ui.common.const.getMediumEmotion
import com.tellingus.tellingme.presentation.ui.common.model.ButtonSize
import com.tellingus.tellingme.presentation.ui.feature.mypage.MyPageContract
import com.tellingus.tellingme.presentation.ui.feature.mypage.MyPageViewModel
import com.tellingus.tellingme.presentation.ui.feature.myspace.MySpaceContract
import com.tellingus.tellingme.presentation.ui.feature.myspace.MySpaceViewModel
import com.tellingus.tellingme.presentation.ui.theme.Gray100
import com.tellingus.tellingme.presentation.ui.theme.Gray500
import com.tellingus.tellingme.presentation.ui.theme.Gray600
import com.tellingus.tellingme.presentation.ui.theme.Primary400
import com.tellingus.tellingme.presentation.ui.theme.Typography

@Composable
fun MyLogScreen(navController: NavController, viewModel: MyLogViewModel = hiltViewModel()) {


    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MainLayout(
        header = { MyLogScreenHeader { navController.popBackStack() } },
        content = { MyLogScreenContent(uiState) },
        isScrollable = false,
        background = Color.White
    )
}

@Composable
fun MyLogScreenContent(uiState: MyLogContract.State) {

    val answerList = uiState.answerList
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
    ) {

        if (answerList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "나의 기록이 없어요", style = Typography.body2Bold)
            }
        } else {
            LazyColumn() {
                items(items = answerList) { it ->
                    MyLogCard(
                        spareTitle = it.spareTitle,
                        date = "${it.date[0]}-${it.date[1]}-${it.date[2]}",
                        emotion = it.emotion
                    )
                }
            }
        }


    }
}

@Composable
fun MyLogCard(spareTitle: String, date: String, emotion: Int) {
    var isPressed by remember { mutableStateOf(false) }
    // 크기 애니메이션
    val scale by animateFloatAsState(if (isPressed) 0.96f else 1f, label = "scale")
    // 배경색 애니메이션
    val backgroundColor by animateColorAsState(
        if (isPressed) Gray100 else Color.White, label = "backgroundColor"
    )

    Row(modifier = Modifier
        .fillMaxWidth()
        .height(80.dp)
        .background(backgroundColor) // 배경색 적용
        .graphicsLayer(scaleX = scale, scaleY = scale) // 스케일 적용
        .pointerInput(Unit) {
            detectTapGestures(onPress = {
                isPressed = true // 눌림 상태로 변경
                tryAwaitRelease() // 터치 해제될 때까지 대기
                isPressed = false // 터치 해제 후 원래 상태로 변경
            })
        }) {
        Image(painter = painterResource(getMediumEmotion(emotion)), contentDescription = "")
        Spacer(modifier = Modifier.width(16.dp))
        Column() {
            Text(
                text = "$spareTitle",
                style = Typography.body2Bold,
                color = Gray600,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "$date", style = Typography.caption1Regular, color = Gray600)
        }
    }
}

@Composable
fun MyLogScreenHeader(navigateToPreviousScreen: () -> Unit) {
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
        centerSlot = {
            Text("나의 기록 모음", style = Typography.body1Bold, color = Gray600)
        },
    )
}

data class MyLogItem(
    val id: Int,
    val title: String,
    val date: String,
)

var dummyList = listOf(
    MyLogItem(id = 1, title = "텔링미를 사용하실 때 어떤 기분과 생각을 abcdefg", date = "2024.11.29"),
    MyLogItem(id = 2, title = "2텔링미를 사용하실 때 어떤 기분과 생각을 abcdefg", date = "2024.11.29"),
    MyLogItem(id = 3, title = "3텔링미를 사용하실 때 어떤 기분과 생각을 abcdefg", date = "2024.11.29"),
    MyLogItem(id = 4, title = "4텔링미를 사용하실 때 어떤 기분과 생각을 abcdefg", date = "2024.11.29"),
    MyLogItem(id = 5, title = "5텔링미를 사용하실 때 어떤 기분과 생각을 abcdefg", date = "2024.11.29"),
    MyLogItem(id = 6, title = "6텔링미를 사용하실 때 어떤 기분과 생각을 abcdefg", date = "2024.11.29"),
    MyLogItem(id = 7, title = "7텔링미를 사용하실 때 어떤 기분과 생각을 abcdefg", date = "2024.11.29"),
    MyLogItem(id = 8, title = "8텔링미를 사용하실 때 어떤 기분과 생각을 abcdefg", date = "2024.11.29"),
    MyLogItem(id = 9, title = "9텔링미를 사용하실 때 어떤 기분과 생각을 abcdefg", date = "2024.11.29"),
    MyLogItem(id = 10, title = "10텔링미를 사용하실 때 어떤 기분과 생각을 abcdefg", date = "2024.11.29"),
    MyLogItem(id = 11, title = "11텔링미를 사용하실 때 어떤 기분과 생각을 abcdefg", date = "2024.11.29"),
    MyLogItem(id = 12, title = "12텔링미를 사용하실 때 어떤 기분과 생각을 abcdefg", date = "2024.11.29")
)