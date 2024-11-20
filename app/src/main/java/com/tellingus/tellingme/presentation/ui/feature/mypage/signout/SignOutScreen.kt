package com.tellingus.tellingme.presentation.ui.feature.mypage.signout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tellingus.tellingme.R
import com.tellingus.tellingme.presentation.ui.common.component.button.PrimaryButton
import com.tellingus.tellingme.presentation.ui.common.component.button.PrimaryLightButton
import com.tellingus.tellingme.presentation.ui.common.component.button.TellingmeIconButton
import com.tellingus.tellingme.presentation.ui.common.component.dialog.DoubleButtonDialog
import com.tellingus.tellingme.presentation.ui.common.component.dialog.ShowDoubleButtonDialog
import com.tellingus.tellingme.presentation.ui.common.component.dialog.SingleButtonDialog
import com.tellingus.tellingme.presentation.ui.common.model.ButtonSize
import com.tellingus.tellingme.presentation.ui.feature.mypage.MyPageViewModel
import com.tellingus.tellingme.presentation.ui.theme.Base0
import com.tellingus.tellingme.presentation.ui.theme.Error600
import com.tellingus.tellingme.presentation.ui.theme.Gray300
import com.tellingus.tellingme.presentation.ui.theme.Gray50
import com.tellingus.tellingme.presentation.ui.theme.Gray500
import com.tellingus.tellingme.presentation.ui.theme.Gray600
import com.tellingus.tellingme.presentation.ui.theme.TellingmeTheme
import com.tellingus.tellingme.util.noRippleClickable

