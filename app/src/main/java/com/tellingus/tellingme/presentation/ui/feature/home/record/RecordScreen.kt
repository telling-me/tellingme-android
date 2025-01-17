package com.tellingus.tellingme.presentation.ui.feature.home.record

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.holix.android.bottomsheetdialog.compose.BottomSheetBehaviorProperties
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialog
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties
import com.holix.android.bottomsheetdialog.compose.NavigationBarProperties
import com.tellingus.tellingme.R
import com.tellingus.tellingme.data.model.home.HomeRequest
import com.tellingus.tellingme.presentation.ui.common.component.appbar.BasicAppBar
import com.tellingus.tellingme.presentation.ui.common.component.badge.CheeseBadge
import com.tellingus.tellingme.presentation.ui.common.component.button.PrimaryButton
import com.tellingus.tellingme.presentation.ui.common.component.button.PrimaryLightButton
import com.tellingus.tellingme.presentation.ui.common.component.button.SingleButton
import com.tellingus.tellingme.presentation.ui.common.component.button.TellingmeIconButton
import com.tellingus.tellingme.presentation.ui.common.component.dialog.ShowDoubleButtonDialog
import com.tellingus.tellingme.presentation.ui.common.component.layout.MainLayout
import com.tellingus.tellingme.presentation.ui.common.component.toast.TellingmeToast
import com.tellingus.tellingme.presentation.ui.common.model.ButtonSize
import com.tellingus.tellingme.presentation.ui.common.model.ToolTipType
import com.tellingus.tellingme.presentation.ui.common.component.widget.ToolTip
import com.tellingus.tellingme.presentation.ui.common.const.LargeEmotionList
import com.tellingus.tellingme.presentation.ui.common.const.getEmotionText
import com.tellingus.tellingme.presentation.ui.common.const.getLargeEmotion
import com.tellingus.tellingme.presentation.ui.common.navigation.HomeDestinations
import com.tellingus.tellingme.presentation.ui.feature.home.HomeViewModel
import com.tellingus.tellingme.presentation.ui.theme.Background100
import com.tellingus.tellingme.presentation.ui.theme.Base0
import com.tellingus.tellingme.presentation.ui.theme.Gray300
import com.tellingus.tellingme.presentation.ui.theme.Gray500
import com.tellingus.tellingme.presentation.ui.theme.Gray600
import com.tellingus.tellingme.presentation.ui.theme.Gray700
import com.tellingus.tellingme.presentation.ui.theme.Gray800
import com.tellingus.tellingme.presentation.ui.theme.Primary400
import com.tellingus.tellingme.presentation.ui.theme.TellingmeTheme
import com.tellingus.tellingme.util.collectWithLifecycle
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun RecordScreen(
    modifier: Modifier = Modifier,
    viewModel: RecordViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavController,
    date: String,
    type: String
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var showDialogState by remember { mutableStateOf(false) }
    var showToastMessage by remember { mutableStateOf(Pair(false, "")) }

    LaunchedEffect(Unit) {
        viewModel.getQuestion(date)
    }

    MainLayout(
        header = {
            BasicAppBar(
                modifier = modifier
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
                    SingleButton(
                        size = ButtonSize.LARGE,
                        text = "완료",
                        onClick = {
                            viewModel.processEvent(RecordContract.Event.OnClickRecordButton)
                        }
                    )
                }
            )
        },
        content = {
            RecordScreenContent(
                viewModel = viewModel,
                title = uiState.questionResponse.title,
                phrase = uiState.questionResponse.phrase,
                type = type
            )
        },
        isScrollable = false
    )

    if (showDialogState) {
        ShowDoubleButtonDialog(
            modifier = modifier,
            title = "글을 등록할까요?",
            contents = "글을 등록하고 나면 감정을 바꿀 수 없어요.",
            leftButton = {
                PrimaryLightButton(
                    modifier = Modifier.weight(1f),
                    size = ButtonSize.LARGE,
                    text = "취소",
                    onClick = { showDialogState = false }
                )
            },
            rightButton = {
                PrimaryButton(
                    modifier = Modifier.weight(1f),
                    size = ButtonSize.LARGE,
                    text = "완료",
                    onClick = {
                        viewModel.processEvent(RecordContract.Event.RecordAnswer)
                    }
                )
            }
        )
    }

    if (showToastMessage.first) {
        TellingmeToast(context).showToast(
            text = showToastMessage.second,
            icon = R.drawable.icon_warn
        )
        showToastMessage = Pair(false, "")
    }

    viewModel.effect.collectWithLifecycle { effect ->
        when (effect) {
            is RecordContract.Effect.ShowRecordDialog -> {
                showDialogState = true
            }

            is RecordContract.Effect.ShowToastMessage -> {
                showToastMessage = Pair(true, effect.text)
            }

            is RecordContract.Effect.MoveToMoreScreen -> {

            }

            is RecordContract.Effect.CompletePurchaseEmotion -> {

            }

            is RecordContract.Effect.CompleteRecord -> {
                homeViewModel.getNoticeReward()
                val today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                homeViewModel.getMain(HomeRequest(date = today, page = 0, size = 0, sort = ""))
//                navController.navigate(("${HomeDestinations.HOME}"))
                navController.popBackStack()
            }

            is RecordContract.Effect.CompleteUpdate -> {
                navController.popBackStack()
            }

            else -> {}
        }
    }
}

