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
    val levelInfo: LevelInfo,
    val recordCount: Int,
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
    val levelDto: LevelDto,
    val daysToLevelUp: Int
)

data class LevelDto(
    val level: Int,
    val currentExp: Int,
    val requiredExp: Int
)
