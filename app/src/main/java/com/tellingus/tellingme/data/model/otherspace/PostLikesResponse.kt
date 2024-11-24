package com.tellingus.tellingme.data.model.otherspace

data class PostLikesResponse(
    val code : Int,
    val data: PostLikesData,
    val message : String,
)

data class PostLikesData(
    val likeCount : Int,
    val isLiked :Boolean,
)
