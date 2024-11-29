package com.tellingus.tellingme.domain.usecase.user

import com.tellingus.tellingme.data.model.user.GetNotificationResponse
import com.tellingus.tellingme.data.network.adapter.ApiResult
import com.tellingus.tellingme.domain.repository.UserRepository
import javax.inject.Inject


class GetNotificationUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(): ApiResult<GetNotificationResponse> {
        return userRepository.getNotification()
    }
}