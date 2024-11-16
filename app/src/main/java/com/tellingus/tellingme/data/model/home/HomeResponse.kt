package com.tellingus.tellingme.data.model.home

data class HomeResponse(
    val code: Int,
    val message: String,
    val data: HomeData
)

data class HomeData(
    val recordCount: Int,
    val todayAnswerCount: Int,
    val communicationList: List<CommunicationData>,
    val unreadNoticeStatus: Boolean,
    val questionTitle: String,
    val questionPhrase: String,
    val userNickname: String,
    val userLevel: Int,
    val userExp: Int,
    )

data class CommunicationData(
    val answerId: Int,
    val content: String,
    val createdTime: String,
    val userId: String,
    val likeCount: Int,
    val job: Int,
    val purpose: String,
    val weight: Int,
    val emotion: Int,
    val isLiked: Boolean
)