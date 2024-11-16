package com.tellingus.tellingme.presentation.ui.feature.home.userfeedback

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
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
import com.tellingus.tellingme.presentation.ui.common.navigation.HomeDestinations
import com.tellingus.tellingme.presentation.ui.feature.otherspace.detail.ComplaintBottomSheet
import com.tellingus.tellingme.presentation.ui.theme.Background100
import com.tellingus.tellingme.presentation.ui.theme.Gray100
import com.tellingus.tellingme.presentation.ui.theme.Gray200
import com.tellingus.tellingme.presentation.ui.theme.Gray500
import com.tellingus.tellingme.presentation.ui.theme.Gray600
import com.tellingus.tellingme.presentation.ui.theme.Gray700
import com.tellingus.tellingme.presentation.ui.theme.Typography
import kotlinx.coroutines.launch

@Composable
fun UserFeedbackScreen(navController: NavController) {


    MainLayout(
        header = { UserFeedbackScreenHeader { navController.navigate(HomeDestinations.HOME) } },
        content = {
            UserFeedbackScreenContent(navController = navController)
        })
}

@Composable
fun UserFeedbackScreenContent(navController: NavController) {
    val context = LocalContext.current
    var isChecked by remember {
        mutableStateOf(false)
    }
    var isInfoBottomSheetOpen by remember { mutableStateOf(false) }

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
                Image(painter = painterResource(id = R.drawable.icon_info),
                    contentDescription = "",
                    modifier = Modifier.clickable { isInfoBottomSheetOpen = true })
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "의견을 반영해서 더 좋은 질문을 드리고 싶어요!", style = Typography.body2Bold, color = Gray600)

            Spacer(modifier = Modifier.height(36.dp))

            PrimaryButton(modifier = Modifier.fillMaxWidth(),
                size = ButtonSize.LARGE,
                text = "좋았어요!",
                onClick = {
                    val intent =
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://walla.my/v/dZcLYuN8kgVeh5iZFgdb")
                        )
                    context.startActivity(intent)
//                    navController.navigate(HomeDestinations.USER_FEEDBACK_GOOD)
                })
            Spacer(modifier = Modifier.height(20.dp))
            PrimaryLightButton(modifier = Modifier.fillMaxWidth(),
                size = ButtonSize.LARGE,
                text = "아쉬워요..",
                onClick = {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://walla.my/v/dJFQAPUTccxEDCaG3KkW")
                    )
                    context.startActivity((intent))
//                    navController.navigate(HomeDestinations.USER_FEEDBACK_BAD)
                })

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
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()
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

    if (isInfoBottomSheetOpen) {
        InfoBottomSheet(
            onConfirm = { isInfoBottomSheetOpen = false },
            onDismiss = { isInfoBottomSheetOpen = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoBottomSheet(onConfirm: () -> Unit, onDismiss: () -> Unit = {}) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        modifier = Modifier.heightIn(min = 300.dp)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = "오늘의 질문",
            style = Typography.body1Bold,
            color = Gray600
        )

        Column(
            modifier = Modifier
                .padding(vertical = 12.dp, horizontal = 16.dp)
                .background(Background100, shape = RoundedCornerShape(8.dp))
                .fillMaxWidth()
        ) {
            Text(
                text = "텔링미를 사용하실 때\n" + "어떤 기분과 생각을 하시나요?",
                style = Typography.body2Regular,
                color = Gray700
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "하루 한번 질문에 답변하며 나를 깨닫는 시간", style = Typography.body2Regular, color = Gray500
            )

        }
        Spacer(modifier = Modifier.height(16.dp))
        PrimaryButton(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
            size = ButtonSize.LARGE,
            text = "확인",
            onClick = { onConfirm() })
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