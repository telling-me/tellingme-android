package com.tellingus.tellingme.data.model.home

data class UpdateAnswerRequest(
    val date: String,
    val content: String,
    val isPublic: Boolean,
)
