package com.tellingus.tellingme.data.model.user

data class GetNotificationResponse(
    val code:Int,
    val data: GetNotificationData,
    val message: String
)

data class GetNotificationData(
    val allowNotification:Boolean
)
