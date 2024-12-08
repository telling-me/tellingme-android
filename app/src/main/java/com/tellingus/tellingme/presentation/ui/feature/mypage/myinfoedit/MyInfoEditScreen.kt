package com.tellingus.tellingme.presentation.ui.feature.mypage.myinfoedit

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.holix.android.bottomsheetdialog.compose.BottomSheetBehaviorProperties
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialog
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties
import com.holix.android.bottomsheetdialog.compose.NavigationBarProperties
import com.tellingus.tellingme.R
import com.tellingus.tellingme.presentation.ui.common.component.appbar.BasicAppBar
import com.tellingus.tellingme.presentation.ui.common.component.bottomsheet.CustomBottomSheet
import com.tellingus.tellingme.presentation.ui.common.component.button.SingleButton
import com.tellingus.tellingme.presentation.ui.common.component.button.TellingmeIconButton
import com.tellingus.tellingme.presentation.ui.common.component.chip.ChoiceChip
import com.tellingus.tellingme.presentation.ui.common.component.layout.MainLayout
import com.tellingus.tellingme.presentation.ui.common.model.ButtonSize
import com.tellingus.tellingme.presentation.ui.feature.auth.signup.Job
import com.tellingus.tellingme.presentation.ui.feature.auth.signup.Worry
import com.tellingus.tellingme.presentation.ui.feature.mypage.MyPageViewModel
import com.tellingus.tellingme.presentation.ui.theme.Base0
import com.tellingus.tellingme.presentation.ui.theme.Gray100
import com.tellingus.tellingme.presentation.ui.theme.Gray200
import com.tellingus.tellingme.presentation.ui.theme.Gray300
import com.tellingus.tellingme.presentation.ui.theme.Gray50
import com.tellingus.tellingme.presentation.ui.theme.Gray500
import com.tellingus.tellingme.presentation.ui.theme.Gray600
import com.tellingus.tellingme.presentation.ui.theme.Primary400
import com.tellingus.tellingme.presentation.ui.theme.TellingmeTheme
import com.tellingus.tellingme.presentation.ui.theme.Typography

