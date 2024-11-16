package com.tellingus.tellingme.domain.usecase

import com.tellingus.tellingme.data.model.home.HomeRequest
import com.tellingus.tellingme.data.model.home.HomeResponse
import com.tellingus.tellingme.data.network.adapter.ApiResult
import com.tellingus.tellingme.domain.repository.HomeRepository
import javax.inject.Inject

class HomeUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    suspend operator fun invoke(req: HomeRequest): ApiResult<HomeResponse> {
        return homeRepository.getMain((req))
    }
}