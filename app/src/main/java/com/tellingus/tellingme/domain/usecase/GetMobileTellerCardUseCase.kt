package com.tellingus.tellingme.domain.usecase

import com.tellingus.tellingme.data.model.home.MobileTellerCardResponse
import com.tellingus.tellingme.data.network.adapter.ApiResult
import com.tellingus.tellingme.domain.repository.HomeRepository
import javax.inject.Inject

class GetMobileTellerCardUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(): ApiResult<MobileTellerCardResponse> {
        return homeRepository.getMobileTellerCard()
    }
}