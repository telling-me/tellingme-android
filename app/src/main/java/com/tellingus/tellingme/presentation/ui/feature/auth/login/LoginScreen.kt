package com.tellingus.tellingme.presentation.ui.feature.auth.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tellingus.tellingme.R
import com.tellingus.tellingme.presentation.ui.common.component.button.PrimaryButton
import com.tellingus.tellingme.presentation.ui.common.component.dialog.PushAlertDialog
import com.tellingus.tellingme.presentation.ui.common.component.layout.MainLayout
import com.tellingus.tellingme.presentation.ui.common.model.ButtonSize
import com.tellingus.tellingme.presentation.ui.common.navigation.AuthDestinations
import com.tellingus.tellingme.presentation.ui.common.navigation.HomeDestinations
import com.tellingus.tellingme.presentation.ui.theme.Error400
import com.tellingus.tellingme.presentation.ui.theme.Gray500
import com.tellingus.tellingme.presentation.ui.theme.Profile100
import com.tellingus.tellingme.presentation.ui.theme.TellingmeTheme
import com.tellingus.tellingme.util.collectWithLifecycle
import com.tellingus.tellingme.util.noRippleClickable
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    MainLayout(
        content = {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(color = Profile100),
            ) {
                Column(
                    modifier = modifier
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.tellingme_logo),
                        contentDescription = null
                    )
                    Spacer(modifier = modifier.size(8.dp))
                    Text(
                        modifier = modifier,
                        text = "나를 깨닫는 시간",
                        style = TellingmeTheme.typography.body1Regular.copy(
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    )
                }
                Image(
                    modifier = modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 70.dp)
                        .noRippleClickable {
                            viewModel.processEvent(LoginContract.Event.KakaoLoginButtonClicked(context))
                        },
                    imageVector = ImageVector.vectorResource(R.drawable.kakao_login),
                    contentDescription = null
                )
            }
        },
        isScrollable = false
    )

    viewModel.effect.collectWithLifecycle { effect ->
        when (effect) {
            is LoginContract.Effect.MoveToSignup -> {
                navController.navigate("${AuthDestinations.Signup.SIGNUP_NICKNAME}/${effect.socialId}")
            }
            is LoginContract.Effect.MoveToHome -> {
                navController.navigate(HomeDestinations.HOME) {
                    popUpTo(AuthDestinations.ROUTE) {
                        inclusive = true
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(rememberNavController())
}