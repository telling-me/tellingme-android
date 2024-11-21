package com.tellingus.tellingme.data.model.home

data class AnswerResponse(
    val content: String,
    val emotion: Int,
    val date: String,
    val isPublic: Boolean,
    val isSpare: Boolean,
)
