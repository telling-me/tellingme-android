package com.tellingus.tellingme.domain.usecase

import com.tellingus.tellingme.data.model.common.BasicResponse
import com.tellingus.tellingme.data.model.oauth.login.OauthRequest
import com.tellingus.tellingme.data.model.oauth.login.TokenResponse
import com.tellingus.tellingme.data.network.adapter.ApiResult
import com.tellingus.tellingme.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() : ApiResult<BasicResponse> {
        return authRepository.logout()
    }
}