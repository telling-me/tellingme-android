package com.tellingus.tellingme.presentation.ui.feature.otherspace.list

import com.tellingus.tellingme.data.model.otherspace.CommunicationListData
import com.tellingus.tellingme.presentation.ui.common.base.UiEffect
import com.tellingus.tellingme.presentation.ui.common.base.UiEvent
import com.tellingus.tellingme.presentation.ui.common.base.UiState

class OtherSpaceListContract {

    data class State(
        val communicationListData: CommunicationListData

    ) : UiState

    sealed class Event : UiEvent {
        data class OnClickRecently(
            val sort: String
        ) : Event()

        data class OnClickRelative(val sort: String) : Event()
        data class OnClickEmpathy(val sort: String) : Event()
        data class OnClickHeart(val answerId: Int) : Event()


    }

    sealed class Effect : UiEffect {

    }
}