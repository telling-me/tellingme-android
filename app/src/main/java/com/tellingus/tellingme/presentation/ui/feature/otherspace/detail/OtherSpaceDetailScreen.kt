package com.tellingus.tellingme.presentation.ui.feature.otherspace.detail

import android.text.style.AlignmentSpan
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tellingus.tellingme.R
import com.tellingus.tellingme.presentation.ui.common.component.appbar.BasicAppBar
import com.tellingus.tellingme.presentation.ui.common.component.button.TellingmeIconButton
import com.tellingus.tellingme.presentation.ui.common.component.card.OpinionCard
import com.tellingus.tellingme.presentation.ui.common.component.layout.MainLayout
import com.tellingus.tellingme.presentation.ui.common.component.section.QuestionSection
import com.tellingus.tellingme.presentation.ui.common.model.ButtonSize
import com.tellingus.tellingme.presentation.ui.common.model.ButtonState
import com.tellingus.tellingme.presentation.ui.theme.Background100
import com.tellingus.tellingme.presentation.ui.theme.Gray500
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.holix.android.bottomsheetdialog.compose.BottomSheetBehaviorProperties
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialog
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties
import com.holix.android.bottomsheetdialog.compose.NavigationBarProperties
import com.tellingus.tellingme.presentation.ui.common.component.bottomsheet.CustomBottomSheet
import com.tellingus.tellingme.presentation.ui.common.component.box.CheckBox
import com.tellingus.tellingme.presentation.ui.common.component.button.PrimaryButton
import com.tellingus.tellingme.presentation.ui.common.component.button.PrimaryLightButton
import com.tellingus.tellingme.presentation.ui.common.component.dialog.ShowSingleButtonDialog
import com.tellingus.tellingme.presentation.ui.theme.Base0
import com.tellingus.tellingme.presentation.ui.theme.Gray100
import com.tellingus.tellingme.presentation.ui.theme.Gray600
import com.tellingus.tellingme.presentation.ui.theme.Typography
import kotlinx.coroutines.launch

@Composable
fun OtherSpaceDetailScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    var isComplaintBottomSheetOpen by remember { mutableStateOf(false) }
    var isComplainReasonBottomSheetOpen by remember { mutableStateOf(false) }
    var selectedComplainReason by remember { mutableStateOf("") }
    var isComplainConfirmModalOpen by remember { mutableStateOf(false) }

    MainLayout(header = {
        BasicAppBar(modifier = modifier
            .fillMaxWidth()
            .padding(start = 12.dp, top = 12.dp, bottom = 12.dp, end = 12.dp), leftSlot = {
            TellingmeIconButton(iconRes = R.drawable.icon_caret_left,
                size = ButtonSize.MEDIUM,
                color = Gray500,
                onClick = {
                    navController.popBackStack()
                })
        }, rightSlot = {
            TellingmeIconButton(iconRes = R.drawable.icon_more,
                size = ButtonSize.MEDIUM,
                color = Gray500,
                onClick = {
                    isComplaintBottomSheetOpen = true
                })
        })
    },
        content = {
            OtherSpaceDetailScreenContent()
            if (isComplaintBottomSheetOpen) {
                ComplaintBottomSheet(
                    onDismiss = { isComplaintBottomSheetOpen = false },
                    onClick = { isComplainReasonBottomSheetOpen = true })
            }
            if (isComplainReasonBottomSheetOpen) {
                ComplainReasonBottomSheet(
                    onDismiss = {
                        isComplaintBottomSheetOpen = false
                        isComplainReasonBottomSheetOpen = false
                    },
                    onConfirm = {
                        isComplaintBottomSheetOpen = false
                        selectedComplainReason = it
                        isComplainConfirmModalOpen = true
                    }
                )
            }
            if (isComplainConfirmModalOpen) {
                ShowSingleButtonDialog(title = "신고가 접수되었습니다.",
                    contents = "",
                    completeButton = {
                        PrimaryButton(
                            modifier = Modifier.fillMaxWidth(),
                            size = ButtonSize.LARGE,
                            text = "확인",
                            onClick = {
                                isComplainConfirmModalOpen = false
                            }
                        )
                    }
                )
            }
        })
}


