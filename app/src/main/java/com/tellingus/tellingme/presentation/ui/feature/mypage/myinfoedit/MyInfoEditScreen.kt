package com.tellingus.tellingme.presentation.ui.feature.mypage.myinfoedit

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tellingus.tellingme.R
import com.tellingus.tellingme.presentation.ui.common.component.appbar.BasicAppBar
import com.tellingus.tellingme.presentation.ui.common.component.button.SingleButton
import com.tellingus.tellingme.presentation.ui.common.component.button.TellingmeIconButton
import com.tellingus.tellingme.presentation.ui.common.component.chip.ChoiceChip
import com.tellingus.tellingme.presentation.ui.common.component.layout.MainLayout
import com.tellingus.tellingme.presentation.ui.common.model.ButtonSize
import com.tellingus.tellingme.presentation.ui.theme.Gray200
import com.tellingus.tellingme.presentation.ui.theme.Gray300
import com.tellingus.tellingme.presentation.ui.theme.Gray50
import com.tellingus.tellingme.presentation.ui.theme.Gray500
import com.tellingus.tellingme.presentation.ui.theme.Gray600
import com.tellingus.tellingme.presentation.ui.theme.Primary400
import com.tellingus.tellingme.presentation.ui.theme.TellingmeTheme
import com.tellingus.tellingme.presentation.ui.theme.Typography

@Composable
fun MyInfoEditScreen(navController: NavController) {
    MainLayout(header = { MyInfoEditScreenHeader(navController) },
        content = { MyInfoEditScreenContent() })
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MyInfoEditScreenContent() {
    var isFocused by remember { mutableStateOf(false) }
    var nickname by remember { mutableStateOf("") }

    var selectedConcerns by remember { mutableStateOf<List<ConcernType>>(emptyList()) }

    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
    ) {
        FormHelper(title = "닉네임(2-8글자 이내)") {
            BasicTextField(modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    isFocused = it.isFocused
                }, value = nickname, onValueChange = {
                nickname = it

            }, textStyle = TellingmeTheme.typography.body1Regular.copy(
                color = Gray600, textAlign = TextAlign.Start
            ), maxLines = 1, singleLine = true, decorationBox = { innerTextField ->
                Column {
                    Box {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            innerTextField()
                            androidx.compose.material.Text(
                                text = if (nickname.isBlank()) "닉네임을 입력해주세요." else " ",
                                style = TellingmeTheme.typography.body1Regular.copy(
                                    color = Gray300
                                )
                            )
                            if (nickname.isNotBlank()) {
                                Icon(
                                    modifier = Modifier
                                        .align(Alignment.CenterEnd)
                                        .clickable(interactionSource = remember { MutableInteractionSource() },
                                            indication = null,
                                            onClick = {
                                                nickname = ""
//                                                onChangeTextField(contents)
                                            }),
                                    imageVector = ImageVector.vectorResource(id = R.drawable.icon_clear_text),
                                    contentDescription = null,
                                    tint = Gray300
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(if (isFocused) Primary400 else Gray200)
                            .height(1.dp)
                    )

                }
            })
            Text(
                text = "영문, 숫자, 띄어쓰기, 특수문자 불가", style = Typography.caption1Regular, color = Gray500
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
        FormHelper(title = "고민", subTitle = "최대 2가지 중복 선택 가능") {
            FlowRow(
                maxItemsInEachRow = 3,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // 각 ChoiceChip에 대해 클릭 시 선택 상태를 업데이트합니다.
                listOf(
                    ConcernType.Study,
                    ConcernType.Relationship,
                    ConcernType.Personality,
                    ConcernType.Habits,
                    ConcernType.Health,
                    ConcernType.Others
                ).forEach { concernType ->
                    ChoiceChip(
                        text = concernType.label,
                        onClick = {
                            // 현재 선택된 고민에 concernType이 이미 존재하면 제거하고, 없으면 추가합니다.
                            if (selectedConcerns.contains(concernType)) {
                                selectedConcerns = selectedConcerns.filterNot { it == concernType }
                            } else {
                                if (selectedConcerns.size < 2) { // 최대 2개까지 선택 가능
                                    selectedConcerns = selectedConcerns + concernType
                                }
                            }
                        },
                        selected = selectedConcerns.contains(concernType)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
        FormHelper(title = "직업") {
            DropdownSelect(text = "dd")
        }

        Spacer(modifier = Modifier.height(32.dp))
        FormHelper(title = "성별") {
            Text(text = "여성", color = Gray500, style = Typography.body1Regular)
        }

        Spacer(modifier = Modifier.height(32.dp))
        FormHelper(title = "출생년도") {
            Text(text = "1990년", color = Gray500, style = Typography.body1Regular)
        }

        Spacer(modifier = Modifier.height(32.dp))
        FormHelper(title = "mbti") {
            DropdownSelect(text = "mbti선택")
        }

    }
}

@Composable
fun FormHelper(
    modifier: Modifier = Modifier,
    title: String = "",
    subTitle: String = "",
    content: @Composable () -> Unit
) {
    Column(modifier = modifier) {
        Text(text = title, style = Typography.body1Bold, color = Gray600)
        if (subTitle !== "") {
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = subTitle, style = Typography.caption1Regular, color = Gray500)
        }
        Spacer(modifier = Modifier.height(12.dp))
        content()
    }
}

@Composable
fun DropdownSelect(text: String = "", modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(vertical = 12.dp)
            .height(56.dp)
            .background(Gray50, shape = RoundedCornerShape(12.dp))
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(start = 12.dp), text = text,
            style = Typography.body2Regular,
            color = Gray600
        )
        Image(
            modifier = Modifier.padding(end = 12.dp),
            painter = painterResource(id = R.drawable.icon_caret_down),
            contentDescription = "icon_caret_down"
        )
    }

}

@Composable
fun MyInfoEditScreenHeader(navController: NavController) {
    BasicAppBar(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 12.dp, top = 5.dp, bottom = 5.dp, end = 10.dp), leftSlot = {
        TellingmeIconButton(iconRes = R.drawable.icon_caret_left,
            size = ButtonSize.MEDIUM,
            color = Gray500,
            onClick = {
                navController.popBackStack()
            })
    }, rightSlot = {
        Row {
            SingleButton(size = ButtonSize.LARGE, text = "완료", onClick = {

            })
        }
    })
}

sealed class ConcernType(val label: String, val value: String) {
    object Study : ConcernType("학업 / 진로", "study")
    object Relationship : ConcernType("대인관계", "relationship")
    object Personality : ConcernType("성격 / 가치관", "personality")
    object Habits : ConcernType("행동 / 습관", "habits")
    object Health : ConcernType("건강", "health")
    object Others : ConcernType("기타", "others")
}

// TODO: 액션시트 만들기
sealed class JobType(val label: String, val value: String) {
    object Student : JobType("중·고등학생", "student")
    object Academic : JobType("대학(원)생", "academic")
}