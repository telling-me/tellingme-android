package com.tellingus.tellingme.domain.usecase

import com.tellingus.tellingme.data.model.common.BasicResponse
import com.tellingus.tellingme.data.model.home.NoticeRewardResponse
import com.tellingus.tellingme.data.model.oauth.UserResponse
import com.tellingus.tellingme.data.model.oauth.login.OauthRequest
import com.tellingus.tellingme.data.model.oauth.login.TokenResponse
import com.tellingus.tellingme.data.network.adapter.ApiResult
import com.tellingus.tellingme.domain.repository.AuthRepository
import com.tellingus.tellingme.domain.repository.HomeRepository
import javax.inject.Inject

class GetNoticeRewardUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke() : ApiResult<NoticeRewardResponse> {
        return homeRepository.getNoticeReward()
    }
}