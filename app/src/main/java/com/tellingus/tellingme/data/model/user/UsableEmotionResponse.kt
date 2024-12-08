package com.tellingus.tellingme.data.model.user

data class UsableEmotionResponse(
    val code: Int,
    val message: String,
    val data: UsableEmotion
)

data class UsableEmotion(
    val emotionList: List<Int> = emptyList()
)