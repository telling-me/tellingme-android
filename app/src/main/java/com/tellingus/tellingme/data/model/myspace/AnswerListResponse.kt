package com.tellingus.tellingme.data.model.myspace

data class AnswerListResponse(
    val code: Int,
    val message: String,
    val data: List<Answer>
)

data class Answer(
    val answerId: Int = 0,
    val emotion: Int = 0,
    val title: String = "",
    val phrase: String = "",
    val date: List<Int> = emptyList(),
    val content: String = "",
    val spareTitle: String = "",
    val sparePhrase: String = "",
    val isSpare: Boolean = false,
)
