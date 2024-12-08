package com.tellingus.tellingme.domain.usecase

import com.tellingus.tellingme.data.model.myspace.AnswerListResponse
import com.tellingus.tellingme.data.model.user.UsableEmotion
import com.tellingus.tellingme.data.model.user.UsableEmotionResponse
import com.tellingus.tellingme.data.network.adapter.ApiResult
import com.tellingus.tellingme.domain.repository.AuthRepository
import com.tellingus.tellingme.domain.repository.MySpaceRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUsableEmotionUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() : ApiResult<UsableEmotionResponse> {
        return authRepository.getUsableEmotion()
    }
}