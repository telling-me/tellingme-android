package com.tellingus.tellingme.data.repositoryimpl

import com.tellingus.tellingme.data.model.user.UserBadgeResponse
import com.tellingus.tellingme.data.network.NetworkService
import com.tellingus.tellingme.data.network.adapter.ApiResult
import com.tellingus.tellingme.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val service: NetworkService
) : UserRepository {
    override suspend fun getUserBadge(): ApiResult<UserBadgeResponse> {
        return service.getUserBadge()
    }

}