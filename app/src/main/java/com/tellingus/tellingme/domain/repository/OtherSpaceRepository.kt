package com.tellingus.tellingme.domain.repository

import com.tellingus.tellingme.data.model.otherspace.CommunicationListRequest
import com.tellingus.tellingme.data.model.otherspace.CommunicationListResponse
import com.tellingus.tellingme.data.model.otherspace.CommunicationRequest
import com.tellingus.tellingme.data.model.otherspace.CommunicationResponse
import com.tellingus.tellingme.data.network.adapter.ApiResult

interface OtherSpaceRepository {
    suspend fun getCommunication(date: String): ApiResult<CommunicationResponse>

    suspend fun getCommunicationList(communicationListRequest: CommunicationListRequest): ApiResult<CommunicationListResponse>
}