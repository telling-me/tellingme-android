package com.tellingus.tellingme.presentation.ui.feature.mypage

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.tellingus.tellingme.data.model.oauth.User
import com.tellingus.tellingme.data.model.oauth.UserRequest
import com.tellingus.tellingme.data.network.adapter.onFailure
import com.tellingus.tellingme.data.network.adapter.onNetworkError
import com.tellingus.tellingme.data.network.adapter.onSuccess
import com.tellingus.tellingme.domain.repository.DataStoreRepository
import com.tellingus.tellingme.domain.usecase.GetUserInfoUseCase
import com.tellingus.tellingme.domain.usecase.LogoutUseCase
import com.tellingus.tellingme.domain.usecase.SignOutUseCase
import com.tellingus.tellingme.domain.usecase.UpdateUserInfoUseCase
import com.tellingus.tellingme.presentation.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
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

    override fun reduceState(event: MyPageContract.Event) {

    }
}