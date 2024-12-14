package com.tellingus.tellingme.data.model.home

data class AnswerResponse(
    val code: Int,
    val message: String,
    val data: Answer
)

data class Answer(
    val answerId: Int = 0,
    val content: String = "",
    val emotion: Int = 0,
    val likeCount: Int = 0,
    val isLiked: Boolean = false,
    val isPublic: Boolean = false,
)
