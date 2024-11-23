package com.tellingus.tellingme.presentation.ui.feature.otherspace

import androidx.lifecycle.viewModelScope
import com.tellingus.tellingme.data.network.adapter.onFailure
import com.tellingus.tellingme.data.network.adapter.onSuccess
import com.tellingus.tellingme.domain.usecase.otherspace.GetCommunicationUseCase
import com.tellingus.tellingme.presentation.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class OtherSpaceViewModel @Inject constructor(
    private val getCommunicationUseCase: GetCommunicationUseCase
) : BaseViewModel<OtherSpaceContract.State, OtherSpaceContract.Event, OtherSpaceContract.Effect>(
    initialState = OtherSpaceContract.State()
) {

    init {
        val date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        getCommunication(date)
    }

    fun getCommunication(date: String) {
        viewModelScope.launch {
            getCommunicationUseCase(date).onSuccess {
                updateState(currentState.copy(communication = it.data))
            }.onFailure { s, i ->
            }
        }
    }

    override fun reduceState(event: OtherSpaceContract.Event) {

    }
}