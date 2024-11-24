package com.tellingus.tellingme.data.model.otherspace

data class GetAnswerByIdResponse(
    val code: Int,
    val data: AnswerByIdData,
    val message: String
)

data class AnswerByIdData(
    val answerId: Int,
    val content: String,
    val emotion: Int,
    val likeCount: Int,
    val isLiked: Boolean,
    val isPublic: Boolean,
)
