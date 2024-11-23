package com.tellingus.tellingme.presentation.ui.feature.mypage

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.tellingus.tellingme.data.network.adapter.onFailure
import com.tellingus.tellingme.data.network.adapter.onSuccess
import com.tellingus.tellingme.domain.usecase.GetAnswerListUseCase
import com.tellingus.tellingme.domain.usecase.SignOutUseCase
import com.tellingus.tellingme.domain.usecase.mypage.GetMyPageUseCase
import com.tellingus.tellingme.presentation.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val getMyPageUseCase: GetMyPageUseCase,
    private val getAnswerListUseCase: GetAnswerListUseCase
) : BaseViewModel<MyPageContract.State, MyPageContract.Event, MyPageContract.Effect>(initialState = MyPageContract.State()) {
    val TAG: String = "로그"

    init {
        getMypage()
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

    fun signOutUser() {
        viewModelScope.launch {
            signOutUseCase().onSuccess {
                Log.d("taag s", it.toString())
            }.onFailure { m, c ->
                Log.d("taag f", c.toString())
            }
        }
    }

    override fun reduceState(event: MyPageContract.Event) {

    }
}