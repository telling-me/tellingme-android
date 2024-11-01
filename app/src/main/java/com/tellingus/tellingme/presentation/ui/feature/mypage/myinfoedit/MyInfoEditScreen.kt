package com.tellingus.tellingme.presentation.ui.feature.mypage.myinfoedit

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
fun MyInfoEditScreen(navController: NavController) {
    MainLayout(header = { MyInfoEditScreenHeader(navController) },
        content = { MyInfoEditScreenContent() })
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MyInfoEditScreenContent() {
    var nicknameValue by remember { mutableStateOf("") }
    var isNicknameFocused by remember { mutableStateOf(false) }
    var jobOthersInputValue by remember { mutableStateOf("") }
    var isJobOthersFocused by remember { mutableStateOf(false) }

    var selectedConcerns by remember { mutableStateOf(mutableSetOf<ConcernType>()) }
    var selectedJobType by remember { mutableStateOf<JobType>(JobType.HouseWife) }

    var selectedBirthYear by remember { mutableStateOf("") }
    var selectedMBTI by remember { mutableStateOf("ENFJ") }

    var selectedGender by remember { mutableStateOf("여성") }

    var isShowJobTypeBottomSheet by remember { mutableStateOf(false) }
    var isShowBirthYearBottomSheet by remember { mutableStateOf(false) }
    var isShowMBTIBottomSheet by remember { mutableStateOf(false) }
    var isShowGenderBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(selectedJobType) {
        if (selectedJobType.value !== "others") {
            jobOthersInputValue = ""
        }
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
    ) {
        FormHelper(title = "닉네임(2-8글자 이내)") {
            EditableTextField(value = nicknameValue,
                placeholder = "닉네임을 입력해주세요.",
                onValueChange = { nicknameValue = it },
                isFocused = isNicknameFocused,
                onFocusChange = { isNicknameFocused = it },
                onClearClick = { nicknameValue = "" })
            Text(
                text = "영문, 숫자, 띄어쓰기, 특수문자 불가", style = Typography.caption1Regular, color = Gray500
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 최대 2개 선택 가능하도록 선택 상태를 토글하는 함수
        fun toggleSelection(concernType: ConcernType) {
            selectedConcerns = if (selectedConcerns.contains(concernType)) {
                selectedConcerns - concernType
            } else {
                // 최대 2개까지 선택할 수 있도록 조건을 확인합니다.
                if (selectedConcerns.size < 2) selectedConcerns + concernType else selectedConcerns
            }.toMutableSet()
        }


        FormHelper(title = "고민", subTitle = "최대 2가지 중복 선택 가능") {
            FlowRow(
                maxItemsInEachRow = 3,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                listOf(
                    ConcernType.Study,
                    ConcernType.Relationship,
                    ConcernType.Personality,
                    ConcernType.Habits,
                    ConcernType.Health,
                    ConcernType.Others
                ).forEach { concernType ->
                    ChoiceChip(
                        text = concernType.label,
                        onClick = { toggleSelection(concernType) },
                        selected = concernType in selectedConcerns
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        FormHelper(title = "직업") {
            DropdownSelect(text = "${selectedJobType.label}", modifier = Modifier.clickable {
                isShowJobTypeBottomSheet = true
            })
            if (selectedJobType.value == "others") {
                Spacer(modifier = Modifier.height(12.dp))
                EditableTextField(value = jobOthersInputValue,
                    placeholder = "기타는 직접 입력해주세요.",
                    onValueChange = { jobOthersInputValue = it },
                    isFocused = isJobOthersFocused,
                    onFocusChange = { isJobOthersFocused = it },
                    onClearClick = { jobOthersInputValue = "" })
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
        FormHelper(title = "성별") {
            Text(text = "여성", color = Gray500, style = Typography.body1Regular)
        }

        Spacer(modifier = Modifier.height(32.dp))
        FormHelper(title = "출생년도") {
            Text(text = "1990년", color = Gray500, style = Typography.body1Regular)
        }

        Spacer(modifier = Modifier.height(32.dp))
        FormHelper(title = "mbti") {
            DropdownSelect(text = "${selectedMBTI}", modifier = Modifier.clickable {
                isShowMBTIBottomSheet = true
            })

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

    if (isShowBirthYearBottomSheet) {
        BirthYearBottomSheet(
            onDismiss = { isShowBirthYearBottomSheet = false },
            onYearSelected = { year ->
                selectedBirthYear = year
                isShowBirthYearBottomSheet = false
            },
            selectedYear = "2024"
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

    if (isShowGenderBottomSheet) {
        GenderBottomSheet(
            onDismiss = { isShowGenderBottomSheet = false },
            selectedGender = selectedGender,
            onGenderSelected = {
                selectedGender = it
                isShowGenderBottomSheet = false
            }
        )
    }

}

@Composable
fun GenderBottomSheet(
    selectedGender: String,
    onGenderSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val genderOptions = listOf("남성", "여성")
    BottomSheetDialog(
        onDismissRequest = { onDismiss() },
        properties = BottomSheetDialogProperties(
            navigationBarProperties = NavigationBarProperties(navigationBarContrastEnforced = false),
            dismissOnClickOutside = true,
            behaviorProperties = BottomSheetBehaviorProperties(isDraggable = false)
        )
    ) {
        CustomBottomSheet {
            genderOptions.forEach { gender ->
                Column(
                    modifier = Modifier
                        .background(
                            if (selectedGender == gender) Gray100 else Color.White,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable(onClick = {
                            onGenderSelected(gender)
                            onDismiss() // 선택 후 바텀시트 닫기
                        })
                ) {
                    Box(modifier = Modifier.padding(vertical = 16.dp, horizontal = 12.dp)) {
                        Text(
                            text = gender,
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
fun MBTIBottomSheet(selectedMBTI: String, onMBTISelected: (String) -> Unit, onDismiss: () -> Unit) {
    val mbtiOptions = listOf(
        "ENFJ", "ENFP", "ENTJ", "ENTP",
        "ESFJ", "ESFP", "ESTJ", "ESTP",
        "INFJ", "INFP", "INTJ", "INTP",
        "ISFJ", "ISFP", "ISTJ", "ISTP"
    )

    // LazyListState를 사용하여 리스트의 스크롤 상태 관리
    val listState = rememberLazyListState()

    // 선택된 MBTI 값에 따라 스크롤 위치 조정
    LaunchedEffect(selectedMBTI) {
        val index = mbtiOptions.indexOf(selectedMBTI)
        if (index != -1) {
            listState.scrollToItem(index)
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
fun BirthYearBottomSheet(
    selectedYear: String,
    onDismiss: () -> Unit,
    onYearSelected: (String) -> Unit
) {
    val years = (1900..2024).map { it.toString() }


    // LazyListState를 사용하여 리스트의 스크롤 상태 관리
    val listState = rememberLazyListState()

    // 선택된 값에 따라 스크롤 위치 조정
    LaunchedEffect(selectedYear) {
        val index = years.indexOf(selectedYear)
        if (index != -1) {
            listState.scrollToItem(index)
        }
    }

    BottomSheetDialog(
        onDismissRequest = onDismiss, properties = BottomSheetDialogProperties(
            navigationBarProperties = NavigationBarProperties(navigationBarContrastEnforced = false),
            /** 하단 시스템 내비게이션과 중첩되는 이슈 해결 **/
            dismissOnClickOutside = true,
            behaviorProperties = BottomSheetBehaviorProperties(isDraggable = false)
        )
    ) {
        CustomBottomSheet(containerModifier = Modifier.heightIn(max = 300.dp)) {
            LazyColumn(state = listState) {
                items(years) { year ->
                    val isSelected = year == selectedYear
                    Text(
                        text = year,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onYearSelected(year)
                            }
                            .background(if (isSelected) Gray100 else Color.White) // 선택된 연도의 배경색
                            .padding(16.dp), // 추가 패딩으로 텍스트를 가운데 정렬
                        textAlign = TextAlign.Center, // 텍스트 가운데 정렬
                    )
                }
            }
        }
    }
}

@Composable
fun JobTypeBottomSheet(
    selectedJobType: JobType, onJobTypeSelected: (JobType) -> Unit, onDismiss: () -> Unit
) {
    BottomSheetDialog(
        onDismissRequest = { onDismiss }, properties = BottomSheetDialogProperties(
            navigationBarProperties = NavigationBarProperties(navigationBarContrastEnforced = false),
            /** 하단 시스템 내비게이션과 중첩되는 이슈 해결 **/
            dismissOnClickOutside = false,
            behaviorProperties = BottomSheetBehaviorProperties(isDraggable = true)
        )
    ) {
        CustomBottomSheet {
            listOf(
                JobType.Student,
                JobType.Academic,
                JobType.JobSeeker,
                JobType.Worker,
                JobType.HouseWife,
                JobType.Others
            ).forEach { jobType ->
                Column(
                    modifier = Modifier
                        .background(
                            if (selectedJobType == jobType) Gray100 else Color.White,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable(onClick = {
                            onJobTypeSelected(jobType)
                            onDismiss() // 선택 후 바텀시트 닫기
                        })
                ) {
                    Box(modifier = Modifier.padding(vertical = 16.dp, horizontal = 12.dp)) {
                        Text(
                            text = jobType.label,
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
            .padding(vertical = 12.dp)
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
        .padding(start = 12.dp, top = 5.dp, bottom = 5.dp, end = 10.dp), leftSlot = {
        TellingmeIconButton(iconRes = R.drawable.icon_caret_left,
            size = ButtonSize.MEDIUM,
            color = Gray500,
            onClick = {

                navController.popBackStack()
            })
    }, rightSlot = {
        Row {
            SingleButton(size = ButtonSize.LARGE, text = "완료", onClick = {

            })
        }
    })
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