@Composable
fun ComplainReasonBottomSheet(onConfirm: (String) -> Unit = {}, onDismiss: () -> Unit) {
    var checkedValue by remember { mutableStateOf("") }
    BottomSheetDialog(
        onDismissRequest = { onDismiss }, properties = BottomSheetDialogProperties(
            navigationBarProperties = NavigationBarProperties(navigationBarContrastEnforced = false),
            /** 하단 시스템 내비게이션과 중첩되는 이슈 해결 **/
            dismissOnClickOutside = false,
            behaviorProperties = BottomSheetBehaviorProperties(isDraggable = true)
        )
    ) {
        CustomBottomSheet {
            Column() {
                Text(text = "신고 사유를 알려주세요.", style = Typography.head3Bold, color = Gray600)
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    listOf(
                        ComplainReasonType.Abuse,
                        ComplainReasonType.Pornography,
                        ComplainReasonType.Advertising,
                        ComplainReasonType.PersonalInfoInfringement,
                        ComplainReasonType.Fishing,
                        ComplainReasonType.Others,
                    ).forEach {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                                .background(
                                    color = if (checkedValue == it.value) Gray100 else Base0,
                                    shape = RoundedCornerShape(8.dp)
                                )
                        ) {
                            CheckBox(
                                text = it.label,
                                onClick = { checkedValue = it.value },
                                isSelected = checkedValue == it.value,
                                isIconCircle = true
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    PrimaryLightButton(
                        modifier = Modifier.weight(1f),
                        size = ButtonSize.LARGE,
                        text = "취소",
                        onClick = { onDismiss() })
                    Spacer(modifier = Modifier.width(4.dp))
                    PrimaryButton(
                        modifier = Modifier.weight(1f),
                        size = ButtonSize.LARGE, text = "확인", onClick = {
                            onConfirm(checkedValue)
                            onDismiss()
                        })
                }
            }
        }
    }

}

@Composable
fun ComplaintBottomSheet(onClick: () -> Unit = {}, onDismiss: () -> Unit = {}) {
    BottomSheetDialog(
        onDismissRequest = { onDismiss }, properties = BottomSheetDialogProperties(
            navigationBarProperties = NavigationBarProperties(navigationBarContrastEnforced = false),
            /** 하단 시스템 내비게이션과 중첩되는 이슈 해결 **/
            dismissOnClickOutside = false,
            behaviorProperties = BottomSheetBehaviorProperties(isDraggable = true)
        )
    ) {
        CustomBottomSheet {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 17.5.dp, bottom = 17.5.dp, start = 12.dp)
                    .clickable(enabled = true,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { onClick() }
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(
                        id = R.drawable.icon_siren
                    ), contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("신고하기")
            }
        }
    }


}

@Composable
fun OtherSpaceDetailScreenContent() {
    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 0.dp, bottom = 20.dp)
            .fillMaxHeight()
    ) {
        QuestionSection(
            title = "지금까지의 나의 인생을 두 단계로\n" + "나눈다면 어느 시점에 구분선을 둘 건가요?",
            description = "스스로 크게 변화한 시점을 떠올려봐요.",
            isButtonVisible = false,
            bgColor = Background100
        )
        OpinionCard(
            heartCount = mockData.heartCount,
            buttonState = mockData.buttonState,
            feeling = mockData.feeling,
            description = mockData.description,
            type = "full"
        )
    }
}

@Preview
@Composable
fun OtherSpaceDetailScreenPreview() {
    OtherSpaceDetailScreen(navController = rememberNavController())
}

@Preview
@Composable
fun ComplaintBottomSheetPreview() {
    ComplaintBottomSheet(onDismiss = {})
}


data class OpinionItem(
    val id: Int,
    val heartCount: Int,
    val buttonState: ButtonState,
    val feeling: String,
    val description: String
)

val mockData = OpinionItem(
    id = 1,
    heartCount = 999,
    buttonState = ButtonState.ENABLED,
    feeling = "excited",
    description = "나는 보통 집단 안에서 이야기 나온 내용에서 핵심을 뽑아내 정리하는 것을 잘하는 것 같다. 예를 들면 학교 팀플을 진행할 때 빛을 보인다. 팀원들의 의견을 수용하여 핵심만을 요약한다."
)

sealed class ComplainReasonType(val label: String, val value: String) {
    object Abuse : ComplainReasonType(label = "욕설", value = "abuse")
    object Pornography : ComplainReasonType(label = "음란물", value = "pornography")
    object Advertising : ComplainReasonType(label = "광고", value = "advertising")
    object PersonalInfoInfringement : ComplainReasonType(label = "개인정보 침해", value = "personalInfo")
    object Fishing : ComplainReasonType(label = "낚시성 콘텐츠", value = "fishing")
    object Others : ComplainReasonType(label = "기타", value = "others")
}