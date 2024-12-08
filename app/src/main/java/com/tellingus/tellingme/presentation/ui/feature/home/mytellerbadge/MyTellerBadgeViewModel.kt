package com.tellingus.tellingme.presentation.ui.feature.home.mytellerbadge

import androidx.lifecycle.viewModelScope
import com.tellingus.tellingme.data.network.adapter.onFailure
import com.tellingus.tellingme.data.network.adapter.onSuccess
import com.tellingus.tellingme.domain.usecase.user.GetUserBadgeUseCase
import com.tellingus.tellingme.presentation.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyTellerBadgeViewModel @Inject constructor(
    private val getUserBadgeUseCase: GetUserBadgeUseCase,
) : BaseViewModel<MyTellerBadgeContract.State, MyTellerBadgeContract.Event, MyTellerBadgeContract.Effect>(
    initialState = MyTellerBadgeContract.State()
) {
    init {
        getUserBadge()
    }

    fun getUserBadge() {
        viewModelScope.launch {
            getUserBadgeUseCase().onSuccess {
                updateState(currentState.copy(userBadgeList = it.data))
            }.onFailure { s, i -> }
        }
    }

    override fun reduceState(event: MyTellerBadgeContract.Event) {
        TODO("Not yet implemented")
    }
}