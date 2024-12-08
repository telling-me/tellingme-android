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
    val levelDto: LevelDto,
    val daysToLevelUp: Int,
)

data class LevelDto(
    val level: Int,
    val currentExp: Int,
    val requiredExp: Int
)
