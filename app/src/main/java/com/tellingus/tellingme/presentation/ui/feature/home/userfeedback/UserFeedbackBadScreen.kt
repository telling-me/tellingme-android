package com.tellingus.tellingme.presentation.ui.feature.home.userfeedback

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tellingus.tellingme.R
import com.tellingus.tellingme.presentation.ui.common.component.appbar.BasicAppBar
import com.tellingus.tellingme.presentation.ui.common.component.button.PrimaryButton
import com.tellingus.tellingme.presentation.ui.common.component.button.TellingmeIconButton
import com.tellingus.tellingme.presentation.ui.common.component.layout.MainLayout
import com.tellingus.tellingme.presentation.ui.common.model.ButtonSize
import com.tellingus.tellingme.presentation.ui.theme.Gray300
import com.tellingus.tellingme.presentation.ui.theme.Gray500
import com.tellingus.tellingme.presentation.ui.theme.Gray600
import com.tellingus.tellingme.presentation.ui.theme.Gray7
import com.tellingus.tellingme.presentation.ui.theme.Gray800
import com.tellingus.tellingme.presentation.ui.theme.Side200
import com.tellingus.tellingme.presentation.ui.theme.Side300
import com.tellingus.tellingme.presentation.ui.theme.TellingmeTheme
import com.tellingus.tellingme.presentation.ui.theme.Typography

@Composable
fun UserFeedbackBadScreen(navController: NavController) {
    MainLayout(
        header = { UserFeedbackBadScreenHeader(navController = navController) },
        content = {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 72.dp)
                ) {
                    UserFeedbackBadContent(navController = navController)
                }
                PrimaryButton(
                    size = ButtonSize.LARGE,
                    text = "제출하기",
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(16.dp) // 버튼 주변 여백
                )
            }
        })
}

@Composable
fun UserFeedbackBadContent(navController: NavController) {
    var textValue by remember { mutableStateOf("") }

    Column {
        Spacer(modifier = Modifier.height(42.dp))
        Column(modifier = Modifier.padding(horizontal = 25.dp)) {
            Text(text = "더 나은 텔링미가 될 수 있도록\n그 이유를 알려주세요!")
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "다중 선택 가능")
        }
        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier.padding(horizontal = 25.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BadFeedbackType.getAllFeedbackTypes().forEach {
                Row(
                    modifier = Modifier
                        .background(
                            shape = RoundedCornerShape(16.dp), color = Side200
                        )
                        .padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = it.label,
                        color = Gray7,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .width(325.dp)
                            .heightIn(max = 48.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column(modifier = Modifier.padding(horizontal = 25.dp)) {
            Text(text = "그 외 하고 싶은 말을\n자유롭게 적어주세요.")
            Spacer(modifier = Modifier.height(16.dp))

            BasicTextField(modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 190.dp)
                .background(shape = RoundedCornerShape(16.dp), color = Side200)
                .padding(16.dp),
                value = textValue,
                onValueChange = {
                    if (it.length <= 500) {
                        textValue = it
//                        viewModel.updateAnswer(it)
                    }
                },
                textStyle = TellingmeTheme.typography.body2Regular.copy(
                    color = Gray800, lineHeight = 24.sp, textAlign = TextAlign.Start
                ),
                decorationBox = { innerTextField ->
                    Box {
                        if (textValue.isBlank()) {
                            Text(
                                text = "500자 이내",
                                style = TellingmeTheme.typography.body2Regular.copy(
                                    color = Gray300
                                )
                            )
                        }
                        innerTextField()
                    }
                })
        }
    }
}

@Composable
fun UserFeedbackBadScreenHeader(navController: NavController) {
    BasicAppBar(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 12.dp, top = 5.dp, bottom = 5.dp, end = 10.dp), leftSlot = {
        TellingmeIconButton(iconRes = R.drawable.icon_caret_left,
            size = ButtonSize.MEDIUM,
            color = Gray500,
            onClick = {
                navController.popBackStack()
            })
    }, centerSlot = {
        androidx.compose.material3.Text(
            "소중한 피드백", style = Typography.body1Bold, color = Gray600
        )
    }, rightSlot = {
        TellingmeIconButton(iconRes = R.drawable.icon_close,
            size = ButtonSize.MEDIUM,
            color = Gray500,
            onClick = {
                navController.popBackStack()
            })
    })
}

sealed class BadFeedbackType(val label: String, val value: String) {
    object TooBoring : BadFeedbackType("질문이 너무 식상해요", "too_boring")
    object HardToUnderstand : BadFeedbackType("질문을 이해하기 어려워요", "hard_to_understand")
    object Awkward : BadFeedbackType("질문 또는 아래 문구가 어색했어요", "awkward")
    object DifficultToAnswer : BadFeedbackType("답변을 작성할 때 너무 막막했어요", "difficult_to_answer")
    object NotHelpful : BadFeedbackType("질문이 나를 알아가는 데 도움이 되지 않아요", "not_helpful")
    object Other : BadFeedbackType("기타", "other")

    companion object {
        fun getAllFeedbackTypes() = listOf(
            TooBoring, HardToUnderstand, Awkward, DifficultToAnswer, NotHelpful, Other
        )
    }
}