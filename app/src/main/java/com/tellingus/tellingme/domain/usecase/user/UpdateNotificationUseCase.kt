package com.tellingus.tellingme.domain.usecase.user

import com.tellingus.tellingme.data.model.user.UpdateNotificationRequest
import com.tellingus.tellingme.data.model.user.UpdateNotificationResponse
import com.tellingus.tellingme.data.network.adapter.ApiResult
import com.tellingus.tellingme.domain.repository.UserRepository
import javax.inject.Inject

class UpdateNotificationUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(updateNotificationRequest: UpdateNotificationRequest): ApiResult<UpdateNotificationResponse> {
        return userRepository.updateNotification(updateNotificationRequest)
    }
}