@Composable
fun RecordScreenContent(
    modifier: Modifier = Modifier,
    viewModel: RecordViewModel,
    title: String,
    phrase: String,
    type: String
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var isEmotionBottomSheetOpen by remember { mutableStateOf(false) }

    Box {
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = modifier
                    .weight(1f)
                    .padding(top = 16.dp, start = 20.dp, end = 20.dp, bottom = 31.dp)
            ) {
                Text(
                    text = title,
                    style = TellingmeTheme.typography.body1Regular.copy(
                        color = Gray700,
                    ),
                )
                Spacer(modifier = modifier.size(8.dp))
                Text(
                    text = phrase,
                    style = TellingmeTheme.typography.body2Regular,
                    color = Gray500
                )
                Spacer(modifier = modifier.size(16.dp))

                Card(
                    modifier = modifier
                        .fillMaxSize(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(Base0)
                ) {
                    Column(
                        modifier = modifier
                            .padding(horizontal = 16.dp, vertical = 20.dp)
                    ) {
                        Row(
                            modifier = modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = modifier
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null,
                                        onClick = {
                                            if (type == "1") {
                                                isEmotionBottomSheetOpen = true
                                            }
                                        }
                                    ),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    modifier = modifier.size(52.dp),
                                    imageVector = if (uiState.selectedEmotion == -1) {
                                        ImageVector.vectorResource(R.drawable.emotion_circle)
                                    } else ImageVector.vectorResource(getLargeEmotion(uiState.selectedEmotion + 1)),
                                    contentDescription = null
                                )
                                Spacer(modifier = modifier.size(4.dp))
                                Text(
                                    modifier = modifier
                                        .defaultMinSize(minWidth = 63.dp)
                                        .background(
                                            color = Background100,
                                            shape = RoundedCornerShape(4.dp)
                                        )
                                        .padding(horizontal = 6.dp, vertical = 1.5.dp),
                                    text = if (uiState.selectedEmotion == -1) "?" else getEmotionText(
                                        uiState.selectedEmotion + 1
                                    ),
                                    textAlign = TextAlign.Center,
                                    style = TellingmeTheme.typography.body2Bold,
                                    color = Gray600
                                )
                            }
                            Spacer(modifier = modifier.size(12.dp))
                            if (uiState.selectedEmotion == -1) {
                                ToolTip(
                                    modifier = modifier.padding(top = 4.dp),
                                    type = ToolTipType.BASIC,
                                    text = "감정을 선택해주세요!"
                                )
                            }
                        }
                        Spacer(modifier = modifier.size(12.dp))
                        Text(
                            text = uiState.today,
                            style = TellingmeTheme.typography.caption1Bold,
                            color = Gray300
                        )
                        Spacer(modifier = modifier.size(8.dp))

                        BasicTextField(
                            modifier = modifier.fillMaxWidth(),
                            value = uiState.answer,
                            onValueChange = {
                                if (it.length <= 300) {
                                    viewModel.updateAnswer(it)
                                }
                            },
                            textStyle = TellingmeTheme.typography.body2Regular.copy(
                                color = Gray800,
                                lineHeight = 24.sp,
                                textAlign = TextAlign.Start
                            ),
                            decorationBox = { innerTextField ->
                                Box {
                                    if (uiState.answer.isBlank()) {
                                        Text(
                                            text = "여기를 눌러 작성해주세요!",
                                            style = TellingmeTheme.typography.body2Regular.copy(
                                                color = Gray300
                                            )
                                        )
                                    }
                                    innerTextField()
                                }
                            }
                        )
                    }
                }
            }

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "공개",
                    style = TellingmeTheme.typography.caption1Bold,
                    color = Gray600
                )
                Spacer(modifier = modifier.size(10.dp))
                Switch(
                    modifier = modifier.scale(0.8f),
                    checked = uiState.isPublic,
                    onCheckedChange = {
                        viewModel.processEvent(RecordContract.Event.OnClickOpenSwitch)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Base0,
                        checkedTrackColor = Primary400
                    ),
                    interactionSource = remember { MutableInteractionSource() }
                )
                Spacer(modifier = modifier.weight(1f))
                Text(
                    text = "${uiState.answer.length} / 300",
                    style = TellingmeTheme.typography.caption1Bold,
                    color = Gray600
                )
            }
        }
    }

    if (isEmotionBottomSheetOpen) {
        EmotionBottomSheet(
            viewModel = viewModel,
            selectedEmotion = uiState.selectedEmotion,
            onDismiss = { isEmotionBottomSheetOpen = false },
            onClickCancel = { isEmotionBottomSheetOpen = false },
            onClickConfirm = {
                viewModel.updateSelectedEmotion(it)
                isEmotionBottomSheetOpen = false
            },
            cheeseCount = uiState.cheeseCount,
            usableEmotionList = uiState.usableEmotionList
        )
    }
}

