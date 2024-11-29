package com.tellingus.tellingme.domain.repository

import com.tellingus.tellingme.data.model.user.GetCheeseResponse
import com.tellingus.tellingme.data.model.user.GetNotificationResponse
import com.tellingus.tellingme.data.model.user.UpdateNotificationRequest
import com.tellingus.tellingme.data.model.user.UpdateNotificationResponse
import com.tellingus.tellingme.data.model.user.UserBadgeResponse
import com.tellingus.tellingme.data.network.adapter.ApiResult

interface UserRepository {
    suspend fun getUserBadge(): ApiResult<UserBadgeResponse>

    suspend fun getCheese(): ApiResult<GetCheeseResponse>

    suspend fun getNotification(): ApiResult<GetNotificationResponse>

    suspend fun updateNotification(updateNotificationRequest: UpdateNotificationRequest): ApiResult<UpdateNotificationResponse>
}