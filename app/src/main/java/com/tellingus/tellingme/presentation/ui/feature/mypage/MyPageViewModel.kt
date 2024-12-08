package com.tellingus.tellingme.presentation.ui.feature.mypage

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.tellingus.tellingme.R
import com.tellingus.tellingme.data.model.oauth.UserRequest
import com.tellingus.tellingme.data.model.user.UpdateNotificationRequest
import com.tellingus.tellingme.data.network.adapter.onFailure
import com.tellingus.tellingme.data.network.adapter.onNetworkError
import com.tellingus.tellingme.data.network.adapter.onSuccess
import com.tellingus.tellingme.domain.repository.DataStoreKey.NICKNAME
import com.tellingus.tellingme.domain.repository.DataStoreRepository
import com.tellingus.tellingme.domain.usecase.GetUserInfoUseCase
import com.tellingus.tellingme.domain.usecase.LogoutUseCase
import com.tellingus.tellingme.domain.usecase.GetAnswerListUseCase
import com.tellingus.tellingme.domain.usecase.SignOutUseCase
import com.tellingus.tellingme.domain.usecase.UpdateUserInfoUseCase
import com.tellingus.tellingme.domain.usecase.VerifyNicknameUseCase
import com.tellingus.tellingme.domain.usecase.mypage.GetMyPageUseCase
import com.tellingus.tellingme.domain.usecase.user.GetNotificationUseCase
import com.tellingus.tellingme.domain.usecase.user.UpdateNotificationUseCase
import com.tellingus.tellingme.presentation.ui.common.base.BaseViewModel
import com.tellingus.tellingme.presentation.ui.feature.auth.signup.SignupContract
import com.tellingus.tellingme.presentation.ui.feature.home.record.RecordContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val signOutUseCase: SignOutUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase,
    private val getMyPageUseCase: GetMyPageUseCase,
    private val getAnswerListUseCase: GetAnswerListUseCase,
    private val getNotificationUseCase: GetNotificationUseCase,
    private val updateNotificationUseCase: UpdateNotificationUseCase,
    private val verifyNicknameUseCase: VerifyNicknameUseCase
) : BaseViewModel<MyPageContract.State, MyPageContract.Event, MyPageContract.Effect>(initialState = MyPageContract.State()) {
    val TAG: String = "로그"

    init {
        getMypage()
        getNotification()
        getUesrInfo()
    }

    fun getMypage() {
        viewModelScope.launch {
            getMyPageUseCase().onSuccess { response ->
                if (response.code == 200) {
                    response.data?.let { data ->
                        // `userProfile` 데이터 처리
                        val nickname = data.userProfile.nickname
                        val badgeCode = data.userProfile.badgeCode
                        val cheeseBalance = data.userProfile.cheeseBalance
                        val badgeCount = data.userProfile.badgeCount
                        val answerCount = data.userProfile.answerCount
                        val premium = data.userProfile.premium

                        // `level` 데이터 처리
                        val levelDto = data.level.levelDto
                        val level = levelDto.level
                        val currentExp = levelDto.currentExp
                        val requiredExp = levelDto.requiredExp
                        val daysToLevelUp = data.level.daysToLevelUp


                        // 필요한 로직에 따라 상태 업데이트
                        updateState(
                            currentState.copy(
                                nickname = nickname,
                                badgeCode = badgeCode,
                                cheeseBalance = cheeseBalance,
                                badgeCount = badgeCount,
                                answerCount = answerCount,
                                premium = premium,
                                level = level,
                                current_exp = currentExp,
                                required_exp = requiredExp,
                                days_to_level_up = daysToLevelUp
                            )
                        )
                    }
                }

            }.onFailure { m, c ->
                Log.d(TAG, "MyPageViewModel - getMypage() called $m $c")
            }
        }
    }

    private fun getUesrInfo() {
        viewModelScope.launch {
            getUserInfoUseCase().onSuccess {
                updateState(
                    currentState.copy(userInfo = it.data)
                )
                Log.d("taag", it.data.toString())
            }
        }
    }

    fun updateUserInfo(
        nickname: String,
        job: Int,
        jobInfo: String = "",
        purpose: String,
        mbti: String
    ) {
        // 닉네임 검사 후 등록
        viewModelScope.launch {
            verifyNicknameUseCase(nickname).onSuccess {
                if (it.data) {
                    // 중복 X
                    postEffect(MyPageContract.Effect.DisableNickname(text = "정상"))
                } else {
                    updateUserInfoUseCase(
                        userRequest = UserRequest(
                            nickname = nickname,
                            birthDate = currentState.userInfo.birthDate,
                            job = job,
                            jobInfo = jobInfo,
                            purpose = purpose,
                            mbti = mbti,
                            gender = currentState.userInfo.gender
                        )
                    ).onSuccess {
                        Log.d("taag", it.toString())
                        getUesrInfo()
                    }.onFailure { m, c ->
                        Log.d("taag f", c.toString())
                    }.onNetworkError {
                        Log.d("taag", it.message.toString())
                    }
                }
            }.onFailure { message, i ->
                if (message.contains("중복된 닉네임입니다.")) {
                    if (dataStoreRepository.getString(NICKNAME).first() == nickname) {
                        updateUserInfoUseCase(
                            userRequest = UserRequest(
                                nickname = nickname,
                                birthDate = currentState.userInfo.birthDate,
                                job = job,
                                jobInfo = jobInfo,
                                purpose = purpose,
                                mbti = mbti,
                                gender = currentState.userInfo.gender
                            )
                        ).onSuccess {
                            Log.d("taag", it.toString())
                            getUesrInfo()
                        }.onFailure { m, c ->
                            Log.d("taag f", c.toString())
                        }.onNetworkError {
                            Log.d("taag", it.message.toString())
                        }
                    }
                    postEffect(MyPageContract.Effect.DisableNickname(text = "중복된 닉네임입니다."))
                }
            }


        }
    }

    fun signOutUser() {
        viewModelScope.launch {
            signOutUseCase().onSuccess {
                Log.d("taag s", it.toString())
            }.onFailure { m, c ->
                Log.d("taag f", c.toString())
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase().onSuccess {
                dataStoreRepository.deleteAll()
                postEffect(MyPageContract.Effect.MoveToLoginScreen)
            }
        }
    }



    fun getNotification() {
        viewModelScope.launch {
            getNotificationUseCase().onSuccess {
                updateState(currentState.copy(allowNotification = it.data.allowNotification))
            }.onFailure { s, i -> }
        }
    }

    fun updateNotification(notificationStatus: Boolean) {
        viewModelScope.launch {
            updateNotificationUseCase(
                updateNotificationRequest = UpdateNotificationRequest(notificationStatus)
            )
                .onSuccess {
                    updateState(currentState.copy(allowNotification = it.data.allowNotification))
                }
                .onFailure { s, i -> }
        }
    }

    override fun reduceState(event: MyPageContract.Event) {
        when (event) {
            is MyPageContract.Event.OnToggleNotificationSwitch -> {
                updateNotification(event.notificationStatus)
            }
            is MyPageContract.Event.OnClickSignOutButton -> {
            }
        }
    }
}