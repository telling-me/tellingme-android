package com.tellingus.tellingme.presentation.ui.common.component.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.tellingus.tellingme.presentation.ui.common.component.button.PrimaryButton
import com.tellingus.tellingme.presentation.ui.common.component.button.PrimaryLightButton
import com.tellingus.tellingme.presentation.ui.common.model.ButtonSize
import com.tellingus.tellingme.presentation.ui.theme.Base0
import com.tellingus.tellingme.presentation.ui.theme.Gray600
import com.tellingus.tellingme.presentation.ui.theme.TellingmeTheme

@Composable
fun DoubleButtonDialog(
    modifier: Modifier = Modifier,
    title: String,
    contents: String,
    leftButton: @Composable RowScope.() -> Unit,
    rightButton: @Composable RowScope.() -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
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
            Text(
                text = title,
                style = TellingmeTheme.typography.body1Bold.copy(
                    color = Gray600
                )
            )
            Spacer(modifier = modifier.size(4.dp))
            Text(
                text = contents,
                style = TellingmeTheme.typography.body2Regular.copy(
                    color = Gray600
                )
            )
            Spacer(modifier = modifier.size(20.dp))

            Row {
                leftButton()
                Spacer(modifier = modifier.size(8.dp))
                rightButton()
            }
        }
    }
}

@Composable
fun ShowDoubleButtonDialog(
    modifier: Modifier = Modifier,
    title: String,
    contents: String,
    leftButton: @Composable RowScope.() -> Unit,
    rightButton: @Composable RowScope.() -> Unit
) {
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
            DoubleButtonDialog(
                title = title,
                contents = contents,
                leftButton = leftButton,
                rightButton = rightButton
            )
        }
    }
}

@Preview
@Composable
fun DoubleButtonDialogPreview() {
    DoubleButtonDialog(
        title = "Title",
        contents = "텍스트",
        leftButton = {
            PrimaryLightButton(
                modifier = Modifier.weight(1f),
                size = ButtonSize.LARGE,
                text = "취소",
                onClick = {}
            )
        },
        rightButton = {
            PrimaryButton(
                  modifier = Modifier.weight(1f),
                  size = ButtonSize.LARGE,
                  text = "완료",
                  onClick = {}
              )
        }
    )
}