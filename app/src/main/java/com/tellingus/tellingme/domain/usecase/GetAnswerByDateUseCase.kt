package com.tellingus.tellingme.domain.usecase

import com.tellingus.tellingme.data.model.home.AnswerResponse
import com.tellingus.tellingme.data.model.myspace.AnswerListResponse
import com.tellingus.tellingme.data.network.adapter.ApiResult
import com.tellingus.tellingme.domain.repository.HomeRepository
import com.tellingus.tellingme.domain.repository.MySpaceRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAnswerByDateUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(
        date: String
    ) : ApiResult<AnswerResponse> {
        return homeRepository.getAnswerByDate(date)
    }
}