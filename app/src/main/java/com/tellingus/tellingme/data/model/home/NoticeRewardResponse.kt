package com.tellingus.tellingme.data.model.home

data class NoticeRewardResponse(
    val code: Int,
    val message: String,
    val data: List<NoticeReward>
)

data class NoticeReward(
    val noticeId: Int,
    val title: String,
    val content: String,
    val isRead: Boolean,
    val createdAt: List<Int>,
    val link: String,
    val isInternal: Boolean,
    val answerId: Int,
    val date: String,
    val rewardType: String
)
