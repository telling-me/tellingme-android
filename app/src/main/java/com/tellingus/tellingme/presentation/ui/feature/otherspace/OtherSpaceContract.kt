package com.tellingus.tellingme.presentation.ui.feature.otherspace

import com.tellingus.tellingme.data.model.otherspace.CommunicationData
import com.tellingus.tellingme.presentation.ui.common.base.UiEffect
import com.tellingus.tellingme.presentation.ui.common.base.UiEvent
import com.tellingus.tellingme.presentation.ui.common.base.UiState

class OtherSpaceContract {

    data class State(
        val communication: List<CommunicationData> = emptyList()

    ) : UiState

    sealed class Event : UiEvent {}

    sealed class Effect : UiEffect {

    }


}