package com.tellingus.tellingme.presentation.ui.common.component.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import com.tellingus.tellingme.R
import com.tellingus.tellingme.presentation.ui.common.component.button.PrimaryButton
import com.tellingus.tellingme.presentation.ui.common.component.button.SingleButton
import com.tellingus.tellingme.presentation.ui.common.model.ButtonSize
import com.tellingus.tellingme.presentation.ui.theme.Gray500
import com.tellingus.tellingme.presentation.ui.theme.Gray600
import com.tellingus.tellingme.presentation.ui.theme.TellingmeTheme

@Composable
fun PushDenyDialog(
    modifier: Modifier = Modifier,
    onClickPositive: () -> Unit,
    onClickNegative: () -> Unit
) {
    Dialog(
        onDismissRequest = { onClickNegative() },
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Column(
            modifier = modifier
                .padding(20.dp)
                .background(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White
                )
                .padding(top = 30.dp, start = 16.dp, end = 16.dp, bottom = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = modifier.padding(bottom = 20.dp),
                textAlign = TextAlign.Center,
                text = "혜택이나 이벤트 알림을\n" +
                        "받지 못할 수도 있어요.\n" +
                        "그래도 괜찮으시겠어요?",
                style = TellingmeTheme.typography.body1Bold.copy(
                    color = Gray600,
                    fontSize = 16.sp
                )
            )
            Row {
                SingleButton(
                    modifier = modifier.fillMaxWidth().weight(1f),
                    size = ButtonSize.LARGE,
                    text = "취소",
                    onClick = {
                        onClickNegative()
                    }
                )
                Spacer(modifier = Modifier.size(8.dp))
                PrimaryButton(
                    modifier = modifier.fillMaxWidth().weight(1f),
                    size = ButtonSize.LARGE,
                    text = "알림 받기",
                    onClick = {
                        onClickPositive()
                    }
                )
            }
        }
    }
}