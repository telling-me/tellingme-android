package com.tellingus.tellingme.data.model.otherspace

data class CommunicationResponse(
    val code: Int,
    val data: List<CommunicationData>,
    val message: String
)

data class CommunicationData(
    val title: String,
    val phrase: String,
    val date: List<Int> = emptyList(),
    val answerCount: Int,
)