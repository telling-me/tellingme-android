package com.tellingus.tellingme.data.model.home

data class AnswerResponse(
    val code: Int,
    val message: String,
    val data: Answer
)

data class Answer(
    val content: String = "",
    val emotion: Int = 0,
    val date: String = "",
    val isPublic: Boolean = false,
    val isSpare: Boolean = false,
    val likeCount: Int = 0
)
