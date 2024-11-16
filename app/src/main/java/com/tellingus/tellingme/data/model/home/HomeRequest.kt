package com.tellingus.tellingme.data.model.home

data class HomeRequest (
    val date: String,
    val page: Int,
    val size: Int,
    val sort: String,
)
