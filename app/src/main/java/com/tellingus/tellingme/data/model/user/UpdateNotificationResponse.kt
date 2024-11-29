package com.tellingus.tellingme.data.model.user

data class UpdateNotificationResponse(
    val code: Int,
    val data: UpdateNotificationData,
    val message: String
)

data class UpdateNotificationData(
    val allowNotification: Boolean
)
