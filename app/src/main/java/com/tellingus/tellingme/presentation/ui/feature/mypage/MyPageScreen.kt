package com.tellingus.tellingme.presentation.ui.feature.mypage

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tellingus.tellingme.R
import com.tellingus.tellingme.presentation.ui.common.component.appbar.BasicAppBar
import com.tellingus.tellingme.presentation.ui.common.component.button.PrimaryButton
import com.tellingus.tellingme.presentation.ui.common.component.button.PrimaryLightButton
import com.tellingus.tellingme.presentation.ui.common.component.dialog.ShowDoubleButtonDialog
import com.tellingus.tellingme.presentation.ui.common.component.layout.MainLayout
import com.tellingus.tellingme.presentation.ui.common.component.widget.LevelSection
import com.tellingus.tellingme.presentation.ui.common.component.widget.ToolTip
import com.tellingus.tellingme.presentation.ui.common.const.getMediumEmotionBadge
import com.tellingus.tellingme.presentation.ui.common.model.ButtonSize
import com.tellingus.tellingme.presentation.ui.common.model.ToolTipType
import com.tellingus.tellingme.presentation.ui.common.navigation.HomeDestinations
import com.tellingus.tellingme.presentation.ui.common.navigation.MyPageDestinations
import com.tellingus.tellingme.presentation.ui.theme.Background100
import com.tellingus.tellingme.presentation.ui.theme.Base0
import com.tellingus.tellingme.presentation.ui.theme.Gray200
import com.tellingus.tellingme.presentation.ui.theme.Gray500
import com.tellingus.tellingme.presentation.ui.theme.Gray600
import com.tellingus.tellingme.presentation.ui.theme.TellingmeTheme
import com.tellingus.tellingme.util.AppUtils


@Composable
fun MyPageScreen(
    navController: NavController, viewModel: MyPageViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MainLayout(header = {
        MyPageScreenHeader(navController = navController)
    }, content = {
        MyPageScreenContent(navController, uiState, viewModel = viewModel)
    })
}

@Composable
fun MyPageScreenHeader(
    navController: NavController
) {
    BasicAppBar(modifier = Modifier
        .background(Background100)
        .height(48.dp)
        .padding(start = 20.dp, end = 20.dp)
        .fillMaxWidth(), rightSlot = {
        Icon(
            painter = painterResource(R.drawable.icon_setting),
            contentDescription = "icon_notice",
            modifier = Modifier
                .size(24.dp)
                .clickable(onClick = {
                    navController.navigate(MyPageDestinations.SETTING)
                }),
            tint = Gray200
        )
    })
}

// 권한 확인 함수
fun isNotificationPermissionGranted(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    } else {
        true // Android 13 미만에서는 권한이 항상 허용됨
    }
}

// 알림 설정 화면 열기
fun openNotificationSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
        putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
    }
    context.startActivity(intent)
}

