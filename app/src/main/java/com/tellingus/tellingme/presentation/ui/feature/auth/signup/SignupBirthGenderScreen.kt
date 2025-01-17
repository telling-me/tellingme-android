package com.tellingus.tellingme.presentation.ui.feature.auth.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tellingus.tellingme.R
import com.tellingus.tellingme.presentation.ui.common.component.appbar.BasicAppBar
import com.tellingus.tellingme.presentation.ui.common.component.box.SelectBox
import com.tellingus.tellingme.presentation.ui.common.component.button.PrimaryButton
import com.tellingus.tellingme.presentation.ui.common.component.button.SingleBlackButton
import com.tellingus.tellingme.presentation.ui.common.component.button.TellingmeIconButton
import com.tellingus.tellingme.presentation.ui.common.component.layout.MainLayout
import com.tellingus.tellingme.presentation.ui.common.model.ButtonSize
import com.tellingus.tellingme.presentation.ui.common.navigation.AuthDestinations
import com.tellingus.tellingme.presentation.ui.theme.Base0
import com.tellingus.tellingme.presentation.ui.theme.Error600
import com.tellingus.tellingme.presentation.ui.theme.Gray200
import com.tellingus.tellingme.presentation.ui.theme.Gray300
import com.tellingus.tellingme.presentation.ui.theme.Gray500
import com.tellingus.tellingme.presentation.ui.theme.Gray600
import com.tellingus.tellingme.presentation.ui.theme.Primary400
import com.tellingus.tellingme.presentation.ui.theme.TellingmeTheme
import com.tellingus.tellingme.util.collectWithLifecycle

@Composable
fun SignupBirthGenderScreen(
    navController: NavController,
    viewModel: SignupViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    MainLayout(
        header = {
            BasicAppBar(
                modifier = modifier
                    .fillMaxWidth(),
                leftSlot = {
                    TellingmeIconButton(
                        modifier = modifier
                            .padding(all = 12.dp),
                        iconRes = R.drawable.icon_caret_left,
                        size = ButtonSize.MEDIUM,
                        color = Gray500,
                        onClick = {
                            navController.popBackStack()
                        }
                    )
                }
            )
        },
        content = {
            SignupBirthGenderContentScreen(
                navController = navController,
                viewModel = viewModel
            )
        },
        isScrollable = false,
        background = Base0
    )

}

@Composable
fun SignupBirthGenderContentScreen(
    navController: NavController,
    viewModel: SignupViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var birth by remember { mutableStateOf(uiState.signupRequest.birthDate) }
    var gender by remember { mutableStateOf(uiState.signupRequest.gender) }
    var isFocused by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 30.dp, bottom = 16.dp, start = 20.dp, end = 20.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            Text(
                text = "출생연도와 성별을 알려주세요",
                style = TellingmeTheme.typography.head2Bold,
                color = Gray600
            )
            Spacer(modifier = Modifier.size(55.dp))

            BasicTextField(
                modifier = modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        isFocused = it.isFocused
                    },
                value = birth,
                onValueChange = {
                    if (it.length <=4) {
                        birth = it
                    }
                },
                textStyle = TellingmeTheme.typography.body1Regular.copy(
                    color = Gray600,
                    textAlign = TextAlign.Start
                ),
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                decorationBox = { innerTextField ->
                    Column {
                        Box {
                            Box(
                                modifier = modifier.fillMaxWidth(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                innerTextField()
                                Text(
                                    text = if (birth.isBlank()) "출생연도 4자리" else " ",
                                    style = TellingmeTheme.typography.body1Regular.copy(
                                        color = Gray300
                                    )
                                )
                                if (birth.isNotBlank()) {
                                    Icon(
                                        modifier = modifier
                                            .align(Alignment.CenterEnd)
                                            .clickable(
                                                interactionSource = remember { MutableInteractionSource() },
                                                indication = null,
                                                onClick = {
                                                    birth = ""
                                                }
                                            ),
                                        imageVector = ImageVector.vectorResource(id = R.drawable.icon_clear_text),
                                        contentDescription = null,
                                        tint = Gray300
                                    )
                                }
                            }
                        }
                        Spacer(modifier = modifier.size(8.dp))
                        Box(
                            modifier = modifier
                                .fillMaxWidth()
                                .background(if (isFocused) Primary400 else Gray200)
                                .height(1.dp)

                        )
                        Spacer(modifier = modifier.size(8.dp))
                        Text(
                            modifier = modifier
                                .clickable(
                                    enabled = false,
                                    onClick = {}
                                ),
                            text = if (birth.length == 4 && birth.toInt() > 2025) "올바른 형식으로 다시 입력해주세요." else " ",
                            style = TellingmeTheme.typography.caption1Regular.copy(
                                color = Error600
                            )
                        )
                    }
                }
            )
            Spacer(modifier = modifier.size(26.dp))

            Row(
                modifier = modifier.fillMaxWidth()
            ) {
                SelectBox(
                    modifier = modifier.weight(1f),
                    text = "남성",
                    textStyle = TellingmeTheme.typography.body1Regular,
                    isSelected = gender == Gender.MALE.name,
                    onClick = {
                        gender = Gender.MALE.name
                    }
                )
                Spacer(modifier = modifier.size(11.dp))
                SelectBox(
                    modifier = modifier.weight(1f),
                    text = "여성",
                    textStyle = TellingmeTheme.typography.body1Regular,
                    isSelected = gender == Gender.FEMALE.name,
                    onClick = {
                        gender = Gender.FEMALE.name
                    }
                )
            }
        }
        PrimaryButton(
            modifier = modifier
                .fillMaxWidth(),
            size = ButtonSize.LARGE,
            text = "다음",
            enable = gender.isNotBlank() && birth.length==4 && birth.toInt() <= 2024,
            onClick = {
                if (gender.isNotBlank() && birth.length==4 && birth.toInt() <= 2024) {
                    viewModel.processEvent(
                        SignupContract.Event.NextButtonClickedInBirthGender(
                            birth = birth,
                            gender = gender
                        )
                    )
                } else {

                }
            }
        )
    }

    viewModel.effect.collectWithLifecycle { effect ->
        when(effect) {
            is SignupContract.Effect.MoveToJob -> {
                navController.navigate(AuthDestinations.Signup.SIGNUP_JOB)
            }
            else -> {}
        }
    }


}

enum class Gender(name: String) {
    MALE("MALE"),
    FEMALE("FAMALE")
}

@Preview
@Composable
fun SignupBirthGenderScreenPreview() {
    SignupBirthGenderScreen(navController = rememberNavController())
}