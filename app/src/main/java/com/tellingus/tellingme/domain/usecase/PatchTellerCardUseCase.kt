package com.tellingus.tellingme.domain.usecase

import com.tellingus.tellingme.data.model.home.PatchTellerCardRequest
import com.tellingus.tellingme.data.model.home.PatchTellerCardResponse
import com.tellingus.tellingme.data.network.adapter.ApiResult
import com.tellingus.tellingme.domain.repository.HomeRepository
import javax.inject.Inject

class PatchTellerCardUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(
        patchTellerCardRequest: PatchTellerCardRequest
    ): ApiResult<PatchTellerCardResponse> {
        return homeRepository.patchTellerCard(patchTellerCardRequest)
    }
}