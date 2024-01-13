package com.tellingus.tellingme.presentation.ui.feature.home


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tellingus.tellingme.R
import com.tellingus.tellingme.presentation.ui.common.appbar.BasicAppBar
import com.tellingus.tellingme.presentation.ui.common.button.BUTTON_STATE
import com.tellingus.tellingme.presentation.ui.common.card.OpinionCard
import com.tellingus.tellingme.presentation.ui.common.chip.ActionChip
import com.tellingus.tellingme.presentation.ui.common.layout.MainLayout
import com.tellingus.tellingme.presentation.ui.common.section.QuestionSection
import com.tellingus.tellingme.presentation.ui.common.widget.LevelSection
import com.tellingus.tellingme.presentation.ui.common.widget.ProfileWidget
import com.tellingus.tellingme.presentation.ui.theme.Primary400
import com.tellingus.tellingme.presentation.ui.theme.TellingmeTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navigateToRecordScreen: () -> Unit, navigateToOtherSpace: (name: String) -> Unit
) {
    val cardList = listOf(
        "happy",
        "excited",
        "happy",
        "excited",
        "happy",
        "excited",
        "happy",
        "excited",
        "happy",
        "excited",
        "happy",
        "excited",
    )

    MainLayout(header = {
        BasicAppBar(
            leftSlot = {
                Image(
                    painter = painterResource(R.drawable.icon_caret_left),
                    contentDescription = "",
                    modifier = Modifier.size(24.dp),
                )
            },
            rightSlot = {
                Text(
                    text = "완료",
                    style = TellingmeTheme.typography.body1Bold,
                    color = Primary400
                )
            }
        )
    }, content = {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
                ProfileWidget(nickname = "듀이듀이", description = "연속 1일째 기록")
            }
            Box(modifier = Modifier.padding(top = 10.dp, start = 20.dp, end = 20.dp)) {
                LevelSection(level = 1, percent = 50)
            }
            Column(modifier = Modifier.padding(top = 32.dp, start = 20.dp, end = 20.dp)) {
                Text(text = "오늘의 질문", style = TellingmeTheme.typography.body1Bold)
                Text(
                    modifier = Modifier.padding(top = 5.dp),
                    text = "12명이 대답했어요!",
                    style = TellingmeTheme.typography.caption1Regular
                )
                QuestionSection(
                    modifier = Modifier.padding(top = 12.dp),
                    title = "지금까지의 나의 인생을 두 단계로\n나눈다면 어느 시점에 구분선을 둘 건가요?",
                    description = "그 역활이 나의 성향을 반영할 수 있어요",
                    onClickButton = navigateToRecordScreen
                )
            }

            Column(modifier = Modifier.padding(start = 20.dp, top = 32.dp)) {
                Text(text = "나와 비슷한 텔러들의 이야기", style = TellingmeTheme.typography.body1Bold)

                HorizontalPager(
                    modifier = Modifier.padding(top = 12.dp),
                    pageCount = cardList.size,
                    contentPadding = PaddingValues(end = 32.dp),
                ) { page ->
                    val item = cardList[page]
                    OpinionCard(
                        modifier = Modifier.padding(end = 12.dp),
                        heartCount = 1234,
                        buttonState = BUTTON_STATE.SELECTED,
                        feeling = item
                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(top = 14.dp, bottom = 30.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ActionChip(
                    onClick = { /*TODO*/ }, text = "더보기"
                )
            }
        }
    })
}



