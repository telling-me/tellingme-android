package com.tellingus.tellingme.domain.usecase

import com.tellingus.tellingme.data.model.common.BasicResponse
import com.tellingus.tellingme.data.model.home.DeleteAnswerRequest
import com.tellingus.tellingme.data.model.home.QuestionResponse
import com.tellingus.tellingme.data.model.oauth.signup.NicknameResponse
import com.tellingus.tellingme.data.network.adapter.ApiResult
import com.tellingus.tellingme.domain.repository.HomeRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteAnswerUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(
        date: String
    ) : ApiResult<BasicResponse> {
        return homeRepository.deleteAnswer(DeleteAnswerRequest(date))
    }
}