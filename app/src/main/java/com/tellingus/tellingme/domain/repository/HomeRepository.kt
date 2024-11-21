package com.tellingus.tellingme.domain.repository

import com.google.android.gms.common.api.Api
import com.tellingus.tellingme.data.model.common.BasicResponse
import com.tellingus.tellingme.data.model.home.AnswerRequest
import com.tellingus.tellingme.data.model.home.HomeRequest
import com.tellingus.tellingme.data.model.home.HomeResponse
import com.tellingus.tellingme.data.model.home.NoticeResponse
import com.tellingus.tellingme.data.model.home.QuestionResponse
import com.tellingus.tellingme.data.model.home.TodayQuestion
import com.tellingus.tellingme.data.network.adapter.ApiResult

interface HomeRepository {
    //    suspend fun loadTodayQuestion() : ApiResult<TodayQuestion>
    suspend fun getNotice(): ApiResult<NoticeResponse>
    suspend fun getQuestion(today: String): ApiResult<QuestionResponse>
    suspend fun getMain(req: HomeRequest): ApiResult<HomeResponse>
    suspend fun writeAnswer(answer: AnswerRequest): ApiResult<BasicResponse>
}