@Composable
fun SignOutScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: MyPageViewModel
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    var isShowSignOutDialog by remember { mutableStateOf(false) }
    var isShowSignOutCompleteDialog by remember { mutableStateOf(false) }
    var isCheckedSignOut by remember { mutableStateOf(false) }

    Column {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            TellingmeIconButton(
                modifier = modifier
                    .align(Alignment.CenterStart),
                iconRes = R.drawable.icon_caret_left,
                size = ButtonSize.LARGE,
                color = Gray500,
                onClick = {
                    navController.popBackStack()
                }
            )
            Text(
                text = "회원 탈퇴",
                style = TellingmeTheme.typography.body1Bold.copy(
                    color = Gray600
                )
            )
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "회원 탈퇴 전 반드시 확인해주세요!",
                style = TellingmeTheme.typography.body1Bold.copy(
                    color = Gray600
                )
            )
            Spacer(modifier = Modifier.size(16.dp))
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.icon_delete_duey),
                contentDescription = null
            )
            Spacer(modifier = Modifier.size(28.dp))

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.icon_values),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "알고 계셨나요?",
                    style = TellingmeTheme.typography.body2Bold.copy(
                        color = Gray600
                    )
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "내가 쓴 답변은 탈퇴하지 않아도 수정 및 삭제가 가능해요.",
                    style = TellingmeTheme.typography.body2Regular.copy(
                        color = Gray600
                    )
                )
            }
            Spacer(modifier = Modifier.size(20.dp))
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.icon_warn),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "계정을 삭제하면 아래의 내용들이 전부 사라져요.",
                    style = TellingmeTheme.typography.body2Bold.copy(
                        color = Gray600
                    )
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "1. 지금까지 작성한 모든 글",
                    style = TellingmeTheme.typography.body2Regular.copy(
                        color = Gray600
                    )
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "2. 소중하게 모은 치즈, 배지, 레벨, 경험치",
                    style = TellingmeTheme.typography.body2Bold.copy(
                        color = Gray600
                    )
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "3.  치즈로 구매한 감정 이모티콘, 배경색 등",
                    style = TellingmeTheme.typography.body2Regular.copy(
                        color = Gray600
                    )
                )
                Spacer(modifier = Modifier.size(6.dp))
                Text(
                    text = "다만, 모두의 공간에 공개한 답변은 자동으로 삭제되지 않으니 탈퇴 전 비공개 처리 해주세요.",
                    style = TellingmeTheme.typography.body2Bold.copy(
                        color = Error600
                    )
                )
            }
            Spacer(modifier = Modifier.size(20.dp))

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .background(color = Gray50, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.icon_crown),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "텔링미 플러스 구독 이용자는 꼭 읽어주세요.",
                    style = TellingmeTheme.typography.body2Bold.copy(
                        color = Gray600
                    )
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "탈퇴하시기 전에 인앱결제가 진행 중인 앱마켓을 통해 정기구독을 반드시 해지해 주셔야 합니다.",
                    style = TellingmeTheme.typography.body2Regular.copy(
                        color = Gray600
                    )
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "구독 해지 방법 자세히 보기",
                    style = TellingmeTheme.typography.body2Bold.copy(
                        color = Color(0xff6376FF)
                    )
                )
                Spacer(modifier = Modifier.size(12.dp))
                Text(
                    text = "구글 플레이 스토어 정책 상 회원님의 모든 결제는 텔링미 계정이 아닌 구글 플레이 스토어 계정에 귀속되므로 텔링미가 대신 해지해 드릴 수 없습니다.",
                    style = TellingmeTheme.typography.body2Regular.copy(
                        color = Gray600
                    )
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "직접 해지하지 않을 시 정기 결제가 자동으로 진행될 수 있어요.",
                    style = TellingmeTheme.typography.body2Bold.copy(
                        color = Error600
                    )
                )
            }
            Spacer(modifier = Modifier.size(12.dp))

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .noRippleClickable {
                        isCheckedSignOut = !isCheckedSignOut
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(
                        if (isCheckedSignOut) R.drawable.icon_check_box_on else R.drawable.icon_check_box_off
                    ),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "(필수) 위 내용을 모두 확인하였습니다.",
                    style = TellingmeTheme.typography.body2Bold.copy(
                        color = Gray600
                    )
                )
            }
            Spacer(modifier = Modifier.size(20.dp))

            PrimaryButton(
                modifier = modifier.fillMaxWidth(),
                size = ButtonSize.LARGE,
                text = "탈퇴하기",
                onClick = {
                    if (isCheckedSignOut) {
                        isShowSignOutDialog = !isShowSignOutDialog
                    }
                }
            )
        }
    }

    if (isShowSignOutDialog) {
        ShowDoubleButtonDialog(
            title = "정말로 텔링미를 떠나실건가요?",
            contents = "그동안 작성하신 답변들이 모두 사라져요..",
            leftButton = {
                PrimaryLightButton(
                    modifier = Modifier.weight(1f),
                    size = ButtonSize.LARGE,
                    text = "아니오",
                    onClick = {
                        isShowSignOutDialog = false
                    }
                )
            },
            rightButton = {
                PrimaryButton(
                    modifier = Modifier.weight(1f),
                    size = ButtonSize.LARGE,
                    text = "떠나기",
                    onClick = {
                        isShowSignOutCompleteDialog = true
//                        viewModel.signOutUser()
                    }
                )
            }
        )
    }

    if (isShowSignOutCompleteDialog) {
        Dialog(
            onDismissRequest = {},
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = false
            )
        ) {
            Surface(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .wrapContentHeight(),
                shape = RoundedCornerShape(20.dp),
                color = Base0
            ) {
                Card(
                    modifier = modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(Base0),
                    elevation = CardDefaults.cardElevation(0.dp),
                ) {
                    Column(
                        modifier = modifier
                            .padding(
                                top = 30.dp,
                                bottom = 20.dp,
                                start = 16.dp,
                                end = 16.dp
                            )
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        androidx.compose.material.Text(
                            text = "여행의 끝은 여기지만,\n언제든 다시 돌아오세요.",
                            style = TellingmeTheme.typography.body1Bold.copy(
                                color = Gray600
                            ),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = modifier.size(4.dp))
                        androidx.compose.material.Text(
                            text = "텔링미는 늘 이 자리에서 기다리고 있을게요.",
                            style = TellingmeTheme.typography.body2Regular.copy(
                                color = Gray600
                            )
                        )
                        Spacer(modifier = modifier.size(20.dp))
                        Image(
                            imageVector = ImageVector.vectorResource(R.drawable.icon_delete_duey),
                            contentDescription = null
                        )
                        Spacer(modifier = modifier.size(20.dp))
                        PrimaryButton(
                            modifier = modifier.fillMaxWidth(),
                            size = ButtonSize.LARGE,
                            text = "네, 안녕히",
                            onClick = {
                                // 로그인 정보 다 지우고 로그인 화면으루

                            }
                        )
                    }
                }
            }
        }
    }
}