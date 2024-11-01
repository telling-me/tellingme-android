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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.tellingus.tellingme.presentation.ui.theme.Typography


// 팀원 정보를 담는 데이터 클래스
data class TeamMember(val name: String, val role: String)

@Composable
fun AboutTellingMe(navController: NavController) {
    MainLayout(
        header = { AboutTellingMeHeader { navController.popBackStack() } },
        content = { AboutTellingMeContent() },
        isScrollable = true,
    )
}

@Composable
fun AboutTellingMeContent() {
    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF8CFED3)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.about_tellingme),
                contentDescription = "about_tellingme",
                modifier = Modifier.padding(top = 24.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "바로 지금, 당신은 나 자신에 대해 잘 알고 있나요?", textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "너무나도 빠르게 변화하는 세상,\n" + "우리는 스스로를 놓치며 치여가듯 살아가죠.",
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "텔링미는\n" + "하루 한 번, 나를 알아가는 질문과 함께\n" + "나를 깊이 탐색할 수 있는 다이어리예요.",
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "오로지 나만을 위한 공간에서\n" + "꾸준히 기록하며 스스로를 알아가봐요.",
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "나도 몰랐던 내 모습을 발견할지도요!", textAlign = TextAlign.Center)
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("By. 팀 텔링어스")
            Spacer(modifier = Modifier.height(30.dp))
            Image(
                painter = painterResource(id = R.drawable.about_tellingus),
                contentDescription = "about_tellingus"
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        val teamMembers = listOf(
            TeamMember("Lavender", "Developer"),
            TeamMember("Lavender", "PO"),
            TeamMember("Lavender", "PM"),
            TeamMember("Lavender", "Design"),
            TeamMember("Lavender", "DevOps"),
            TeamMember("Lavender", "Developer")
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // 팀원 리스트의 크기에 따라 Row를 생성합니다.
            teamMembers.chunked(2).forEach { rowMembers ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 18.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    // Row에 포함된 팀원 수에 따라 TeamCardItem을 생성합니다.
                    rowMembers.forEach { member ->
                        TeamCardItem(member)
                    }
                }
            }
        }


        val context = LocalContext.current
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                size = ButtonSize.LARGE,
                text = "텔링어스 팀블로그",
                onClick = {
                    val intent =
                        Intent(Intent.ACTION_VIEW, Uri.parse("https://medium.com/@tellingme"))
                    context.startActivity(intent)
                })
            Spacer(modifier = Modifier.height(8.dp))
            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                size = ButtonSize.LARGE, text = "텔링어스 팀 소개", onClick = {
                    val intent =
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://doana.notion.site/Telling-US-d749bc2fb3d44c2e95834ccfcdc14214?pvs=4")
                        )
                    context.startActivity(intent)

                })
        }
    }
}


@Composable
fun TeamCardItem(member: TeamMember) {
    Row(modifier = Modifier.widthIn(max = 150.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .width(63.dp)
                .height(63.dp)
                .background(
                    shape = RoundedCornerShape(999.dp), color = Color(0xFFD9D9D9)
                )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = member.name) // 예시 텍스트
            Text(text = member.role) // 예시 텍스트
        }
    }
}

@Composable
fun AboutTellingMeHeader(navigateToPreviousScreen: () -> Unit) {
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
            Text("텔링미에 대해서", style = Typography.body1Bold, color = Gray600)
        },
    )
}

