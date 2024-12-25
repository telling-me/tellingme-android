package com.tellingus.tellingme.data.model.notice

data class NoticeSummaryResponse(
    val code: Int,
    val data: NoticeSummaryResponseData,
    val message: String
)

data class NoticeSummaryResponseData(
    val status: Boolean
)