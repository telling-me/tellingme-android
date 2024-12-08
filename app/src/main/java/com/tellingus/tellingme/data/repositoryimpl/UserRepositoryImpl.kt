package com.tellingus.tellingme.data.repositoryimpl

import com.tellingus.tellingme.data.model.user.GetCheeseResponse
import com.tellingus.tellingme.data.model.user.GetNotificationResponse
import com.tellingus.tellingme.data.model.user.UpdateNotificationRequest
import com.tellingus.tellingme.data.model.user.UpdateNotificationResponse
import com.tellingus.tellingme.data.model.user.UserBadgeResponse
import com.tellingus.tellingme.data.model.user.UserColorResponse
import com.tellingus.tellingme.data.network.NetworkService
import com.tellingus.tellingme.data.network.adapter.ApiResult
import com.tellingus.tellingme.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val service: NetworkService
) : UserRepository {
    override suspend fun getUserBadge(): ApiResult<UserBadgeResponse> {
        return service.getUserBadge()
    }

    override suspend fun getUserColor(): ApiResult<UserColorResponse> {
        return service.getUserColor()
    }

    override suspend fun getCheese(): ApiResult<GetCheeseResponse> {
        return service.getCheese()
    }

    override suspend fun getNotification(): ApiResult<GetNotificationResponse> {
        return service.getNotification()
    }

    override suspend fun updateNotification(updateNotificationRequest: UpdateNotificationRequest): ApiResult<UpdateNotificationResponse> {
        return service.updateNotification(updateNotificationRequest)
    }

}