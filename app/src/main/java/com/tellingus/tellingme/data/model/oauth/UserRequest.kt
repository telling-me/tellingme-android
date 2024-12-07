package com.tellingus.tellingme.data.model.oauth

data class UserRequest(
    val nickname: String = "",
    val birthDate: String = "",
    val job: Int = -1,
    val jobInfo: String = "",
    val purpose: String = "",
    val mbti: String = "",
    val gender: String = ""
)
