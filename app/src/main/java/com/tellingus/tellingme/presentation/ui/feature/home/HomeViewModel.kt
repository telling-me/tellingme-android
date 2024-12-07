package com.tellingus.tellingme.presentation.ui.feature.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import com.tellingus.tellingme.data.model.home.CommunicationData
import com.tellingus.tellingme.data.model.home.HomeRequest
import com.tellingus.tellingme.data.network.adapter.onFailure
import com.tellingus.tellingme.data.network.adapter.onSuccess
import com.tellingus.tellingme.data.repositoryimpl.HomeRepositoryImpl
import com.tellingus.tellingme.domain.repository.DataStoreKey
import com.tellingus.tellingme.domain.repository.DataStoreRepository
import com.tellingus.tellingme.domain.repository.HomeRepository
import com.tellingus.tellingme.domain.usecase.HomeUseCase
import com.tellingus.tellingme.domain.usecase.UpdatePushTokenUseCase
import com.tellingus.tellingme.presentation.ui.common.base.BaseViewModel
import com.tellingus.tellingme.util.getToday
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val homeUseCase: HomeUseCase,
    private val updatePushTokenUseCase: UpdatePushTokenUseCase,
    private val dataStoreRepository: DataStoreRepository
) : BaseViewModel<HomeContract.State, HomeContract.Event, HomeContract.Effect>(
    initialState = HomeContract.State()
) {
    private val TAG = "HomeViewModel"

    init {
        viewModelScope.launch {
            updateState(currentState.copy(denyPushNoti = dataStoreRepository.getBoolean(DataStoreKey.DENY_PUSH_NOTI).first()))
        }
        val today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val request = HomeRequest(
            date = today, page = 0, size = 0, sort = "string"
        )
        getMain(request)

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

    override fun reduceState(event: HomeContract.Event) {
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
                            )
                        )
                    }
                }
            }.onFailure { s, i ->
                Log.d(TAG, "HomeViewModel - getMain() called failure: $s - $i")
            }
        }
    }

    fun getNotice() {
//        viewModelScope.launch {
//            val notice = homeRepository.getNotice()
//                .onSuccess {
//                    Log.d("taag homeViewModel s", it.toString())
//                }
//                .onFailure { s, i ->
//                    Log.d("taag homeViewModel f", "$s - $i")
//                }
//        }
    }

//    fun getQuestion() {
//        viewModelScope.launch {
//            homeRepository.getQuestion()
//        }
//    }
}