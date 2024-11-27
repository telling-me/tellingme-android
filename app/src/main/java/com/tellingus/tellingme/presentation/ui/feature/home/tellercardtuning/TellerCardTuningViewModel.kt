package com.tellingus.tellingme.presentation.ui.feature.home.tellercardtuning

import androidx.lifecycle.viewModelScope
import com.tellingus.tellingme.data.model.home.PatchTellerCardRequest
import com.tellingus.tellingme.data.network.adapter.onFailure
import com.tellingus.tellingme.data.network.adapter.onSuccess
import com.tellingus.tellingme.domain.usecase.GetMobileTellerCardUseCase
import com.tellingus.tellingme.domain.usecase.PatchTellerCardUseCase
import com.tellingus.tellingme.domain.usecase.user.GetCheeseUseCase
import com.tellingus.tellingme.presentation.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TellerCardTuningViewModel @Inject constructor(
    private val getCheeseUseCase: GetCheeseUseCase,
    private val getMobileTellerCardUseCase: GetMobileTellerCardUseCase,
    private val patchTellerCardUseCase: PatchTellerCardUseCase,
) : BaseViewModel<TellerCardTuningContract.State, TellerCardTuningContract.Event, TellerCardTuningContract.Effect>(
    initialState = TellerCardTuningContract.State()
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
                        levelInfo = it.data.levelInfo
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

    fun patchTellerCard(colorCode: String, badgeCode: String) {
        viewModelScope.launch {
            patchTellerCardUseCase(
                patchTellerCardRequest = PatchTellerCardRequest(colorCode, badgeCode)
            ).onSuccess {

            }.onFailure { s, i -> }
        }
    }

    override fun reduceState(event: TellerCardTuningContract.Event) {
        when (event) {
            is TellerCardTuningContract.Event.OnClickPatchTellerCard -> {
                patchTellerCard(colorCode = event.colorCode, badgeCode = event.badgeCode)
            }
        }
    }
}
