package com.tellingus.tellingme.data.model.notice

// TODO: 홈 NoticeResponse 테스트 완료 후 NoticeResponse 로 이름 수정
data class LoadNoticeResponse(
    val code: Int,
    val message: String,
    val data: List<LoadNotice>
)

data class LoadNotice(
    val noticeId: Int,
    val title: String,
    val content: String?,
    val isRead: Boolean,
    val createdAt: List<Int>,
    val link: String?,
    val isInternal: Boolean,
    val answerId: Int,
    val date: List<Int>?, // date가 null이거나 List<Int> 타입이므로 nullable 처리
    val rewardType: String? // JSON에는 있지만 LoadNotice 클래스에서 누락된 필드
)