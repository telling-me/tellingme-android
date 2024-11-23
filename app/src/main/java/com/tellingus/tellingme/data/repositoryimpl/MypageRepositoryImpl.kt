package com.tellingus.tellingme.data.repositoryimpl

import com.tellingus.tellingme.data.model.myspace.MyPageResponse
import com.tellingus.tellingme.data.network.NetworkService
import com.tellingus.tellingme.data.network.adapter.ApiResult
import com.tellingus.tellingme.domain.repository.MyPageRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyPageRepositoryImpl @Inject constructor(
    private val networkService: NetworkService
) : MyPageRepository {


    override suspend fun getMyPage(): ApiResult<MyPageResponse> {
        return networkService.getMyPage()
    }

}