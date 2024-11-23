package com.tellingus.tellingme.domain.usecase.mypage

import com.tellingus.tellingme.data.model.myspace.MyPageResponse
import com.tellingus.tellingme.data.network.adapter.ApiResult
import com.tellingus.tellingme.domain.repository.MyPageRepository
import javax.inject.Inject

class GetMyPageUseCase @Inject constructor(
    private val myPageRepository: MyPageRepository
) {
    suspend operator fun invoke(): ApiResult<MyPageResponse> {
        return myPageRepository.getMyPage()
    }


}