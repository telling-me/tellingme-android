package com.tellingus.tellingme.presentation.ui.feature.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import com.tellingus.tellingme.data.model.home.HomeRequest
import com.tellingus.tellingme.data.model.otherspace.PostLikesRequest
import com.tellingus.tellingme.data.network.adapter.onFailure
import com.tellingus.tellingme.data.network.adapter.onNetworkError
import com.tellingus.tellingme.data.network.adapter.onSuccess
import com.tellingus.tellingme.domain.repository.DataStoreKey
import com.tellingus.tellingme.domain.repository.DataStoreRepository
import com.tellingus.tellingme.domain.usecase.GetNoticeRewardUseCase
import com.tellingus.tellingme.domain.usecase.HomeUseCase
import com.tellingus.tellingme.domain.usecase.UpdatePushTokenUseCase
import com.tellingus.tellingme.domain.usecase.notice.GetNoticeSummaryUseCase
import com.tellingus.tellingme.domain.usecase.otherspace.PostLikesUseCase
import com.tellingus.tellingme.presentation.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase,
    private val updatePushTokenUseCase: UpdatePushTokenUseCase,
    private val dataStoreRepository: DataStoreRepository,
    private val postLikesUseCase: PostLikesUseCase,
    private val getNoticeRewardUseCase: GetNoticeRewardUseCase,
    private val getNoticeSummaryUseCase: GetNoticeSummaryUseCase
) : BaseViewModel<HomeContract.State, HomeContract.Event, HomeContract.Effect>(
    initialState = HomeContract.State()
) {
    private val TAG = "로그"

    init {
        viewModelScope.launch {
            updateState(
                currentState.copy(
                    denyPushNoti = dataStoreRepository.getBoolean(DataStoreKey.DENY_PUSH_NOTI)
                        .first()
                )
            )
        }
        val today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        val request = HomeRequest(
            date = today, page = 0, size = 0, sort = ""
        )

        getMain(request)
        getNoticeSummary()

        FirebaseMessaging.getInstance().token
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    viewModelScope.launch {
                        updatePushTokenUseCase(it.result)
                    }
                }
            }
    }

    val state = HomeContract.State(
        isLoading = false, todayQuestionCardInfo = HomeContract.State.TodayQuestionCardInfo("", "")
    )

    fun denyPushNoti(state: Boolean) {
        viewModelScope.launch {
            dataStoreRepository.setBoolean(DataStoreKey.DENY_PUSH_NOTI, state)
        }
    }

    fun postLikes(answerId: Int, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            postLikesUseCase(PostLikesRequest((answerId))).onSuccess {
                onSuccess()
            }.onFailure { s, i -> }
        }
    }

    fun getNoticeSummary() {
        viewModelScope.launch {
            getNoticeSummaryUseCase().onSuccess {
                updateState(currentState.copy(isUnReadNotice = it.data.status))
            }.onFailure { s, i -> }
        }
    }

    fun getNoticeReward() {
        viewModelScope.launch {
            getNoticeRewardUseCase().onSuccess {
                if (it.data.isNotEmpty()) {
                    Log.d("taag getNoticeReward", it.data[0].content)
                    postEffect(HomeContract.Effect.ShowToastMessage(it.data[0].content))
                }
            }.onFailure { s, i ->
                Log.d("taag getNoticeReward", s)
            }.onNetworkError {
                Log.d("taag getNoticeReward", it.message.toString())
            }
        }
    }

    fun getMain(req: HomeRequest) {
        viewModelScope.launch {
            homeUseCase(req).onSuccess {
                if (it.code == 200) {
                    it.data.apply {
                        updateState(
                            currentState.copy(
                                recordCount = recordCount,
                                todayAnswerCount = todayAnswerCount,
                                communicationList = communicationList,
                                unreadNoticeStatus = unreadNoticeStatus,
                                questionTitle = questionTitle,
                                questionPhrase = questionPhrase,
                                userNickname = userNickname,
                                userLevel = userLevel,
                                userExp = userExp,
                                requiredExp = requiredExp,
                                daysToLevelUp = daysToLevelUp,
                                todayAnswer = todayAnswer,
                                badgeCode = badgeCode,
                                colorCode = colorCode,
                            )
                        )
                    }
                }
            }.onFailure { s, i ->
                Log.d(TAG, "HomeViewModel - getMain() called failure: $s - $i")
            }
        }
    }

    private var lastExecutedTime: Long = 0L
    private val THROTTLE_INTERVAL = 500L // 0.5초 (밀리초 단위)
    fun isThrottled(): Boolean {
        val currentTime = System.currentTimeMillis()
        return if (currentTime - lastExecutedTime < THROTTLE_INTERVAL) {
            true // Throttle 중
        } else {
            lastExecutedTime = currentTime
            false // 실행 가능
        }
    }

    override fun reduceState(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.OnClickHeart -> {
                if (isThrottled()) return

                postLikes(event.answerId) {
                    val updatedList = currentState.communicationList.map { item ->
                        if (item.answerId == event.answerId) {
                            item.copy(
                                isLiked = !item.isLiked,
                                likeCount = item.likeCount + if (item.isLiked) -1 else 1
                            )
                        } else {
                            item
                        }
                    }

                    updateState(
                        currentState.copy(
                            communicationList = updatedList,
                        )
                    )
                }
            }

            else -> {}
        }
    }
}