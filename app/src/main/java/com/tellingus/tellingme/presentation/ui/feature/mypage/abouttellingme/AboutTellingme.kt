package com.tellingus.tellingme.presentation.ui.feature.mypage.abouttellingme

import android.content.Intent
import android.net.Uri
import android.widget.GridLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tellingus.tellingme.R
import com.tellingus.tellingme.presentation.ui.common.component.appbar.BasicAppBar
import com.tellingus.tellingme.presentation.ui.common.component.button.PrimaryButton
import com.tellingus.tellingme.presentation.ui.common.component.button.TellingmeIconButton
import com.tellingus.tellingme.presentation.ui.common.component.layout.MainLayout
import com.tellingus.tellingme.presentation.ui.common.model.ButtonSize
import com.tellingus.tellingme.presentation.ui.theme.Gray500
import com.tellingus.tellingme.presentation.ui.theme.Gray600
import com.tellingus.tellingme.presentation.ui.theme.TellingmeTheme
import com.tellingus.tellingme.presentation.ui.theme.Typography

@Composable
fun AboutTellingMe(navController: NavController) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Box {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xff27294A))
            ) {
                TellingmeIconButton(
                    modifier = Modifier.align(Alignment.TopStart).padding(all = 12.dp),
                    iconRes = R.drawable.icon_caret_left,
                    size = ButtonSize.MEDIUM,
                    color = Color.White,
                    onClick = {
                        navController.popBackStack()
                    }
                )

                Text(
                    modifier = Modifier.align(Alignment.Center).padding(vertical = 12.dp),
                    text = "텔링미 탄생 이야기",
                    style = TellingmeTheme.typography.body1Bold.copy(
                        color = Color.White
                    )
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.image_birth_tellingme),
                contentDescription = "about_tellingme"
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xff5C61BD))
                    .padding(20.dp)
                    .padding(bottom = 30.dp)
            ) {
                PrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    size = ButtonSize.MEDIUM,
                    text = "텔링어스 팀 블로그",
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://medium.com/@tellingme"))
                        context.startActivity(intent)
                    }
                )
                Spacer(modifier = Modifier.width(10.dp))
                PrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    size = ButtonSize.MEDIUM,
                    text = "텔링어스 팀 소개",
                    onClick = {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://doana.notion.site/Telling-US-d749bc2fb3d44c2e95834ccfcdc14214?pvs=4")
                        )
                        context.startActivity(intent)
                    }
                )
            }
        }

    }
}


@Composable
fun AboutTellingMeHeader(navigateToPreviousScreen: () -> Unit) {
    BasicAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0x0027294A))
            .padding(start = 12.dp, top = 5.dp, bottom = 5.dp, end = 10.dp),
        leftSlot = {
            TellingmeIconButton(iconRes = R.drawable.icon_caret_left,
                size = ButtonSize.MEDIUM,
                color = Color.White,
                onClick = { navigateToPreviousScreen() })
        },
        centerSlot = {
            Text("텔링미에 대해서", style = Typography.body1Bold, color = Color.White)
        },
    )
}

