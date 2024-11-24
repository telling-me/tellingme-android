package com.tellingus.tellingme.domain.usecase.otherspace

import com.tellingus.tellingme.data.model.otherspace.PostLikesRequest
import com.tellingus.tellingme.data.model.otherspace.PostLikesResponse
import com.tellingus.tellingme.data.network.adapter.ApiResult
import com.tellingus.tellingme.domain.repository.OtherSpaceRepository
import javax.inject.Inject

class PostLikesUseCase @Inject constructor(
    private val otherSpaceRepository: OtherSpaceRepository
) {
    suspend operator fun invoke(postLikesRequest: PostLikesRequest): ApiResult<PostLikesResponse> {
        return otherSpaceRepository.postLikes(postLikesRequest)
    }
}