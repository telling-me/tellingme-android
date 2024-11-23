package com.tellingus.tellingme.data.model.user

data class UserBadgeResponse(
    val code: Int,
    val data: List<UserBadgeData>,
    val message: String
)

data class UserBadgeData(
    val badgeCode: String = "",
    val badgeName: String = "",
    val badgeMiddleName: String = "",
    val badgeCondition: String = "",
)