@Composable
fun MyInfoEditScreen(
    navController: NavController,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    MainLayout(
        content = { MyInfoEditScreenContent(navController, viewModel) }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MyInfoEditScreenContent(
    navController: NavController,
    viewModel: MyPageViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var nicknameValue by remember { mutableStateOf(uiState.userInfo.nickname) }
    var isAvailableNickname by remember { mutableStateOf(false) }
    var nicknameErrorState by remember { mutableStateOf("") }
    var isNicknameFocused by remember { mutableStateOf(false) }

    var isJobOthersFocused by remember { mutableStateOf(false) }

    val selectedConcerns = remember { mutableStateListOf<Int>() }
    if (selectedConcerns.size == 0) {
        uiState.userInfo.purpose
            .removeSurrounding("[", "]") // 대괄호 제거
            .takeIf { it.isNotEmpty() } // 빈 문자열일 경우 처리
            ?.split(",") // 쉼표로 분리
            ?.map { it.trim().toInt() } // 각 항목을 정수로 변환
            ?.forEach { selectedConcerns.add(it) } // 각 항목을 mutableStateListOf에 추가
    }

    var selectedJobType by remember { mutableStateOf(uiState.userInfo.job) }
    var jobOthersInputValue by remember { mutableStateOf(
        if (uiState.userInfo.jobInfo == null) "" else uiState.userInfo.jobInfo
    )}

    var selectedMBTI by remember { mutableStateOf(uiState.userInfo.mbti) }

    var isShowJobTypeBottomSheet by remember { mutableStateOf(false) }
    var isShowMBTIBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(nicknameValue) {
        verifyNicknameFormat(
            nickname = nicknameValue,
            isAvailable = {
                isAvailableNickname = it
            },
            nicknameErrorState = {
                nicknameErrorState = it
            }
        )
    }

    LaunchedEffect(selectedJobType) {
        if (selectedJobType != 5) {
            jobOthersInputValue = ""
        }
    }

    Column {
        BasicAppBar(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, top = 5.dp, bottom = 5.dp, end = 10.dp),
            leftSlot = {
                TellingmeIconButton(iconRes = R.drawable.icon_caret_left,
                    size = ButtonSize.MEDIUM,
                    color = Gray500,
                    onClick = {
                        navController.popBackStack()
                    })
            },
            rightSlot = {
                Row {
                    SingleButton(size = ButtonSize.LARGE, text = "완료", onClick = {
                        if (nicknameErrorState == "정상" && selectedConcerns.size != 0) {
                            Log.d("taag", "완료 가능")
                            viewModel.updateUserInfo(
                                nickname = nicknameValue,
                                job = selectedJobType,
                                jobInfo = jobOthersInputValue ?: "",
                                purpose = selectedConcerns.joinToString(prefix = "[", postfix = "]"),
                                mbti = selectedMBTI ?: ""
                            )
                        } else {
                            Log.d("taag", "완료 불가능")
                        }
                    })
                }
            })

        Column(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, top = 16.dp)
                .fillMaxWidth()
        ) {
            FormHelper(title = "닉네임(2-8글자 이내)") {
                EditableTextField(value = nicknameValue,
                    placeholder = "닉네임을 입력해주세요.",
                    onValueChange = { nicknameValue = it },
                    isFocused = isNicknameFocused,
                    onFocusChange = { isNicknameFocused = it },
                    onClearClick = { nicknameValue = "" })
                Spacer(modifier = Modifier.size(4.dp))
                if (nicknameErrorState != "정상") {
                    Text(
                        text = nicknameErrorState,
                        style = Typography.caption1Regular,
                        color = Gray500
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 최대 2개 선택 가능하도록 선택 상태를 토글하는 함수
            fun toggleSelection(index: Int) {
                if (selectedConcerns.contains(index)) {
                    selectedConcerns.remove(index)
                } else {
                    if (selectedConcerns.size < 2) {
                        selectedConcerns.add(index)
                    } else {
                        selectedConcerns.removeAt(0)
                        selectedConcerns.add(index)
                    }
                }
            }


            FormHelper(title = "고민", subTitle = "최대 2가지 중복 선택 가능") {
                FlowRow(
                    maxItemsInEachRow = 3,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    worryList.forEachIndexed { index, worry ->
                        ChoiceChip(
                            text = worry.text,
                            onClick = { toggleSelection(it) },
                            index = index,
                            selected = selectedConcerns.contains(index)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            FormHelper(title = "직업") {
                DropdownSelect(text = jobList[selectedJobType].text, modifier = Modifier.clickable {
                    isShowJobTypeBottomSheet = true
                })
                if (selectedJobType == 5) {
                    EditableTextField(value = jobOthersInputValue!!,
                        placeholder = "기타는 직접 입력해주세요.",
                        onValueChange = { jobOthersInputValue = it },
                        isFocused = isJobOthersFocused,
                        onFocusChange = { isJobOthersFocused = it },
                        onClearClick = { jobOthersInputValue = "" })
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            FormHelper(title = "성별") {
                Text(
                    text = if (uiState.userInfo.gender == "MALE") "남성" else "여성",
                    color = Gray500, style = Typography.body1Regular
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
            FormHelper(title = "출생년도") {
                Text(
                    text = uiState.userInfo.birthDate + "년",
                    color = Gray500,
                    style = Typography.body1Regular
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
            FormHelper(title = "mbti") {
                DropdownSelect(
                    text = (if (selectedMBTI == null) "mbti 선택" else selectedMBTI)!!,
                    modifier = Modifier.clickable {
                        isShowMBTIBottomSheet = true
                    })

            }
        }
    }

    if (isShowJobTypeBottomSheet) {
        JobTypeBottomSheet(
            selectedJobType = selectedJobType,
            onJobTypeSelected = { jobType ->
                selectedJobType = jobType
                isShowJobTypeBottomSheet = false
            },
            onDismiss = { isShowJobTypeBottomSheet = false },
        )
    }

    if (isShowMBTIBottomSheet) {
        MBTIBottomSheet(
            onDismiss = { isShowMBTIBottomSheet = false },
            selectedMBTI = selectedMBTI,
            onMBTISelected = {
                selectedMBTI = it
                isShowMBTIBottomSheet = false
            }
        )
    }

}

@Composable
fun MBTIBottomSheet(selectedMBTI: String?, onMBTISelected: (String) -> Unit, onDismiss: () -> Unit) {
    val mbtiOptions = listOf(
        "ENFJ", "ENFP", "ENTJ", "ENTP",
        "ESFJ", "ESFP", "ESTJ", "ESTP",
        "INFJ", "INFP", "INTJ", "INTP",
        "ISFJ", "ISFP", "ISTJ", "ISTP"
    )

    // LazyListState를 사용하여 리스트의 스크롤 상태 관리
    val listState = rememberLazyListState()

    // 선택된 MBTI 값에 따라 스크롤 위치 조정
    LaunchedEffect(Unit) {
        if (selectedMBTI != null) {
            val index = mbtiOptions.indexOf(selectedMBTI)
            if (index != -1) {
                listState.scrollToItem(index)
            }
        }
    }

    BottomSheetDialog(
        onDismissRequest = { onDismiss() },
        properties = BottomSheetDialogProperties(
            navigationBarProperties = NavigationBarProperties(navigationBarContrastEnforced = false),
            dismissOnClickOutside = true,
            behaviorProperties = BottomSheetBehaviorProperties(isDraggable = false)
        )
    ) {
        CustomBottomSheet(containerModifier = Modifier.heightIn(max = 258.dp)) {
            LazyColumn(state = listState) {
                items(mbtiOptions) { mbtiOption ->
                    Column(
                        modifier = Modifier
                            .background(
                                if (selectedMBTI == mbtiOption) Gray100 else Color.White,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable(onClick = {
                                onMBTISelected(mbtiOption)
                                onDismiss() // 선택 후 바텀시트 닫기
                            })
                    ) {
                        Box(modifier = Modifier.padding(vertical = 16.dp, horizontal = 12.dp)) {
                            Text(
                                text = mbtiOption,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun JobTypeBottomSheet(
    selectedJobType: Int,
    onJobTypeSelected: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    BottomSheetDialog(
        onDismissRequest = { onDismiss() }, properties = BottomSheetDialogProperties(
            navigationBarProperties = NavigationBarProperties(navigationBarContrastEnforced = false),
            /** 하단 시스템 내비게이션과 중첩되는 이슈 해결 **/
            dismissOnClickOutside = false,
            behaviorProperties = BottomSheetBehaviorProperties(isDraggable = true)
        )
    ) {
        CustomBottomSheet {
            jobList.forEachIndexed { index, _ ->
                Column(
                    modifier = Modifier
                        .background(
                            if (selectedJobType == index) Gray100 else Color.White,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable(onClick = {
                            onJobTypeSelected(index)
                            onDismiss() // 선택 후 바텀시트 닫기
                        })
                ) {
                    Box(modifier = Modifier.padding(vertical = 16.dp, horizontal = 12.dp)) {
                        Text(
                            text = jobList[index].text,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EditableTextField(
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    isFocused: Boolean,
    onFocusChange: (Boolean) -> Unit,
    onClearClick: () -> Unit
) {
    BasicTextField(value = value,
        onValueChange = onValueChange,
        textStyle = TellingmeTheme.typography.body1Regular.copy(color = Gray600),
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { onFocusChange(it.isFocused) }) { innerTextField ->
        Column {
            Box(modifier = Modifier.fillMaxWidth()) {
                if (value.isBlank()) {
                    Text(
                        text = placeholder,
                        style = TellingmeTheme.typography.body1Regular.copy(color = Gray300)
                    )
                }
                innerTextField()
                if (value.isNotBlank()) {
                    Icon(imageVector = ImageVector.vectorResource(id = R.drawable.icon_clear_text),
                        contentDescription = null,
                        tint = Gray300,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .clickable { onClearClick() })
                }
            }
            Spacer(modifier = Modifier.size(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(if (isFocused) Primary400 else Gray200)
                    .height(1.dp)
            )
        }
    }
}


@Composable
fun FormHelper(
    modifier: Modifier = Modifier,
    title: String = "",
    subTitle: String = "",
    content: @Composable () -> Unit
) {
    Column(modifier = modifier) {
        Text(text = title, style = Typography.body1Bold, color = Gray600)
        if (subTitle !== "") {
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = subTitle, style = Typography.caption1Regular, color = Gray500)
        }
        Spacer(modifier = Modifier.height(12.dp))
        content()
    }
}

@Composable
fun DropdownSelect(text: String = "", modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(bottom = 12.dp)
            .height(56.dp)
            .background(Gray50, shape = RoundedCornerShape(12.dp))
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(start = 12.dp),
            text = text,
            style = Typography.body2Regular,
            color = Gray600
        )
        Image(
            modifier = Modifier.padding(end = 12.dp),
            painter = painterResource(id = R.drawable.icon_caret_down),
            contentDescription = "icon_caret_down"
        )
    }
}

@Composable
fun MyInfoEditScreenHeader(navController: NavController) {
    BasicAppBar(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 12.dp, top = 5.dp, bottom = 5.dp, end = 10.dp),
        leftSlot = {
            TellingmeIconButton(iconRes = R.drawable.icon_caret_left,
                size = ButtonSize.MEDIUM,
                color = Gray500,
                onClick = {

                    navController.popBackStack()
                })
        },
        rightSlot = {
            Row {
                SingleButton(size = ButtonSize.LARGE, text = "완료", onClick = {

                })
            }
        })
}

fun verifyNicknameFormat(
    nickname: String,
    isAvailable: (Boolean) -> Unit,
    nicknameErrorState: (String) -> Unit,
) {
    isAvailable(false)
    if (nickname.isEmpty()) {
        nicknameErrorState("")
    } else if (nickname.length < 2) {
        nicknameErrorState("닉네임은 2~8글자여야 합니다.")
    } else if (" " in nickname) {
        nicknameErrorState("닉네임에 띄어쓰기가 포함될 수 없습니다.")
    } else if (!"^[가-힣]*$".toRegex().matches(nickname)) {
        nicknameErrorState("닉네임은 한글만 가능합니다. 영문과 숫자, 특수기호는 들어갈 수 없습니다.")
    } else {
        nicknameErrorState("정상")
    }
}

sealed class ConcernType(val label: String, val value: String) {
    object Study : ConcernType("학업 / 진로", "study")
    object Relationship : ConcernType("대인관계", "relationship")
    object Personality : ConcernType("성격 / 가치관", "personality")
    object Habits : ConcernType("행동 / 습관", "habits")
    object Health : ConcernType("건강", "health")
    object Others : ConcernType("기타", "others")
}

sealed class JobType(val label: String, val value: String) {
    object Student : JobType("중·고등학생", "student")
    object Academic : JobType("대학(원)생", "academic")
    object JobSeeker : JobType("취업준비생", "jobSeeker")
    object Worker : JobType("직장인", "worker")
    object HouseWife : JobType("주부", "houseWife")
    object Others : JobType("기타", "others")
}

val jobList = listOf<Job>(
    Job(R.drawable.icon_bagpack, "중·고등학생"),
    Job(R.drawable.icon_graduationcap, "대학(원)생"),
    Job(R.drawable.icon_smiley, "취업준비생"),
    Job(R.drawable.icon_briefcase, "직장인"),
    Job(R.drawable.icon_cookpot, "주부"),
    Job(R.drawable.icon_etc, "기타"),
)

val worryList = listOf<Worry>(
    Worry(R.drawable.icon_pen, "학업/진로"),
    Worry(R.drawable.icon_handshake, "대인관계"),
    Worry(R.drawable.icon_values, "성격/가치관"),
    Worry(R.drawable.icon_magnet, "행동/습관"),
    Worry(R.drawable.icon_health, "건강"),
    Worry(R.drawable.icon_etc, "기타"),
)