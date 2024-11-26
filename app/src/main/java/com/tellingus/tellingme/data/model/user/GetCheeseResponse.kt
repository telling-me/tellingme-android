package com.tellingus.tellingme.data.model.user

data class GetCheeseResponse(
    val code: Int,
    val data: GetCheeseResponseData,
    val message: String,
)

data class GetCheeseResponseData(
    val cheeseBalance: Int
)
