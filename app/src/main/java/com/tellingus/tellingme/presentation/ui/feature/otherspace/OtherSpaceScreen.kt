package com.tellingus.tellingme.presentation.ui.feature.otherspace

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tellingus.tellingme.presentation.ui.common.component.appbar.BasicAppBar
import com.tellingus.tellingme.presentation.ui.common.component.card.CommunityCard
import com.tellingus.tellingme.presentation.ui.common.component.layout.MainLayout
import com.tellingus.tellingme.presentation.ui.common.navigation.OtherSpaceDestinations
import com.tellingus.tellingme.presentation.ui.theme.TellingmeTheme
import com.tellingus.tellingme.presentation.ui.theme.Typography

@Composable
fun OtherSpaceScreen(
    navController: NavController,
    otherSpaceViewModel: OtherSpaceViewModel = hiltViewModel()
) {

    val uiState by otherSpaceViewModel.uiState.collectAsStateWithLifecycle()

    MainLayout(
        header = { BasicAppBar() },
        content = { OtherSpaceScreenContent(navController = navController, uiState) },
        isScrollable = false,
    )
}

@Composable
fun OtherSpaceScreenContent(navController: NavController, uiState: OtherSpaceContract.State) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
    ) {

        Text(text = "모두의 공간", style = TellingmeTheme.typography.head2Bold)
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = "모두의 공간은 한 질문당 5일 동안만 열려요!",
            style = TellingmeTheme.typography.body2Regular
        )

        val communications = uiState.communication

        if (communications.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "내용이 없어요", style = Typography.body2Bold)
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(
                    top = 24.dp,
                    bottom = 24.dp
                )
            ) {
                items(items = communications) {
                    CommunityCard(
                        id = it.title,
                        title = it.title,
                        date = "${it.date[0]}- ${it.date[1]} - ${it.date[2]}",
                        commentCount = it.answerCount,
                        onClickCard = { id ->
                            navController.navigate("${OtherSpaceDestinations.OTHER_SPACE}/list/${id}")
                        }
                    )
                }
            }

        }

    }
}

@Preview
@Composable
fun OtherSpaceScreenPreview() {
    OtherSpaceScreen(navController = rememberNavController())
}


data class CommunityItem(
    val id: String,
    val title: String,
    val date: String,
    val commentCount: Int
)

val dummyList = listOf(
    CommunityItem(
        id = "1", title = "소속된 집단에서 내가 주로 맡는 역활은1?",
        date = "오늘",
        commentCount = 1000
    ),
    CommunityItem(
        id = "2", title = "텔링미를 사용하실 때\n어떤 기분과 생각을 하시나요?",
        date = "1일 전",
        commentCount = 100
    ),
    CommunityItem(
        id = "3", title = "소속된 집단에서 내가 주로 맡는 역활은?",
        date = "2일 전",
        commentCount = 1000
    ),
    CommunityItem(
        id = "4", title = "텔링미를 사용하실 때\n어떤 기분과 생각을 하시나요?",
        date = "3일 전",
        commentCount = 100
    ),
    CommunityItem(
        id = "5", title = "소속된 집단에서 내가 주로 맡는 역활은?",
        date = "4일 전",
        commentCount = 1000
    ),
    CommunityItem(
        id = "6", title = "텔링미를 사용하실 때\n어떤 기분과 생각을 하시나요?",
        date = "5일 전",
        commentCount = 100
    )
)
