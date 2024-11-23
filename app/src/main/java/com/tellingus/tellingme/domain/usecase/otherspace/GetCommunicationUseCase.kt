package com.tellingus.tellingme.domain.usecase.otherspace


import com.tellingus.tellingme.data.model.otherspace.CommunicationResponse
import com.tellingus.tellingme.data.network.adapter.ApiResult
import com.tellingus.tellingme.domain.repository.OtherSpaceRepository
import javax.inject.Inject

class GetCommunicationUseCase @Inject constructor(
    private val otherSpaceRepository: OtherSpaceRepository
) {
    suspend operator fun invoke(date: String): ApiResult<CommunicationResponse> {
        return otherSpaceRepository.getCommunication(date)
    }
}