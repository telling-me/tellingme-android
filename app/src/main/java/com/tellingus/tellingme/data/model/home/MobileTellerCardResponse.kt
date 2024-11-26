package com.tellingus.tellingme.data.model.home

data class MobileTellerCardResponse(
    val code: Int,
    val data: MobileTellerCardData,
    val message: String
)


data class MobileTellerCardData(
    val badges: List<Badge>,
    val colors: List<Color>,
    val userInfo: UserInfo,
    val levelInfo: LevelInfo
)

data class Badge(
    val badgeCode: String,
    val badgeName: String,
    val badgeMiddleName: String,
    val badgeCondition: String
)

data class Color(
    val colorCode: String
)

data class UserInfo(
    val nickname: String,
    val cheeseBalance: Int,
    val tellerCard: TellerCard
)

data class TellerCard(
    val badgeCode: String,
    val badgeName: String,
    val badgeMiddleName: String,
    val colorCode: String
)

data class LevelInfo(
    val level_dto: LevelDto,
    val days_to_level_up: Int
)

data class LevelDto(
    val level: Int,
    val current_exp: Int,
    val required_exp: Int
)
