package com.tellingus.tellingme.presentation.ui.feature.mypage.setting

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tellingus.tellingme.R
import com.tellingus.tellingme.presentation.ui.common.component.appbar.BasicAppBar
import com.tellingus.tellingme.presentation.ui.common.component.button.TellingmeIconButton
import com.tellingus.tellingme.presentation.ui.common.component.layout.MainLayout
import com.tellingus.tellingme.presentation.ui.common.model.ButtonSize
import com.tellingus.tellingme.presentation.ui.common.navigation.AuthDestinations
import com.tellingus.tellingme.presentation.ui.common.navigation.HomeDestinations
import com.tellingus.tellingme.presentation.ui.common.navigation.MyPageDestinations
import com.tellingus.tellingme.presentation.ui.common.navigation.MySpaceDestinations
import com.tellingus.tellingme.presentation.ui.common.navigation.OtherSpaceDestinations
import com.tellingus.tellingme.presentation.ui.feature.mypage.MyPageContract
import com.tellingus.tellingme.presentation.ui.feature.mypage.MyPageViewModel
import com.tellingus.tellingme.presentation.ui.theme.Gray500
import com.tellingus.tellingme.presentation.ui.theme.Gray600
import com.tellingus.tellingme.presentation.ui.theme.Typography
import com.tellingus.tellingme.util.collectWithLifecycle

@Composable
fun SettingScreen(navController: NavController, viewModel: MyPageViewModel = hiltViewModel()) {

    MainLayout(
        header = { SettingScreenHeader { navController.popBackStack() } },
        content = {
            Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
                RowItem(
                    text = "개인정보 수정",
                    hasArrow = true,
                    onClick = { navController.navigate(MyPageDestinations.MY_INFO_EDIT) })
                RowItem(text = "로그아웃", hasArrow = false, onClick = {
                    viewModel.logout()
                })
                RowItem(text = "탈퇴하기", hasArrow = true, onClick = {
                    navController.navigate(MyPageDestinations.WITH_DRAW)
                })
            }
        },
        isScrollable = false,
        background = Color.White
    )

    viewModel.effect.collectWithLifecycle { effect ->
        when(effect) {
            is MyPageContract.Effect.MoveToLoginScreen -> {
                // 홈화면으로 이동
                navController.navigate(AuthDestinations.Login.LOGIN) {
                    popUpTo(0) {
                        inclusive = true
                    }
                }
            }
            else -> {}
        }
    }
}

@Composable
fun RowItem(text: String, hasArrow: Boolean, onClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable { onClick() }) {
        Text(text = text, style = Typography.body1Bold, color = Gray600)
        if (hasArrow) {
            Image(
                painter = painterResource(id = R.drawable.icon_caret_right),
                contentDescription = ""
            )
        }
    }
}

@Composable
fun SettingScreenHeader(navigateToPreviousScreen: () -> Unit) {
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
            Text("설정", style = Typography.body1Bold, color = Gray600)
        },
    )
}