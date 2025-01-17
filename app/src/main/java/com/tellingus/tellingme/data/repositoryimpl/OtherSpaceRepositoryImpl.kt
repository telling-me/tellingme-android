package com.tellingus.tellingme.data.repositoryimpl

import com.tellingus.tellingme.data.model.otherspace.CommunicationListRequest
import com.tellingus.tellingme.data.model.otherspace.CommunicationListResponse
import com.tellingus.tellingme.data.model.otherspace.CommunicationRequest
import com.tellingus.tellingme.data.model.otherspace.CommunicationResponse
import com.tellingus.tellingme.data.model.otherspace.GetAnswerByIdResponse
import com.tellingus.tellingme.data.model.otherspace.PostLikesRequest
import com.tellingus.tellingme.data.model.otherspace.PostLikesResponse
import com.tellingus.tellingme.data.model.otherspace.PostReportRequest
import com.tellingus.tellingme.data.network.NetworkService
import com.tellingus.tellingme.data.network.adapter.ApiResult
import com.tellingus.tellingme.domain.repository.OtherSpaceRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OtherSpaceRepositoryImpl @Inject constructor(
    private val service: NetworkService
) : OtherSpaceRepository {
    override suspend fun getCommunication(date: String): ApiResult<CommunicationResponse> {
        return service.getCommunication(date)
    }

    override suspend fun getCommunicationList(
        date: String,
        page: Int,
        size: Int,
        sort: String,
    ): ApiResult<CommunicationListResponse> {
        return service.getCommunicationList(date, page, size, sort)
    }

    override suspend fun postLikes(postLikesRequest: PostLikesRequest): ApiResult<PostLikesResponse> {
        return service.postLikes(postLikesRequest)
    }

    override suspend fun getAnswerById(answerId: Int): ApiResult<GetAnswerByIdResponse> {
        return service.getAnswerById(answerId)
    }

    override suspend fun postReport(postReportRequest: PostReportRequest): ApiResult<Unit> {
        return service.postReport(postReportRequest)
    }


}