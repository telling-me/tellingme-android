package com.tellingus.tellingme.presentation.ui.feature.auth.signup

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.tellingus.tellingme.data.model.oauth.login.OauthRequest
import com.tellingus.tellingme.data.model.oauth.signup.SignUpRequest
import com.tellingus.tellingme.data.network.adapter.onFailure
import com.tellingus.tellingme.data.network.adapter.onNetworkError
import com.tellingus.tellingme.data.network.adapter.onSuccess
import com.tellingus.tellingme.domain.repository.DataStoreKey.KAKAO_OAUTH_KEY
import com.tellingus.tellingme.domain.repository.DataStoreKey.NICKNAME
import com.tellingus.tellingme.domain.repository.DataStoreKey.SOCIAL_ID
import com.tellingus.tellingme.domain.repository.DataStoreRepository
import com.tellingus.tellingme.domain.usecase.LoginUseCase
import com.tellingus.tellingme.domain.usecase.SignUpUseCase
import com.tellingus.tellingme.domain.usecase.VerifyNicknameUseCase
import com.tellingus.tellingme.presentation.ui.common.base.BaseViewModel
import com.tellingus.tellingme.presentation.ui.feature.auth.login.IsAuto
import com.tellingus.tellingme.presentation.ui.feature.auth.login.LoginContract
import com.tellingus.tellingme.presentation.ui.feature.auth.login.LoginType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val verifyNicknameUseCase: VerifyNicknameUseCase,
    private val signupUseCase: SignUpUseCase,
    private val dataStoreRepository: DataStoreRepository,
    private val loginUseCase: LoginUseCase
): BaseViewModel<SignupContract.State, SignupContract.Event, SignupContract.Effect>(
    initialState = SignupContract.State()
) {
    fun initLoginInfo(socialId: String, socialLoginType: String) {
        updateState(currentState.copy(
            signupRequest = currentState.signupRequest.copy(
                socialId = socialId,
                socialLoginType = socialLoginType
            )
        ))
    }

    override fun reduceState(event: SignupContract.Event) {
        when(event) {
            is SignupContract.Event.NextButtonClickedInTerms -> {
                postEffect(SignupContract.Effect.MoveToBirthGender)
            }
            is SignupContract.Event.NextButtonClickedInBirthGender -> {
                updateBirthGender(birth = event.birth, gender = event.gender)
                postEffect(SignupContract.Effect.MoveToJob)
            }
            is SignupContract.Event.NextButtonClickedInJob -> {
                updateJob(job = event.job)
                postEffect(SignupContract.Effect.MoveToWorry)
            }
            is SignupContract.Event.NextButtonClickedInWorry -> {
                updateWorry(worry = event.worry)
            }
        }
    }

    private fun signup(signupRequest: SignUpRequest) {
        viewModelScope.launch {
            signupUseCase(
                signupRequest = signupRequest
            ).onSuccess {
                Log.d("taag joinUser", it.toString())
                dataStoreRepository.setString(NICKNAME, currentState.signupRequest.nickname)

                loginUseCase(
                    oauthToken = dataStoreRepository.getString(KAKAO_OAUTH_KEY).first(),
                    loginType = LoginType.KAKAO.name.lowercase(),
                    isAuto = IsAuto.MANUAL.name.lowercase(),
                    oauthRequest = OauthRequest(socialId = dataStoreRepository.getString(SOCIAL_ID).first())
                ).onSuccess {
                    // 자동 로그인인 경우 바로 홈 화면으로 진입
                    dataStoreRepository.setJwtTokens(
                        accessToken = it.data.accessToken,
                        refreshToken = it.data.refreshToken
                    )
                    postEffect(SignupContract.Effect.CompleteSignup)
                }.onFailure { m, s ->

                }

            }.onFailure { message, code ->
            }
        }
    }

    fun verifyNicknameFormat(nickname: String) {
        updateState(currentState.copy(isAvailableNickname = false))
        if (nickname.isEmpty()) {
            updateState(currentState.copy(nicknameErrorState = ""))
        } else if (nickname.length < 2) {
            updateState(currentState.copy(nicknameErrorState = "닉네임은 2~8글자여야 합니다."))
        } else if (" " in nickname) {
            updateState(currentState.copy(nicknameErrorState = "닉네임에 띄어쓰기가 포함될 수 없습니다."))
        } else if (!"^[가-힣]*$".toRegex().matches(nickname)) {
            updateState(currentState.copy(nicknameErrorState = "닉네임은 한글만 가능합니다. 영문과 숫자, 특수기호는 들어갈 수 없습니다."))
        } else {
            updateState(currentState.copy(nicknameErrorState = "정상"))
        }
    }

    fun updateJobInfo(jobInfo: String) {
        viewModelScope.launch {
            updateState(
                currentState.copy(
                    signupRequest = currentState.signupRequest.copy(
                        jobInfo = jobInfo
                    )
                )
            )
        }
    }

    fun verifyNickname(nickname: String) {
        viewModelScope.launch {
            verifyNicknameUseCase(
                nickname = nickname
            ).onSuccess {
                if (it.data) {
                    updateState(
                        currentState.copy(
                            isAvailableNickname = true,
                            signupRequest = currentState.signupRequest.copy(
                                nickname = nickname
                            )
                        )
                    )
                    postEffect(SignupContract.Effect.ShowTermsBottomSheet)
                } else {
                    updateState(
                        currentState.copy(
                            isAvailableNickname = false,
                            nicknameErrorState = it.message
                        )
                    )
                }
            }.onFailure { message, code ->
                if (message.contains("중복된 닉네임입니다.")) {
                    updateState(
                        currentState.copy(
                            isAvailableNickname = false,
                            nicknameErrorState = "중복된 닉네임입니다."
                        )
                    )
                }
            }.onNetworkError {

            }
        }
    }

    private fun updateBirthGender(birth: String, gender: String) {
        viewModelScope.launch {
            updateState(
                currentState.copy(
                    signupRequest = currentState.signupRequest.copy(
                        birthDate = birth,
                        gender = gender
                    )
                )
            )
        }
    }

    private fun updateJob(job: Int) {
        viewModelScope.launch {
            updateState(
                currentState.copy(
                    signupRequest = currentState.signupRequest.copy(
                        job = job
                    )
                )
            )
        }
    }

    private fun updateWorry(worry: List<Int>) {
        viewModelScope.launch {
            updateState(
                currentState.copy(
                    signupRequest = currentState.signupRequest.copy(
                        purpose = worry.toString()
                    )
                )
            )
            signup(currentState.signupRequest)
        }
    }
}