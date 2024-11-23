package com.tellingus.tellingme.data.model.myspace

data class MypageResponse(
    val code: Int,
    val message: String,
    val data: List<MypageData>
)

data class MypageData(
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
    val level: Level,
    val days_to_level_up: Int,
)

data class Level(
    val level_dto: LevelDto
)

data class LevelDto(
    val level: Int,
    val current_exp: Int,
    val required_exp: Int
)
