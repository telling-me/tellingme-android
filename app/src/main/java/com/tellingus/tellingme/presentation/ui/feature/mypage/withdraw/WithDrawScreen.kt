package com.tellingus.tellingme.presentation.ui.feature.mypage.withdraw

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tellingus.tellingme.R
import com.tellingus.tellingme.presentation.ui.common.component.appbar.BasicAppBar
import com.tellingus.tellingme.presentation.ui.common.component.box.CheckBox
import com.tellingus.tellingme.presentation.ui.common.component.button.PrimaryButton
import com.tellingus.tellingme.presentation.ui.common.component.button.PrimaryLightButton
import com.tellingus.tellingme.presentation.ui.common.component.button.TellingmeIconButton
import com.tellingus.tellingme.presentation.ui.common.component.dialog.ShowDoubleButtonDialog
import com.tellingus.tellingme.presentation.ui.common.component.dialog.ShowSingleButtonDialog
import com.tellingus.tellingme.presentation.ui.common.component.layout.MainLayout
import com.tellingus.tellingme.presentation.ui.common.model.ButtonSize
import com.tellingus.tellingme.presentation.ui.common.navigation.AuthDestinations
import com.tellingus.tellingme.presentation.ui.feature.auth.signup.SignupContract
import com.tellingus.tellingme.presentation.ui.feature.mypage.MyPageViewModel
import com.tellingus.tellingme.presentation.ui.theme.Error600
import com.tellingus.tellingme.presentation.ui.theme.Gray500
import com.tellingus.tellingme.presentation.ui.theme.Gray600
import com.tellingus.tellingme.presentation.ui.theme.Typography

@Composable
fun WithDrawScreen(
    navController: NavController,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    MainLayout(
        header = { WithDrawScreenHeader { navController.popBackStack() } },
        content = { WithDrawScreenContent(navController, viewModel) },
        isScrollable = false,
    )
}

@Composable
fun WithDrawScreenContent(
    navController: NavController,
    viewModel: MyPageViewModel
) {
    var isChecked by remember {
        mutableStateOf(false)
    }
    var showConfirmDialogState by remember { mutableStateOf(false) }
    var showByeDialogState by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier.padding(20.dp, 0.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "회원 탈퇴 전 반드시 확인해주세요!", style = Typography.body1Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.delete_duey),
            contentDescription = "",
            alignment = Alignment.Center
        )

        Spacer(modifier = Modifier.height(28.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 139.dp)
                .background(
                    shape = RoundedCornerShape(8.dp), color = Color.White
                ),
            verticalArrangement = Arrangement.Center,
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.icon_values), contentDescription = ""
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "알고 계셨나요?", style = Typography.body2Bold)
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "내가 쓴 답변은 탈퇴하지 않아도 수정 및 삭제가\n" + "가능해요.", style = Typography.body2Regular
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 139.dp)
                .background(
                    shape = RoundedCornerShape(8.dp), color = Color.White
                ),
            verticalArrangement = Arrangement.Center,
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Image(painter = painterResource(id = R.drawable.icon_warn), contentDescription = "")
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "계정을 삭제하면 아래의 내용들이 전부 사라져요",
                    style = Typography.body2Bold,
                    color = Gray600
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "1. 치즈, 배지, 레벨, 경험치 등", style = Typography.body2Regular, color = Gray600
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "2. 치즈로 구매한 감정 이모티콘, 배경색 등",
                    style = Typography.body2Regular,
                    color = Gray600
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "3.  지금까지 작성한 모든 답변 기록", style = Typography.body2Regular, color = Gray600
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "다만, 모두의 공간에 공개한 답변은 자동으로 삭제되지 않으니 탈퇴 전 비공개 처리 해주세요.",
                    style = Typography.caption1Bold,
                    color = Error600
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

// TODO: 1차 출시 결제 미포함
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(139.dp)
//                .background(
//                    shape = RoundedCornerShape(8.dp),
//                    color = Color.White
//                )
//        ) {
//            Image(painter = painterResource(id = R.drawable.icon_crown), contentDescription = "")
//            Spacer(modifier = Modifier.height(10.dp))
//            Text(text = "텔링미 플러스 구독 이용자는 꼭 읽어주세요.", style = Typography.body2Bold, color = Gray600)
//            Spacer(modifier = Modifier.height(10.dp))
//            Text(text = "탈퇴하시기 전에 인앱결제가 진행 중인 앱마켓을 통해 정기구독을 반드시 해지해 주셔야 합니다.", style = Typography.body2Regular, color= Gray600)
//        }

        CheckBox(
            text = "(필수) 위 내용을 모두 확인하였습니다.",
            onClick = { isChecked = !isChecked },
            isSelected = isChecked,
        )

        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            size = ButtonSize.LARGE,
            text = "탈퇴하기",
            enable = isChecked,
            onClick = { if (isChecked) showConfirmDialogState = true }
        )
    }


    if (showByeDialogState) {
        ShowSingleButtonDialog(title = "여행의 끝은 여기지만, \n" + "언제든 다시 돌아오세요.",
            contents = "텔링미는 늘 이 자리에서 기다리고 있을게요.",
            completeButton = {
                PrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    size = ButtonSize.LARGE,
                    text = "네, 안녕히",
                    onClick = {
                        showByeDialogState = false
                        navController.navigate(AuthDestinations.Login.LOGIN) {
                            popUpTo(0) {
                                inclusive = true
                            }
                        }
                    }

                )
            }
        )
    }


    if (showConfirmDialogState) {
        ShowDoubleButtonDialog(
            title = "정말로 텔링미를 떠나실건가요?",
            contents = "그동안 작성하신 답변들이 모두 사라져요..",
            leftButton = {
                PrimaryLightButton(
                    modifier = Modifier.weight(1f),
                    size = ButtonSize.LARGE,
                    text = "아니요",
                    onClick = {
                        showConfirmDialogState = false
                    }
                )
            },
            rightButton = {
                PrimaryButton(
                    modifier = Modifier.weight(1f),
                    size = ButtonSize.LARGE,
                    text = "떠나기",
                    onClick = {
//                    Toast.makeText(context, "완료 후 홈으로 이동", Toast.LENGTH_SHORT).show()
                        showConfirmDialogState = false
                        showByeDialogState = true
                        viewModel.signOutUser()
                    }
                )
            }
        )
    }
}

@Composable
fun WithDrawScreenHeader(navigateToPreviousScreen: () -> Unit) {
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
            Text("회원탈퇴", style = Typography.body1Bold, color = Gray600)
        },
    )
}

