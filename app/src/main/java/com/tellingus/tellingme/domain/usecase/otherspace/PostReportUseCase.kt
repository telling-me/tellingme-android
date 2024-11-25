package com.tellingus.tellingme.domain.usecase.otherspace

import com.tellingus.tellingme.data.model.otherspace.PostReportRequest
import com.tellingus.tellingme.data.network.adapter.ApiResult
import com.tellingus.tellingme.domain.repository.OtherSpaceRepository
import javax.inject.Inject

class PostReportUseCase @Inject constructor(
    private val otherSpaceRepository: OtherSpaceRepository
) {
    suspend operator fun invoke(postReportRequest: PostReportRequest): ApiResult<Unit> {
        return otherSpaceRepository.postReport((postReportRequest))
    }
}