package com.tellingus.tellingme.domain.usecase.otherspace

import com.tellingus.tellingme.data.model.otherspace.GetAnswerByIdResponse
import com.tellingus.tellingme.data.network.adapter.ApiResult
import com.tellingus.tellingme.domain.repository.OtherSpaceRepository
import javax.inject.Inject

class GetAnswerByIdUseCase @Inject constructor(
    private val otherSpaceRepository: OtherSpaceRepository
) {
    suspend operator fun invoke(answerId: Int): ApiResult<GetAnswerByIdResponse> {
        return otherSpaceRepository.getAnswerById(answerId)
    }
}