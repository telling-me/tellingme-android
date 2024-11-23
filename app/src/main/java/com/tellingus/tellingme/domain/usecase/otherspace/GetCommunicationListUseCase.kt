package com.tellingus.tellingme.domain.usecase.otherspace

import com.tellingus.tellingme.data.model.otherspace.CommunicationListRequest
import com.tellingus.tellingme.data.model.otherspace.CommunicationListResponse
import com.tellingus.tellingme.data.network.adapter.ApiResult
import com.tellingus.tellingme.domain.repository.OtherSpaceRepository
import javax.inject.Inject

class GetCommunicationListUseCase @Inject constructor(
    private val otherSpaceRepository: OtherSpaceRepository
) {
    suspend operator fun invoke(
        date: String,
        page: Int,
        size: Int,
        sort: String
    ): ApiResult<CommunicationListResponse> {
        return otherSpaceRepository.getCommunicationList(
            date,
            page,
            size,
            sort,
            )
    }
}