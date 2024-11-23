package com.tellingus.tellingme.data.repositoryimpl

import com.tellingus.tellingme.data.model.common.BasicResponse
import com.tellingus.tellingme.data.model.home.AnswerRequest
import com.tellingus.tellingme.data.model.home.HomeRequest
import com.tellingus.tellingme.data.model.home.HomeResponse
import com.tellingus.tellingme.data.model.home.NoticeResponse
import com.tellingus.tellingme.data.model.home.QuestionRequest
import com.tellingus.tellingme.data.model.home.QuestionResponse
import com.tellingus.tellingme.data.network.NetworkService
import com.tellingus.tellingme.data.network.adapter.ApiResult
import com.tellingus.tellingme.domain.repository.HomeRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepositoryImpl @Inject constructor(
    private val networkService: NetworkService
) : HomeRepository {
    override suspend fun getNotice(): ApiResult<NoticeResponse> {
        return networkService.getNotice()


    }

    override suspend fun getQuestion(today: String): ApiResult<QuestionResponse> {
        return networkService.getQuestion(date = today)


    }

    override suspend fun getMain(req: HomeRequest): ApiResult<HomeResponse> {
        return networkService.getMain(req)

    }

    override suspend fun writeAnswer(answer: AnswerRequest): ApiResult<BasicResponse> {
        TODO("Not yet implemented")
    }


}