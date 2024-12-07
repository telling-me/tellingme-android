package com.tellingus.tellingme.data.model.oauth

data class UserResponse(
    val code: Int,
    val message: String,
    val data: User
)

data class User(
    val socialLoginType: String = "",
    val nickname: String = "",
    val gender: String = "",
    val birthDate: String = "",
    val purpose: String = "",
    val job: Int = -1,
    val jobInfo: String? = "",
    val mbti: String? = "",
    val level: Int = 0,
    val exp: Int = 0,
    val requiredExp: Int = 0,
    val isPremium: Boolean? = false,
    val allowNotification: Boolean? = null
)
