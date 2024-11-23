package com.tellingus.tellingme.data.model.myspace

data class MyPageResponse(
    val code: Int,
    val message: String,
    val data: MyPageData
)

data class MyPageData(
    val userProfile: UserProfile,
    val level: LevelData
)

data class UserProfile(
    val nickname: String,
    val badgeCode: String,
    val cheeseBalance: Int,
    val badgeCount: Int,
    val answerCount: Int,
    val premium: Boolean
)

data class LevelData(
    val level_dto: LevelDto,
    val days_to_level_up: Int,
)

data class LevelDto(
    val level: Int,
    val current_exp: Int,
    val required_exp: Int
)
