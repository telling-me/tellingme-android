package com.tellingus.tellingme.presentation.ui.feature.myspace


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.holix.android.bottomsheetdialog.compose.BottomSheetBehaviorProperties
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialog
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties
import com.holix.android.bottomsheetdialog.compose.NavigationBarProperties
import com.tellingus.tellingme.presentation.ui.common.component.bottomsheet.CustomBottomSheet
import com.tellingus.tellingme.presentation.ui.common.component.box.CheckBox
import com.tellingus.tellingme.presentation.ui.common.component.button.HeartButton
import com.tellingus.tellingme.presentation.ui.common.component.button.PrimaryButton
import com.tellingus.tellingme.presentation.ui.common.component.button.PrimaryLightButton
import com.tellingus.tellingme.presentation.ui.common.component.chip.EmotionChip
import com.tellingus.tellingme.presentation.ui.common.component.dialog.ShowSingleButtonDialog
import com.tellingus.tellingme.presentation.ui.common.const.getMediumEmotion
import com.tellingus.tellingme.presentation.ui.theme.Base0
import com.tellingus.tellingme.presentation.ui.theme.Gray100
import com.tellingus.tellingme.presentation.ui.theme.Gray600
import com.tellingus.tellingme.presentation.ui.theme.Typography
import kotlinx.coroutines.launch

@Composable
fun MySpaceDetailScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: MySpaceViewModel,
    index: Int
) {

    MainLayout(header = {
        BasicAppBar(modifier = modifier
            .fillMaxWidth()
            .padding(start = 12.dp, top = 12.dp, bottom = 12.dp, end = 12.dp),
            leftSlot = {
            TellingmeIconButton(iconRes = R.drawable.icon_caret_left,
                size = ButtonSize.MEDIUM,
                color = Gray500,
                onClick = {
                    navController.popBackStack()
                })
            }
        )
    }, content = {
        OtherSpaceDetailScreenContent(viewModel, index)
    }
    )


}

@Composable
fun OtherSpaceDetailScreenContent(
    viewModel: MySpaceViewModel,
    index: Int
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 0.dp, bottom = 20.dp)
            .fillMaxHeight()
    ) {
        QuestionSection(
            title = uiState.answerList[index].title,
            description = uiState.answerList[index].phrase,
            isButtonVisible = false,
            bgColor = Background100
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White, shape = RoundedCornerShape(20.dp))
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(
                    modifier = Modifier.padding(top = 22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(getMediumEmotion(uiState.answerList[index].emotion)),
                        contentDescription = "",
                        modifier = Modifier.size(52.dp),
                    )
                    EmotionChip(
                        modifier = Modifier.padding(top = 9.5.dp),
                        emotion = uiState.answerList[index].emotion-1
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                            top = 24.dp, bottom = 24.dp, start = 16.dp, end = 16.dp
                        ),
                        text = uiState.answerList[index].content
                    )

                }
            }

        }
    }
}
