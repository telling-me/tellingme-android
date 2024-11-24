package com.tellingus.tellingme.presentation.ui.feature.otherspace.detail

import com.tellingus.tellingme.data.model.home.QuestionData
import com.tellingus.tellingme.data.model.otherspace.AnswerByIdData
import com.tellingus.tellingme.data.model.otherspace.CommunicationListData
import com.tellingus.tellingme.presentation.ui.common.base.UiEffect
import com.tellingus.tellingme.presentation.ui.common.base.UiEvent
import com.tellingus.tellingme.presentation.ui.common.base.UiState

class OtherSpaceDetailContract {

    data class State(
        val questionData: QuestionData,
        val answerData: AnswerByIdData
    ) : UiState

    sealed class Event : UiEvent {
        data class OnClickReport(val answerId: Int, val reason: Int) : Event()
        data class OnClickHeart(val answerId: Int) : Event()

    }

    sealed class Effect : UiEffect {

    }
}