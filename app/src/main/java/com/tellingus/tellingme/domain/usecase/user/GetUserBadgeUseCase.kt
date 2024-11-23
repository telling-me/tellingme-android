package com.tellingus.tellingme.domain.usecase.user

import com.tellingus.tellingme.data.model.user.UserBadgeResponse
import com.tellingus.tellingme.data.network.adapter.ApiResult
import com.tellingus.tellingme.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserBadgeUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): ApiResult<UserBadgeResponse> {
        return userRepository.getUserBadge()
    }
}