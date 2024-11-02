package com.tellingus.tellingme.presentation.ui.feature.home.userfeedback

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.tellingus.tellingme.presentation.ui.theme.Error400
import com.tellingus.tellingme.presentation.ui.theme.Gray300
import com.tellingus.tellingme.presentation.ui.theme.Gray500
import com.tellingus.tellingme.presentation.ui.theme.Gray600
import com.tellingus.tellingme.presentation.ui.theme.Gray7
import com.tellingus.tellingme.presentation.ui.theme.Gray800
import com.tellingus.tellingme.presentation.ui.theme.Side200
import com.tellingus.tellingme.presentation.ui.theme.Side300
import com.tellingus.tellingme.presentation.ui.theme.Side500
import com.tellingus.tellingme.presentation.ui.theme.TellingmeTheme
import com.tellingus.tellingme.presentation.ui.theme.Typography

@Composable
fun UserFeedbackGoodScreen(navController: NavController) {
    MainLayout(
        header = { UserFeedbackGoodScreenHeader(navController = navController) },
        content = {
            Column() {
                UserFeedbackGoodContent(navController = navController)
                PrimaryButton(
                    size = ButtonSize.LARGE,
                    text = "제출하기",
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp) // 버튼 주변 여백
                )
            }
        })
}

@Composable
fun UserFeedbackGoodContent(navController: NavController) {
    var textValue by remember { mutableStateOf("") }
    var slider1Value by remember { mutableFloatStateOf(0f) }
    var slider2Value by remember { mutableFloatStateOf(0f) }
    var slider3Value by remember { mutableFloatStateOf(0f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(42.dp))
        Column(modifier = Modifier.padding(horizontal = 25.dp)) {
            Text(text = "더 나은 텔링미가 될 수 있도록\n3가지 질문에 답해주세요!")
        }
        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier
                .padding(horizontal = 25.dp),
            verticalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            Column {
                Column() {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(
                                shape = RoundedCornerShape(99.dp),
                                color = Side500
                            )
                            .size(20.dp)
                    ) {
                        Text(
                            text = "1",
                            color = Color.White, // 텍스트 색상 변경 (필요에 따라)
                            fontSize = 12.sp // 텍스트 크기 설정 (필요에 따라)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "질문과 아래 문구가")
                    Row() {
                        Text(text = "자연스럽게 연결되나요?")
                        Text(text = "*", color = Error400)
                    }
                }
                Slider(
                    steps = 4,
                    valueRange = 0f..5f,
                    value = slider1Value,
                    onValueChange = { slider1Value = it }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "그렇지 않다")
                    Text(text = "그렇다")
                }
            }

            Column {
                Column() {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(
                                shape = RoundedCornerShape(99.dp),
                                color = Side500
                            )
                            .size(20.dp)
                    ) {
                        Text(
                            text = "2",
                            color = Color.White, // 텍스트 색상 변경 (필요에 따라)
                            fontSize = 12.sp // 텍스트 크기 설정 (필요에 따라)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "스스로에 대해 생각할")
                    Row() {
                        Text(text = "수 있는 질문이었나요?")
                        Text(text = "*", color = Error400)
                    }
                }
                Slider(
                    steps = 4,
                    valueRange = 0f..5f,
                    value = slider2Value,
                    onValueChange = { slider2Value = it }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "그렇지 않다")
                    Text(text = "그렇다")
                }
            }

            Column {
                Column() {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(
                                shape = RoundedCornerShape(99.dp),
                                color = Side500
                            )
                            .size(20.dp)
                    ) {
                        Text(
                            text = "3",
                            color = Color.White, // 텍스트 색상 변경 (필요에 따라)
                            fontSize = 12.sp // 텍스트 크기 설정 (필요에 따라)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "답변을 작성할 때")
                    Row() {
                        Text(text = "어렵거나 막막했나요?")
                        Text(text = "*", color = Error400)
                    }
                }
                Slider(
                    colors = SliderDefaults.colors(
                        thumbColor = Color.White,
                        ),
                    steps = 4,
                    valueRange = 0f..5f,
                    value = slider3Value,
                    onValueChange = { slider3Value = it }
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "그렇지 않다")
                    Text(text = "그렇다")
                }
            }

        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(modifier = Modifier.padding(horizontal = 25.dp)) {
            Column() {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(
                            shape = RoundedCornerShape(99.dp),
                            color = Side500
                        )
                        .size(20.dp)
                ) {
                    Text(
                        text = "4",
                        color = Color.White, // 텍스트 색상 변경 (필요에 따라)
                        fontSize = 12.sp // 텍스트 크기 설정 (필요에 따라)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "그 외 하고 싶은 말을\n자유롭게 적어주세요.")
            }

            Spacer(modifier = Modifier.height(16.dp))

            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 190.dp)
                    .background(shape = RoundedCornerShape(16.dp), color = Side200)
                    .padding(16.dp),
                value = textValue,
                onValueChange = {
                    if (it.length <= 500) {
                        textValue = it
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
                }
            )
        }
    }
}

@Composable
fun UserFeedbackGoodScreenHeader(navController: NavController) {
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
        Text(
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