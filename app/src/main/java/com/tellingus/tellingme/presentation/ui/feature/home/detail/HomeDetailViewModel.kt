package com.tellingus.tellingme.presentation.ui.feature.home.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.tellingus.tellingme.data.model.home.Answer
import com.tellingus.tellingme.data.model.otherspace.PostLikesRequest
import com.tellingus.tellingme.data.network.adapter.onFailure
import com.tellingus.tellingme.data.network.adapter.onSuccess
import com.tellingus.tellingme.domain.usecase.GetAnswerByDateUseCase
import com.tellingus.tellingme.domain.usecase.otherspace.PostLikesUseCase
import com.tellingus.tellingme.presentation.ui.common.base.BaseViewModel
import com.tellingus.tellingme.presentation.ui.feature.otherspace.detail.OtherSpaceDetailContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

val TAG: String = "로그"

@HiltViewModel
class OtherSpaceDetailViewModel @Inject constructor(
    private val getAnswerByDateUseCase: GetAnswerByDateUseCase,
    private val postLikesUseCase: PostLikesUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<HomeDetailContract.State, HomeDetailContract.Event, HomeDetailContract.Effect>(
    initialState = HomeDetailContract.State(
        answerData = Answer(
            answerId = 0,
            content = "",
            emotion = 0,
            likeCount = 0,
            isLiked = false,
            isPublic = false,
        )
    )
) {

    init {
        val date: String = savedStateHandle["date"] ?: LocalDate.now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        getAnswerByDate(date)
    }

    fun postLikes(answerId: Int, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            postLikesUseCase(PostLikesRequest((answerId))).onSuccess {
                onSuccess()
            }.onFailure { s, i -> }
        }
    }

    fun getAnswerByDate(date: String) {
        viewModelScope.launch {
            getAnswerByDateUseCase(date).onSuccess {
                updateState(
                    currentState.copy(
                        answerData = it.data
                    )
                )
            }.onFailure { s, i ->

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
    override fun reduceState(event: HomeDetailContract.Event) {
        when (event) {
            is HomeDetailContract.Event.OnClickHeart -> {
                if (isThrottled()) return // Throttle 적용: 실행 제한
                postLikes(event.answerId) {
                    val updatedAnswerData = currentState.answerData.copy(
                        isLiked = !currentState.answerData.isLiked,
                        likeCount = if (currentState.answerData.isLiked) {
                            currentState.answerData.likeCount - 1
                        } else {
                            currentState.answerData.likeCount + 1
                        }
                    )
                    updateState(currentState.copy(answerData = updatedAnswerData))
                }
            }

        }
    }
}