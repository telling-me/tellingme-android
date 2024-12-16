package com.tellingus.tellingme.presentation.ui.feature.home.detail

import com.tellingus.tellingme.data.model.home.Answer
import com.tellingus.tellingme.data.model.home.QuestionData
import com.tellingus.tellingme.data.model.otherspace.AnswerByIdData
import com.tellingus.tellingme.presentation.ui.common.base.UiEffect
import com.tellingus.tellingme.presentation.ui.common.base.UiEvent
import com.tellingus.tellingme.presentation.ui.common.base.UiState
import com.tellingus.tellingme.presentation.ui.feature.otherspace.detail.OtherSpaceDetailContract

class HomeDetailContract {

    data class State(
        val answerData: Answer,
    ) : UiState

    sealed class Event : UiEvent {
        data class OnClickHeart(val answerId: Int) : Event()

    }

    sealed class Effect : UiEffect {

    }
}