package com.tellingus.tellingme.domain.usecase.user

import com.tellingus.tellingme.data.model.user.GetCheeseResponse
import com.tellingus.tellingme.data.network.adapter.ApiResult
import com.tellingus.tellingme.domain.repository.UserRepository
import javax.inject.Inject

class GetCheeseUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): ApiResult<GetCheeseResponse> {
        return userRepository.getCheese()
    }
}