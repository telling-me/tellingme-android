package com.tellingus.tellingme.presentation.ui.feature.home.tellercard

import androidx.lifecycle.viewModelScope
import com.tellingus.tellingme.data.network.adapter.onFailure
import com.tellingus.tellingme.data.network.adapter.onSuccess
import com.tellingus.tellingme.domain.usecase.GetMobileTellerCardUseCase
import com.tellingus.tellingme.domain.usecase.user.GetCheeseUseCase
import com.tellingus.tellingme.presentation.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TellerCardViewModel @Inject constructor(
    private val getCheeseUseCase: GetCheeseUseCase,
    private val getMobileTellerCardUseCase: GetMobileTellerCardUseCase
) : BaseViewModel<TellerCardContract.State, TellerCardContract.Event, TellerCardContract.Effect>(
    initialState = TellerCardContract.State()
) {
    init {
        getMobileTellerCard()
        getCheese()
    }
    fun getMobileTellerCard() {
        viewModelScope.launch {
            getMobileTellerCardUseCase().onSuccess {
                updateState(
                    currentState.copy(
                        badges = it.data.badges,
                        colors = it.data.colors,
                        userInfo = it.data.userInfo,
                        levelInfo = it.data.levelInfo,
                        recordCount = it.data.recordCount
                    )
                )
            }.onFailure { s, i -> }
        }
    }

    fun getCheese() {
        viewModelScope.launch {
            getCheeseUseCase().onSuccess {
                updateState(currentState.copy(cheeseBalance = it.data.cheeseBalance))
            }.onFailure { s, i -> }
        }
    }

    override fun reduceState(event: TellerCardContract.Event) {
        TODO("Not yet implemented")
    }
}