@Composable
fun MyPageScreenContent(
    navController: NavController,
    uiState: MyPageContract.State,
    viewModel: MyPageViewModel
) {
    val context = LocalContext.current
    val appVersion = AppUtils.getAppVersion(context)
    var isTooltipVisible by remember { mutableStateOf(false) }


    var allowNotification = uiState.allowNotification;

    var isAlarmChecked by remember { mutableStateOf(allowNotification) }
    var isShowPermissionDialog by remember { mutableStateOf(false) }

    LaunchedEffect(allowNotification) {
        isAlarmChecked = allowNotification
    }


    val items = remember {
        mutableStateOf(
            listOf(
                IconTextItem(
                    id = "terms_of_use",
                    iconResId = R.drawable.icon_terms_of_use,
                    text = "이용 약관",
                    actionType = ActionType.OPEN_URL,
                    destination = "https://doana.notion.site/f42ec05972a545ce95231f8144705eae?pvs=4"
                ),
                IconTextItem(
                    id = "privacy_policy",
                    iconResId = R.drawable.icon_privacy_policy,
                    text = "개인정보 처리방침",
                    actionType = ActionType.OPEN_URL,
                    destination = "https://doana.notion.site/7cdab221ee6d436781f930442040d556?pvs=4"
                ),
                IconTextItem(
                    id = "customer_service",
                    iconResId = R.drawable.icon_customer_service,
                    text = "고객센터",
                    actionType = ActionType.SEND_EMAIL,
                    destination = "Email"
                ),
                IconTextItem(
                    id = "tellingme_introduce",
                    iconResId = R.drawable.icon_planet,
                    text = "텔링미를 소개해요",
                    actionType = ActionType.NAVIGATE_SCREEN,
                    destination = MyPageDestinations.ABOUT_TELLING_ME
                ),
                IconTextItem(
                    id = "tellingme_instagram",
                    iconResId = R.drawable.icon_instagram,
                    text = "텔링미 인스타그램",
                    actionType = ActionType.OPEN_URL,
                    destination = "https://www.instagram.com/tellingme.io/"
                ),
            )
        )
    }



    Column(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp)
    ) {
        Row {
            val TAG: String = "로그"
            Log.d(TAG, " - MyPageScreenContent() called ${uiState.badgeCode}")
            Image(
                painter = painterResource(getMediumEmotionBadge(uiState.badgeCode)),
                contentDescription = "",
                modifier = Modifier.size(68.dp)
            )
            Spacer(modifier = Modifier.size(16.dp))
            Column {
                Image(
                    painter = painterResource(R.drawable.tellingme_plus_box),
                    contentDescription = "",

                    )
                Text(
                    text = uiState.nickname,
                    modifier = Modifier.padding(top = 4.dp),
                    style = TellingmeTheme.typography.head3Bold
                )
            }
        }
        Box(modifier = Modifier.padding(top = 10.dp)) {
            LevelSection(
                level = uiState.level,
                percent = uiState.current_exp,
                levelDescription = "연속 ${uiState.days_to_level_up}일만 작성하면 LV.${uiState.level + 1} 달성!"
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .height(100.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(Base0),
        ) {
            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround // Spacing between columns
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable {
                        isTooltipVisible = true
                        Handler(Looper.getMainLooper()).postDelayed({
                            isTooltipVisible = false
                        }, 1000)
                    }) {
                    Image(
                        painter = painterResource(R.drawable.icon_cheese), contentDescription = ""
                    )
                    Text(
                        modifier = Modifier.padding(top = 10.dp),
                        text = "치즈",
                        color = Gray600,
                        style = TellingmeTheme.typography.caption2Regular
                    )
                    Text(
                        text = "${uiState.cheeseBalance}",
                        color = Gray600,
                        style = TellingmeTheme.typography.body2Bold
                    )
                }

                Divider(
                    modifier = Modifier
                        .width(1.dp)
                        .height(54.dp)
                        .background(Gray200)
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { navController.navigate(HomeDestinations.MY_TELLER_BADGE) }) {
                    Image(
                        painter = painterResource(R.drawable.icon_badge), contentDescription = ""
                    )
                    Text(
                        modifier = Modifier.padding(top = 10.dp),
                        text = "배지",
                        color = Gray600,
                        style = TellingmeTheme.typography.caption2Regular
                    )
                    Text(
                        text = "${uiState.badgeCount}",
                        color = Gray600,
                        style = TellingmeTheme.typography.body2Bold
                    )
                }

                Divider(
                    modifier = Modifier
                        .width(1.dp)
                        .height(54.dp)
                        .background(Gray200)
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { navController.navigate(MyPageDestinations.MY_LOG) }) {
                    Image(
                        painter = painterResource(R.drawable.icon_pencil), contentDescription = ""
                    )
                    Text(
                        modifier = Modifier.padding(top = 10.dp),
                        text = "작성글수",
                        color = Gray600,
                        style = TellingmeTheme.typography.caption2Regular
                    )
                    Text(
                        text = "${uiState.answerCount}",
                        color = Gray600,
                        style = TellingmeTheme.typography.body2Bold
                    )
                }
            }
        }

        if (isTooltipVisible) {
            ToolTip(
                type = ToolTipType.HELP,
                text = "현재 보유 중인 치즈는 총 ${uiState.cheeseBalance}개예요!",
                hasTriangle = true,
            )
        }
        Spacer(modifier = Modifier.height(if (isTooltipVisible) 0.dp else 40.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(78.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(Color(0xFF93A0FF)),
        ) {
            Row(
                modifier = Modifier
                    .padding(top = 20.dp, start = 16.dp, end = 13.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    Image(
                        painter = painterResource(R.drawable.tellingme_plus_circle),
                        contentDescription = "",
                        modifier = Modifier.size(42.dp)
                    )
                    Column(modifier = Modifier.padding(start = 12.dp)) {
                        Text(
                            text = "텔링미 구독 서비스",
                            color = Base0,
                            style = TellingmeTheme.typography.caption2Regular
                        )
                        Text(
                            text = "PLUS 라운지 입장하기",
                            color = Base0,
                            style = TellingmeTheme.typography.body2Bold
                        )
                    }
                }
                Icon(
                    painter = painterResource(id = R.drawable.icon_caret_right),
                    contentDescription = "",
                    tint = Base0
                )
            }
        }


        Row(modifier = Modifier.padding(top = 12.dp)) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .height(74.dp)
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .clickable {
                        val intent =
                            Intent(Intent.ACTION_VIEW, Uri.parse("https://tally.so/r/3Nlvlp"))
                        context.startActivity(intent)
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(R.drawable.icon_question_factory),
                    contentDescription = "",
                    modifier = Modifier.padding(start = 16.dp)
                )
                Text(text = "듀이의 질문\n" + "제작소")
                Icon(
                    painter = painterResource(id = R.drawable.icon_caret_right),
                    contentDescription = "",
                    tint = Gray500,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
            Spacer(modifier = Modifier.width(11.dp))
            Row(
                modifier = Modifier
                    .weight(1f)
                    .height(74.dp)
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .clickable {
                        val intent =
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://walla.my/v/7XwE0CxFffPanoQ7TneT")
                            )
                        context.startActivity(intent)

                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(R.drawable.icon_faq),
                    contentDescription = "",
                    modifier = Modifier.padding(start = 16.dp)
                )
                Text(text = "자주 묻는\n" + "질문")
                Icon(
                    painter = painterResource(id = R.drawable.icon_caret_right),
                    contentDescription = "",
                    tint = Gray500,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
        }

        if (isShowPermissionDialog) {
            ShowDoubleButtonDialog(
                title = "알림을 허용하기 위해 설정으로 이동해요.",
                contents = "",
                leftButton = {
                    PrimaryLightButton(
                        modifier = Modifier.weight(1f),
                        size = ButtonSize.LARGE,
                        text = "나중에",
                        onClick = {
                            isShowPermissionDialog = false
                        }
                    )
                },
                rightButton = {
                    PrimaryButton(
                        modifier = Modifier.weight(1f),
                        size = ButtonSize.LARGE,
                        text = "이동하기",
                        onClick = {
                            isShowPermissionDialog = false
                            openNotificationSettings(context)
                        }
                    )
                }
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 40.dp)
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(53.dp)
                        .padding(horizontal = 12.dp)
                        .background(Color.White)
                ) {
                    Row {
                        Image(
                            painter = painterResource(R.drawable.icon_bell), contentDescription = ""
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "푸시 알림 받기")
                    }

                    Switch(checked = isAlarmChecked, onCheckedChange = { checked ->
                        if (checked) {
                            if (isNotificationPermissionGranted(context)) {
                                isAlarmChecked = true // 이미 권한이 있는 경우
                                viewModel.processEvent(
                                    MyPageContract.Event.OnToggleNotificationSwitch(
                                        true
                                    )
                                )
                            } else {
                                isShowPermissionDialog = true // 권한 요청 필요

                            }
                        } else {
                            isAlarmChecked = false
                            viewModel.processEvent(
                                MyPageContract.Event.OnToggleNotificationSwitch(
                                    false
                                )
                            )
                        }
                    })
                }

                val context = LocalContext.current

                items.value.forEach { item ->
                    Row(verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(53.dp)
                            .padding(horizontal = 12.dp)
                            .background(Color.White)
                            .clickable {
                                when (item.actionType) {
                                    ActionType.OPEN_URL -> {
                                        val intent =
                                            Intent(Intent.ACTION_VIEW, Uri.parse(item.destination))
                                        context.startActivity(intent)
                                    }

                                    ActionType.NAVIGATE_SCREEN -> {
                                        navController.navigate(item.destination)
                                    }

                                    ActionType.SEND_EMAIL -> {
                                        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                                            data = Uri.parse("mailto:")
                                            putExtra(
                                                Intent.EXTRA_EMAIL,
                                                arrayOf("tellingmetime@gmail.com")
                                            )
                                            putExtra(
                                                Intent.EXTRA_CC, arrayOf("crin1224@icloud.com")
                                            )
                                            putExtra(Intent.EXTRA_SUBJECT, "[텔링미 고객센터] 전달사항이 있어요!")
                                            putExtra(Intent.EXTRA_TEXT, getEmailBody(appVersion))
                                        }
                                        context.startActivity(
                                            Intent.createChooser(
                                                emailIntent, "[텔링미 고객센터] 전달사항이 있어요!"
                                            )
                                        )
                                    }
                                }
                            }) {
                        Row {
                            Image(
                                painter = painterResource(id = item.iconResId),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)

                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = item.text, style = TellingmeTheme.typography.body2Regular)
                        }

                        Row {
                            if (item.id === "tellingme_introduce") {
                                Text(
                                    text = "v. $appVersion",
                                    style = TellingmeTheme.typography.body2Bold
                                )
                            }
                            Icon(
                                painter = painterResource(id = R.drawable.icon_caret_right),
                                contentDescription = "",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}


fun getEmailBody(appVersion: String): String {
    return "안녕하세요, 텔링미입니다.\n어떤 내용을 텔링미에게 전달하고 싶으신가요? 자유롭게 작성해주시면 확인 후 답변드리겠습니다. 감사합니다.😃\n📲 쓰고 있는 핸드폰 기종 (예:아이폰 12) : \n\n🧭 앱 버전 : ${appVersion}\n🧗 닉네임 : \n\n⚠️ 오류를 발견하셨을 경우 ⚠️\n📍발견한 오류 : \n📷 오류 화면 (캡쳐 혹은 화면녹화) : \n"
}

enum class ActionType {
    OPEN_URL, NAVIGATE_SCREEN, SEND_EMAIL
}

data class IconTextItem(
    val id: String,
    val iconResId: Int,
    val text: String,
    val actionType: ActionType,
    val destination: String
)

@Preview
@Composable
fun MyPageScreenPreview() {
    MyPageScreen(navController = rememberNavController())
}