package com.tellingus.tellingme.presentation.ui.feature.mypage.alarm

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.tellingus.tellingme.data.network.adapter.onFailure
import com.tellingus.tellingme.data.network.adapter.onSuccess
import com.tellingus.tellingme.domain.usecase.notice.LoadNoticeUseCase
import com.tellingus.tellingme.domain.usecase.notice.NoticeRadByNoticeIdUseCase
import com.tellingus.tellingme.domain.usecase.notice.NoticeReadAllUseCase
import com.tellingus.tellingme.presentation.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val loadNoticeUseCase: LoadNoticeUseCase,
    private val noticeReadAllUseCase: NoticeReadAllUseCase,
    private val noticeRadByNoticeIdUseCase: NoticeRadByNoticeIdUseCase
) : BaseViewModel<AlarmContract.State, AlarmContract.Event, AlarmContract.Effect>(
    initialState = AlarmContract.State()
) {
    val TAG: String = "로그"

    init {
        loadAlarmList()
    }

    override fun reduceState(event: AlarmContract.Event) {
        when (event) {
            is AlarmContract.Event.OnClickTotalRead -> {
                postReadAll()
            }

            is AlarmContract.Event.OnClickItemRead -> {
                postReadNotice(event.noticeId)
            }

            is AlarmContract.Event.OnClickItemDelete -> {

            }
        }
    }


    fun deleteNotice(noticeId: Int) {
        // TODO: networkService api 정의
    }

    fun postReadNotice(noticeId: Int) {
        viewModelScope.launch {
            noticeRadByNoticeIdUseCase(noticeId).onSuccess {

            }.onFailure { message, code -> }
        }
    }

    fun postReadAll() {
        viewModelScope.launch {
            noticeReadAllUseCase().onSuccess {
                // TODO: invalidate api
            }.onFailure { message, code ->

            }
        }
    }

    fun loadAlarmList() {
        viewModelScope.launch {
            loadNoticeUseCase().onSuccess { it ->
                currentState.copy(isLoading = true, list = it.data)
            }.onFailure { message, code ->
                currentState.copy(isLoading = false)
                val TAG: String = "로그"
                Log.d(
                    TAG,
                    "AlarmViewModel - loadAlarmList() called Error message: $message, code: $code"
                )
            }
        }
    }

}