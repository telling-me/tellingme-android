package com.tellingus.tellingme.presentation.ui.feature.mypage.withdraw

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tellingus.tellingme.R
import com.tellingus.tellingme.presentation.ui.common.component.appbar.BasicAppBar
import com.tellingus.tellingme.presentation.ui.common.component.button.TellingmeIconButton
import com.tellingus.tellingme.presentation.ui.common.component.layout.MainLayout
import com.tellingus.tellingme.presentation.ui.common.model.ButtonSize
import com.tellingus.tellingme.presentation.ui.theme.Gray500
import com.tellingus.tellingme.presentation.ui.theme.Gray600
import com.tellingus.tellingme.presentation.ui.theme.Typography

@Composable
fun WithDrawScreen(navController: NavController) {
    MainLayout(
        header = { WithDrawScreenHeader { navController.popBackStack() } },
        content = { WithDrawScreenContent() },
        isScrollable = false,
        background = Color.White
    )
}

@Composable
fun WithDrawScreenContent() {

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

