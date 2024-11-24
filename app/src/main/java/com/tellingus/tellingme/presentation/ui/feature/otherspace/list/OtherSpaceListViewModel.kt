package com.tellingus.tellingme.presentation.ui.feature.otherspace.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.tellingus.tellingme.data.model.home.QuestionData
import com.tellingus.tellingme.data.model.otherspace.CommunicationListData
import com.tellingus.tellingme.data.model.otherspace.Pageable
import com.tellingus.tellingme.data.model.otherspace.PostLikesRequest
import com.tellingus.tellingme.data.model.otherspace.Sort
import com.tellingus.tellingme.data.network.adapter.onFailure
import com.tellingus.tellingme.data.network.adapter.onSuccess
import com.tellingus.tellingme.domain.usecase.GetQuestionUseCase
import com.tellingus.tellingme.domain.usecase.otherspace.GetCommunicationListUseCase
import com.tellingus.tellingme.domain.usecase.otherspace.PostLikesUseCase
import com.tellingus.tellingme.presentation.ui.common.base.BaseViewModel
import com.tellingus.tellingme.presentation.ui.feature.otherspace.KEY_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class OtherSpaceListViewModel @Inject constructor(
    private val getCommunicationListUseCase: GetCommunicationListUseCase,
    private val getQuestionUseCase: GetQuestionUseCase,
    private val postLikesUseCase: PostLikesUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<OtherSpaceListContract.State, OtherSpaceListContract.Event, OtherSpaceListContract.Effect>(
    initialState = OtherSpaceListContract.State(
        communicationListData = CommunicationListData(
            content = emptyList(),
            pageable = Pageable(
                sort = Sort(empty = true, sorted = false, unsorted = true),
                offset = 0,
                pageNumber = 0,
                pageSize = 0,
                paged = false,
                unpaged = true
            ),
            totalPages = 0,
            totalElements = 0,
            last = true,
            size = 0,
            number = 0,
            sort = Sort(empty = true, sorted = false, unsorted = true),
            numberOfElements = 0,
            first = true,
            empty = true
        ),
        questionData = QuestionData(
            date = emptyList(),
            title = "",
            phrase = "",
            spareTitle = "",
            sparePhrase = "",
            userQuestionType = ""
        )
    )
) {

    init {
        val date: String = savedStateHandle[KEY_ID] ?: LocalDate.now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        getQuestion(date)
        getCommunicationList(date, page = 0, sort = "최신순")
    }

    fun getQuestion(date: String) {
        viewModelScope.launch {
            getQuestionUseCase(date).onSuccess {
                updateState(
                    currentState.copy(
                        questionData = it.data
                    )
                )
            }.onFailure { s, i -> }
        }
    }

    fun getCommunicationList(date: String, page: Int, sort: String) {
        viewModelScope.launch {
            getCommunicationListUseCase(date, page, size = 20, sort).onSuccess {
                val communicationData = it.data
                val isLastPage =
                    communicationData.pageable.pageNumber == communicationData.totalPages - 1
                updateState(
                    currentState.copy(
                        communicationListData = communicationData,
                        currentPage = page,
                        isLastPage = isLastPage
                    )
                )
            }.onFailure { s, i ->
                // 실패 시 처리 로직 추가 (예: 에러 메시지 처리)
            }
        }
    }

    fun postLikes(answerId: Int, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            postLikesUseCase(PostLikesRequest((answerId))).onSuccess {
                onSuccess()
            }.onFailure { s, i -> }
        }
    }

    fun loadMoreDataIfNeeded() {
        if (!currentState.isLastPage) {
            // 다음 페이지로 데이터 요청
            val nextPage = currentState.currentPage + 1
            getCommunicationList(
                date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                page = nextPage,
                sort = "최신순"
            )
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

    override fun reduceState(event: OtherSpaceListContract.Event) {
        when (event) {
            is OtherSpaceListContract.Event.OnClickRecently -> {
                updateState(currentState.copy(currentPage = 0, isLastPage = false))
                getCommunicationList(date = event.date, page = 0, sort = "최신순")
            }

            is OtherSpaceListContract.Event.OnClickRelative -> {
                updateState(currentState.copy(currentPage = 0, isLastPage = false))
                getCommunicationList(date = event.date, page = 0, sort = "관련순")
            }

            is OtherSpaceListContract.Event.OnClickEmpathy -> {
                updateState(currentState.copy(currentPage = 0, isLastPage = false))
                getCommunicationList(date = event.date, page = 0, sort = "공감순")
            }

            is OtherSpaceListContract.Event.OnClickHeart -> {
                if (isThrottled()) return // Throttle 적용: 실행 제한

                postLikes(event.answerId) {
                    val updatedContent = currentState.communicationListData.content.map { item ->
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
                            communicationListData = currentState.communicationListData.copy(
                                content = updatedContent
                            )
                        )
                    )
                }
            }
        }
    }
}