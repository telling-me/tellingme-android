package com.tellingus.tellingme.presentation.ui.feature.mypage.alarm

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.tellingus.tellingme.data.network.adapter.onFailure
import com.tellingus.tellingme.data.network.adapter.onSuccess
import com.tellingus.tellingme.domain.usecase.notice.DeleteNoticeByNoticeIdUseCase
import com.tellingus.tellingme.domain.usecase.notice.LoadNoticeUseCase
import com.tellingus.tellingme.domain.usecase.notice.NoticeReadAllUseCase
import com.tellingus.tellingme.domain.usecase.notice.NoticeReadByNoticeIdUseCase
import com.tellingus.tellingme.presentation.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val loadNoticeUseCase: LoadNoticeUseCase,
    private val noticeReadAllUseCase: NoticeReadAllUseCase,
    private val noticeReadByNoticeIdUseCase: NoticeReadByNoticeIdUseCase,
    private val deleteNoticeByNoticeIdUseCase: DeleteNoticeByNoticeIdUseCase,
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
                deleteNotice(event.noticeId)
            }
        }
    }

    fun deleteNotice(noticeId: Int) {
        viewModelScope.launch {
            deleteNoticeByNoticeIdUseCase(noticeId).onSuccess {
                Log.d(TAG, "AlarmViewModel - deleteNotice() called")
                loadAlarmList()
            }.onFailure { message, code ->
                Log.d(
                    TAG,
                    "AlarmViewModel - deleteNotice() called Error message: $message, code: $code"
                )
            }
        }
    }

    fun postReadNotice(noticeId: Int) {
        viewModelScope.launch {
            noticeReadByNoticeIdUseCase(noticeId).onSuccess {
                Log.d(TAG, "AlarmViewModel - postReadNotice() called")
                loadAlarmList()
            }.onFailure { message, code ->
                Log.d(
                    TAG,
                    "AlarmViewModel - postReadNotice() called Error message: $message, code: $code"
                )
            }
        }
    }

    fun postReadAll() {
        viewModelScope.launch {
            noticeReadAllUseCase().onSuccess {
                loadAlarmList()
                Log.d(TAG, "AlarmViewModel - postReadAll() called")
            }.onFailure { message, code ->
                Log.d(
                    TAG,
                    "AlarmViewModel - postReadAll() called Error message: $message, code: $code"
                )
            }
        }
    }

    fun loadAlarmList() {
        viewModelScope.launch {
            loadNoticeUseCase().onSuccess { it ->
                Log.d(TAG, "AlarmViewModel - loadAlarmList() called ${it.data}")
                updateState(currentState.copy(isLoading = true, list = it.data))
            }.onFailure { message, code ->
                updateState(currentState.copy(isLoading = false))
                Log.d(
                    TAG,
                    "AlarmViewModel - loadAlarmList() called Error message: $message, code: $code"
                )
            }
        }
    }


}