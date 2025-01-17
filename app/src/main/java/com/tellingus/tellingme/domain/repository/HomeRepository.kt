package com.tellingus.tellingme.domain.repository

import com.google.android.gms.common.api.Api
import com.tellingus.tellingme.data.model.common.BasicResponse
import com.tellingus.tellingme.data.model.home.AnswerRequest
import com.tellingus.tellingme.data.model.home.AnswerResponse
import com.tellingus.tellingme.data.model.home.DeleteAnswerRequest
import com.tellingus.tellingme.data.model.home.HomeResponse
import com.tellingus.tellingme.data.model.home.HomeRequest
import com.tellingus.tellingme.data.model.home.MobileTellerCardResponse
import com.tellingus.tellingme.data.model.home.NoticeResponse
import com.tellingus.tellingme.data.model.home.NoticeRewardResponse
import com.tellingus.tellingme.data.model.home.PatchTellerCardRequest
import com.tellingus.tellingme.data.model.home.PatchTellerCardResponse
import com.tellingus.tellingme.data.model.home.QuestionResponse
import com.tellingus.tellingme.data.model.home.TodayQuestion
import com.tellingus.tellingme.data.model.home.UpdateAnswerRequest
import com.tellingus.tellingme.data.network.adapter.ApiResult

interface HomeRepository {
    //    suspend fun loadTodayQuestion() : ApiResult<TodayQuestion>
    suspend fun getNotice(): ApiResult<NoticeResponse>
    suspend fun getQuestion(date: String): ApiResult<QuestionResponse>
    suspend fun getAnswerByDate(date: String): ApiResult<AnswerResponse>
    suspend fun writeAnswer(answer: AnswerRequest): ApiResult<BasicResponse>
    suspend fun updateAnswer(updateAnswerRequest: UpdateAnswerRequest): ApiResult<BasicResponse>
    suspend fun deleteAnswer(deleteAnswerRequest: DeleteAnswerRequest): ApiResult<BasicResponse>
    suspend fun getMain(req: HomeRequest): ApiResult<HomeResponse>

    suspend fun getMobileTellerCard(): ApiResult<MobileTellerCardResponse>

    suspend fun patchTellerCard(patchTellerCardRequest: PatchTellerCardRequest): ApiResult<PatchTellerCardResponse>
    suspend fun getNoticeReward(): ApiResult<NoticeRewardResponse>
}