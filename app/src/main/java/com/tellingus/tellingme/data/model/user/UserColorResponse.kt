package com.tellingus.tellingme.data.model.user

data class UserColorResponse(
    val code: Int,
    val data: List<UserColorData>,
    val message: String
)

data class UserColorData(
    val colorCode: String,
    val colorName: String,
    val colorHexCode: String,
)
