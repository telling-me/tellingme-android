package com.tellingus.tellingme.presentation.ui.feature.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.tellingus.tellingme.data.model.home.HomeRequest
import com.tellingus.tellingme.data.network.adapter.onFailure
import com.tellingus.tellingme.data.network.adapter.onSuccess
import com.tellingus.tellingme.data.repositoryimpl.HomeRepositoryImpl
import com.tellingus.tellingme.domain.repository.HomeRepository
import com.tellingus.tellingme.domain.usecase.HomeUseCase
import com.tellingus.tellingme.presentation.ui.common.base.BaseViewModel
import com.tellingus.tellingme.util.getToday
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val homeUseCase: HomeUseCase,
) : BaseViewModel<HomeContract.State, HomeContract.Event, HomeContract.Effect>(
    initialState = HomeContract.State()
) {
    private val TAG = "HomeViewModel"

    init {
        val today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val request = HomeRequest(
            date = today, page = 0, size = 0, sort = "string"
        )
        getMain(request)
    }

    val state = HomeContract.State(
        isLoading = false, todayQuestionCardInfo = HomeContract.State.TodayQuestionCardInfo("", "")
    )

    override fun reduceState(event: HomeContract.Event) {
    }

    fun getMain(req: HomeRequest) {
        viewModelScope.launch {
            homeUseCase(req).onSuccess {
                Log.d(TAG, "HomeViewModel - it: $it")
                if (it.code == 200) {
                    Log.d(TAG, "if문")
                    updateState(currentState.copy(mainData = it.data))
                } else {
                    Log.d(TAG, "else문")
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