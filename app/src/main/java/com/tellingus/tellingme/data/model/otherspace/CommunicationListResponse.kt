package com.tellingus.tellingme.data.model.otherspace

data class CommunicationListResponse(
    val code: Int,
    val data: CommunicationListData,
    val message: String
)

data class CommunicationListData(
    val content: List<CommunicationItem>, // content 리스트
    val pageable: Pageable,
    val totalPages: Int,
    val totalElements: Int,
    val last: Boolean,
    val size: Int,
    val number: Int,
    val sort: Sort,
    val numberOfElements: Int,
    val first: Boolean,
    val empty: Boolean
)

data class CommunicationItem(
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

data class Pageable(
    val sort: Sort,
    val offset: Long,
    val pageNumber: Int,
    val pageSize: Int,
    val paged: Boolean,
    val unpaged: Boolean
)

data class Sort(
    val empty: Boolean,
    val sorted: Boolean,
    val unsorted: Boolean
)