var purchaseIndex = 0

@Composable
fun EmotionBottomSheet(
    viewModel: RecordViewModel,
    selectedEmotion: Int = -1,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onClickCancel: () -> Unit,
    onClickConfirm: (Int) -> Unit,
    cheeseCount: Int = 0,
    usableEmotionList: List<Int>
) {
    var selectedEmotion by remember { mutableStateOf(selectedEmotion) }
    var showBuyEmotionDialog by remember { mutableStateOf(false) }
    var notEnoughDialog by remember { mutableStateOf(false) }

    BottomSheetDialog(
        onDismissRequest = onDismiss,
        properties = BottomSheetDialogProperties(
            navigationBarProperties = NavigationBarProperties(navigationBarContrastEnforced = false),
            /** 하단 시스템 내비게이션과 중첩되는 이슈 해결 **/
            dismissOnClickOutside = false,
            behaviorProperties = BottomSheetBehaviorProperties(isDraggable = false)
        )
    ) {
        Column(
            modifier = modifier
                .background(
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                    color = Background100
                )
                .padding(top = 24.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = modifier.weight(1f)
                ) {
                    Text(
                        text = "이 글 속 나의 감정을 떠올려 봐요",
                        style = TellingmeTheme.typography.body1Bold.copy(
                            color = Gray600,
                        ),
                    )
                    Spacer(modifier = modifier.size(4.dp))
                    Text(
                        text = if (selectedEmotion == -1) "듀이 감정티콘을 선택해주세요" else getEmotionText(
                            selectedEmotion + 1
                        ),
                        style = TellingmeTheme.typography.body2Regular.copy(
                            color = Gray600
                        ),
                    )
                }
                CheeseBadge(cheeseBalance = cheeseCount)
            }
            Spacer(modifier = modifier.size(16.dp))
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(Base0),
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    contentPadding = PaddingValues(vertical = 24.dp)
                ) {
                    itemsIndexed(LargeEmotionList) { position, item ->
                        Box(
                            modifier = modifier
                                .alpha(
                                    if (selectedEmotion == -1 || position == selectedEmotion) {
                                        1f
                                    } else {
                                        0.5f
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                modifier = modifier
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null,
                                        onClick = {
                                            if (position < 6) {
                                                selectedEmotion = position
                                            } else {
                                                if (position + 1 in usableEmotionList) {
                                                    selectedEmotion = position
                                                } else {
                                                    purchaseIndex = position + 1
                                                    if (cheeseCount >= 33) {
                                                        showBuyEmotionDialog = true
                                                    } else {
                                                        notEnoughDialog = true
                                                    }
                                                }
                                            }
                                        }
                                    ),
                                imageVector = ImageVector.vectorResource(item.emotionRes),
                                contentDescription = "emotion"
                            )
                            if (position >= 6) {
                                if (position + 1 !in usableEmotionList) {
                                    Row(
                                        modifier = modifier
                                            .background(
                                                shape = RoundedCornerShape(100.dp),
                                                color = Color.White
                                            )
                                            .padding(horizontal = 8.5.dp, vertical = 3.5.dp)
                                            .align(Alignment.BottomCenter),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Image(
                                            modifier = modifier.size(14.dp),
                                            imageVector = ImageVector.vectorResource(R.drawable.icon_cheese),
                                            contentDescription = null
                                        )
                                        Spacer(modifier = Modifier.size(4.dp))
                                        Text(
                                            text = "33",
                                            style = TellingmeTheme.typography.caption2Bold.copy(
                                                color = Gray600
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = modifier.size(16.dp))
            Row {
                PrimaryLightButton(
                    modifier = modifier.weight(1f),
                    size = ButtonSize.LARGE,
                    text = "취소",
                    onClick = onClickCancel
                )
                Spacer(modifier = modifier.size(8.dp))
                PrimaryButton(
                    modifier = modifier.weight(1f),
                    size = ButtonSize.LARGE,
                    text = "확인",
                    enable = selectedEmotion != -1,
                    onClick = {
                        onClickConfirm(selectedEmotion)
                    }
                )
            }
        }
    }

    if (showBuyEmotionDialog) {
        Dialog(
            onDismissRequest = {
                showBuyEmotionDialog = false
            },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = false
            )
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .background(
                        shape = RoundedCornerShape(20.dp),
                        color = Base0
                    )
                    .padding(top = 30.dp, start = 16.dp, end = 16.dp, bottom = 20.dp)
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "치즈 33개로 구매하시겠어요?",
                    style = TellingmeTheme.typography.body1Bold.copy(
                        color = Gray600
                    )
                )
                Spacer(modifier = Modifier.size(20.dp))
                Image(
                    modifier = modifier.size(120.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.icon_cheese_box),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.size(20.dp))
                Row {
                    PrimaryLightButton(
                        modifier = modifier.weight(1f),
                        size = ButtonSize.LARGE,
                        text = "취소",
                        onClick = {
                            showBuyEmotionDialog = false
                        }
                    )
                    Spacer(modifier = modifier.size(8.dp))
                    PrimaryButton(
                        modifier = modifier.weight(1f),
                        size = ButtonSize.LARGE,
                        text = "구매하기",
                        onClick = {
                            if (cheeseCount >= 33) {
                                viewModel.purchaseEmotion(purchaseIndex)
                                showBuyEmotionDialog = false

                            } else {
                                showBuyEmotionDialog = true
                            }
                        }
                    )
                }
            }
        }
    }

    if (notEnoughDialog) {
        Dialog(
            onDismissRequest = {
                showBuyEmotionDialog = false
            },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = false
            )
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .background(
                        shape = RoundedCornerShape(20.dp),
                        color = Base0
                    )
                    .padding(top = 30.dp, start = 16.dp, end = 16.dp, bottom = 20.dp)
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "치즈가 부족해요!",
                    style = TellingmeTheme.typography.body1Bold.copy(
                        color = Gray600
                    ),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = "텔링미 플러스를 구독하면\n" + "모든 감정을 이용할 수 있어요.",
                    style = TellingmeTheme.typography.body2Regular.copy(
                        color = Gray600
                    )
                )
                Spacer(modifier = Modifier.size(20.dp))
                Image(
                    modifier = modifier.size(120.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.icon_cheese_empty),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.size(20.dp))
                Row {
                    PrimaryButton(
                        modifier = modifier.weight(1f),
                        size = ButtonSize.LARGE,
                        text = "확인",
                        onClick = {
                            notEnoughDialog = false
                        }
                    )
                }
            }
        }
    }
}
