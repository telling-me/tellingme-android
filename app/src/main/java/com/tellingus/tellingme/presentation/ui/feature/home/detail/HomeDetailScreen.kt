package com.tellingus.tellingme.presentation.ui.feature.home.detail


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tellingus.tellingme.R
import com.tellingus.tellingme.presentation.ui.common.component.appbar.BasicAppBar
import com.tellingus.tellingme.presentation.ui.common.component.button.TellingmeIconButton
import com.tellingus.tellingme.presentation.ui.common.component.card.OpinionCard
import com.tellingus.tellingme.presentation.ui.common.component.layout.MainLayout
import com.tellingus.tellingme.presentation.ui.common.component.section.QuestionSection
import com.tellingus.tellingme.presentation.ui.common.model.ButtonSize
import com.tellingus.tellingme.presentation.ui.common.model.ButtonState
import com.tellingus.tellingme.presentation.ui.theme.Background100
import com.tellingus.tellingme.presentation.ui.theme.Gray500
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeDetailScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    title: String = "",
    phrase: String = "",
    viewModel: OtherSpaceDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MainLayout(header = {
        BasicAppBar(modifier = modifier
            .fillMaxWidth()
            .padding(start = 12.dp, top = 12.dp, bottom = 12.dp, end = 12.dp), leftSlot = {
            TellingmeIconButton(iconRes = R.drawable.icon_caret_left,
                size = ButtonSize.MEDIUM,
                color = Gray500,
                onClick = {
                    navController.popBackStack()
                })
        })
    }, content = { HomeDetailScreenContent(uiState, viewModel, title, phrase) })
}

@Composable
fun HomeDetailScreenContent(
    uiState: HomeDetailContract.State,
    viewModel: OtherSpaceDetailViewModel,
    title: String,
    phrase: String
) {
    val answerData = uiState.answerData

    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 0.dp, bottom = 20.dp)
            .fillMaxHeight()
    ) {
        QuestionSection(
            title = "$title",
            description = "$phrase",
            isButtonVisible = false,
            bgColor = Background100
        )
        OpinionCard(modifier = Modifier.heightIn(min = 774.dp),
            heartCount = answerData.likeCount,
            buttonState = if (answerData.isLiked) ButtonState.ENABLED else ButtonState.DISABLED,
            emotion = answerData.emotion,
            description = answerData.content,
            type = "full",
            onClickHeart = {
                viewModel.processEvent(HomeDetailContract.Event.OnClickHeart(answerData.answerId))
            }

        )
    }
}

@Preview
@Composable
fun HomeDetailScreenPreview() {
    HomeDetailScreen(navController = rememberNavController())
}