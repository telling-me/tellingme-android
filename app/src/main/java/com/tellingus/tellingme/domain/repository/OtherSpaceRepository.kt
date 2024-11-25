package com.tellingus.tellingme.domain.repository

import com.tellingus.tellingme.data.model.otherspace.CommunicationListRequest
import com.tellingus.tellingme.data.model.otherspace.CommunicationListResponse
import com.tellingus.tellingme.data.model.otherspace.CommunicationRequest
import com.tellingus.tellingme.data.model.otherspace.CommunicationResponse
import com.tellingus.tellingme.data.model.otherspace.GetAnswerByIdResponse
import com.tellingus.tellingme.data.model.otherspace.PostLikesRequest
import com.tellingus.tellingme.data.model.otherspace.PostLikesResponse
import com.tellingus.tellingme.data.model.otherspace.PostReportRequest
import com.tellingus.tellingme.data.network.adapter.ApiResult

interface OtherSpaceRepository {
    suspend fun getCommunication(date: String): ApiResult<CommunicationResponse>

    suspend fun getCommunicationList(
        date: String,
        page: Int,
        size: Int,
        sort: String
    ): ApiResult<CommunicationListResponse>

    suspend fun postLikes(postLikesRequest: PostLikesRequest) : ApiResult<PostLikesResponse>

    suspend fun getAnswerById(answerId: Int) : ApiResult<GetAnswerByIdResponse>

    suspend fun postReport(postReportRequest: PostReportRequest): ApiResult<Unit>
}