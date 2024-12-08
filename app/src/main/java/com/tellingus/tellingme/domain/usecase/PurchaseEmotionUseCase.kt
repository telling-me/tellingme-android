package com.tellingus.tellingme.domain.usecase

import com.tellingus.tellingme.data.model.common.BasicResponse
import com.tellingus.tellingme.data.model.home.QuestionResponse
import com.tellingus.tellingme.data.model.oauth.signup.NicknameResponse
import com.tellingus.tellingme.data.network.adapter.ApiResult
import com.tellingus.tellingme.domain.repository.AuthRepository
import com.tellingus.tellingme.domain.repository.HomeRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PurchaseEmotionUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        code: String
    ) : ApiResult<BasicResponse> {
        return authRepository.purchaseEmotion(code)
    }
}