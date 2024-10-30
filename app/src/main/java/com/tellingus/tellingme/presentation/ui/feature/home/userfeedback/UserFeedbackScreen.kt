package com.tellingus.tellingme.presentation.ui.feature.home.userfeedback

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tellingus.tellingme.R
import com.tellingus.tellingme.presentation.ui.common.component.appbar.BasicAppBar
import com.tellingus.tellingme.presentation.ui.common.component.box.CheckBox
import com.tellingus.tellingme.presentation.ui.common.component.button.PrimaryButton
import com.tellingus.tellingme.presentation.ui.common.component.button.PrimaryLightButton
import com.tellingus.tellingme.presentation.ui.common.component.button.TellingmeIconButton
import com.tellingus.tellingme.presentation.ui.common.component.layout.MainLayout
import com.tellingus.tellingme.presentation.ui.common.model.ButtonSize
import com.tellingus.tellingme.presentation.ui.theme.Gray500
import com.tellingus.tellingme.presentation.ui.theme.Gray600
import com.tellingus.tellingme.presentation.ui.theme.Typography

@Composable
fun UserFeedbackScreen(navController: NavController) {
    MainLayout(header = { UserFeedbackScreenHeader { navController.popBackStack() } },
        content = { UserFeedbackScreenContent() })
}

@Composable
fun UserFeedbackScreenContent() {
    var isChecked by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp, top = 114.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            Image(painter = painterResource(id = R.drawable.feedback_duey), contentDescription = "")
            Spacer(modifier = Modifier.height(20.dp))
            Row {
                Text(text = "오늘 질문은 어땠나요?", style = Typography.head3Bold, color = Gray600)
                Spacer(modifier = Modifier.width(4.dp))
                Image(painter = painterResource(id = R.drawable.icon_info), contentDescription = "")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "의견을 반영해서 더 좋은 질문을 드리고 싶어요!", style = Typography.body2Bold, color = Gray600)

            Spacer(modifier = Modifier.height(36.dp))

            PrimaryButton(modifier = Modifier.fillMaxWidth(),
                size = ButtonSize.LARGE,
                text = "좋았어요!",
                onClick = { /*TODO*/ })
            Spacer(modifier = Modifier.height(20.dp))
            PrimaryLightButton(modifier = Modifier.fillMaxWidth(),
                size = ButtonSize.LARGE,
                text = "아쉬워요..",
                onClick = {})

            Spacer(modifier = Modifier.height(24.dp))
            CheckBox(
                text = "오늘 다시 보지 않기",
                onClick = { isChecked = !isChecked },
                isSelected = isChecked,
                horizontalArrangement = Arrangement.Center
            )
        }

        Spacer(modifier = Modifier.height(114.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "직접 질문을 제안하고 싶다면",
                style = Typography.caption1Regular,
                color = Gray600,
                textAlign = TextAlign.Center
            )
            Row() {
                Text(
                    text = "마이페이지 -",
                    style = Typography.caption1Regular,
                    color = Gray600,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "듀이의 질문 제작소",
                    style = Typography.caption1Bold,
                    color = Gray500,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "를 찾아주세요!",
                    style = Typography.caption1Regular,
                    color = Gray600,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun UserFeedbackScreenHeader(navigateToPreviousScreen: () -> Unit) {
    BasicAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, top = 5.dp, bottom = 5.dp, end = 10.dp),
        rightSlot = {
            TellingmeIconButton(iconRes = R.drawable.icon_close,
                size = ButtonSize.MEDIUM,
                color = Gray500,
                onClick = { navigateToPreviousScreen() })
        },
        centerSlot = {
            Text("소중한 피드백", style = Typography.body1Bold, color = Gray600)
        },
    )

}