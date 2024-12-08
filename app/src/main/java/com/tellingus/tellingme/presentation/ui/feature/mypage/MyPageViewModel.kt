package com.tellingus.tellingme.presentation.ui.feature.mypage

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.tellingus.tellingme.data.model.oauth.User
import com.tellingus.tellingme.data.model.oauth.UserRequest
import com.tellingus.tellingme.data.model.user.UpdateNotificationRequest
import com.tellingus.tellingme.data.model.oauth.User
import com.tellingus.tellingme.data.model.oauth.UserRequest
import com.tellingus.tellingme.data.network.adapter.onFailure
import com.tellingus.tellingme.data.network.adapter.onNetworkError
import com.tellingus.tellingme.data.network.adapter.onSuccess
import com.tellingus.tellingme.domain.repository.DataStoreRepository
import com.tellingus.tellingme.domain.usecase.GetUserInfoUseCase
import com.tellingus.tellingme.domain.usecase.LogoutUseCase
import com.tellingus.tellingme.domain.usecase.GetAnswerListUseCase
import com.tellingus.tellingme.domain.repository.DataStoreRepository
import com.tellingus.tellingme.domain.usecase.GetUserInfoUseCase
import com.tellingus.tellingme.domain.usecase.LogoutUseCase
import com.tellingus.tellingme.domain.usecase.SignOutUseCase
import com.tellingus.tellingme.domain.usecase.UpdateUserInfoUseCase
import com.tellingus.tellingme.domain.usecase.mypage.GetMyPageUseCase
import com.tellingus.tellingme.domain.usecase.user.GetNotificationUseCase
import com.tellingus.tellingme.domain.usecase.user.UpdateNotificationUseCase
import com.tellingus.tellingme.domain.usecase.UpdateUserInfoUseCase
import com.tellingus.tellingme.presentation.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val getMyPageUseCase: GetMyPageUseCase,
    private val getAnswerListUseCase: GetAnswerListUseCase,
    private val getNotificationUseCase: GetNotificationUseCase,
    private val updateNotificationUseCase: UpdateNotificationUseCase
) : BaseViewModel<MyPageContract.State, MyPageContract.Event, MyPageContract.Effect>(initialState = MyPageContract.State()) {
    val TAG: String = "로그"

    init {
        getMypage()
        getNotification()
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
                        val levelDto = data.level.level_dto
                        val level = levelDto.level
                        val currentExp = levelDto.current_exp
                        val requiredExp = levelDto.required_exp
                        val daysToLevelUp = data.level.days_to_level_up


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

    private val dataStoreRepository: DataStoreRepository,
    private val signOutUseCase: SignOutUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase
    private val dataStoreRepository: DataStoreRepository,
    private val signOutUseCase: SignOutUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase
): BaseViewModel<MyPageContract.State, MyPageContract.Event, MyPageContract.Effect>(initialState = MyPageContract.State()) {

    init {
        getUesrInfo()
    }

    fun getUesrInfo() {
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
        viewModelScope.launch {
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