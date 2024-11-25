package com.tellingus.tellingme.data.model.otherspace

data class PostReportRequest(
    val answerId: Int,
    val reason : Int, // 0 ~ 6
)
