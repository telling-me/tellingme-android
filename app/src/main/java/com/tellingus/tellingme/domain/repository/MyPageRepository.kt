package com.tellingus.tellingme.domain.repository

import com.tellingus.tellingme.data.model.myspace.AnswerListResponse
import com.tellingus.tellingme.data.model.myspace.MyPageResponse
import com.tellingus.tellingme.data.network.adapter.ApiResult

interface MyPageRepository {
    suspend fun getMyPage(): ApiResult<MyPageResponse>

    suspend fun getAnswerList():ApiResult<AnswerListResponse>

}