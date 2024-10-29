package com.tellingus.tellingme.presentation.ui.feature.mypage.myinfoedit

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tellingus.tellingme.R
import com.tellingus.tellingme.presentation.ui.common.component.appbar.BasicAppBar
import com.tellingus.tellingme.presentation.ui.common.component.button.SingleButton
import com.tellingus.tellingme.presentation.ui.common.component.button.TellingmeIconButton
import com.tellingus.tellingme.presentation.ui.common.component.layout.MainLayout
import com.tellingus.tellingme.presentation.ui.common.model.ButtonSize
import com.tellingus.tellingme.presentation.ui.theme.Gray500

@Composable
fun MyInfoEditScreen(navController: NavController) {
    MainLayout(
        header = { MyInfoEditScreenHeader(navController) },
        content = { MyInfoEditScreenContent() })
}

@Composable
fun MyInfoEditScreenContent() {

}

@Composable
fun MyInfoEditScreenHeader(navController: NavController) {
    BasicAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, top = 5.dp, bottom = 5.dp, end = 10.dp),
        leftSlot = {
            TellingmeIconButton(
                iconRes = R.drawable.icon_caret_left,
                size = ButtonSize.MEDIUM,
                color = Gray500,
                onClick = {
                    navController.popBackStack()
                }
            )
        },
        rightSlot = {
            Row {
                SingleButton(
                    size = ButtonSize.LARGE,
                    text = "완료",
                    onClick = {

                    }
                )
            }
        }
    )
}