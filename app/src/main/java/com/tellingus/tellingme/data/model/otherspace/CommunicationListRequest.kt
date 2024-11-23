package com.tellingus.tellingme.data.model.otherspace

data class CommunicationListRequest(
    val date: String,
    val page: Int,
    val size: Int,
    val sort: Int
)
