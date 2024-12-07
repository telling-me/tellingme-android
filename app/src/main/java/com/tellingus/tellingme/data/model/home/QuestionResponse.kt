package com.tellingus.tellingme.data.model.home

data class QuestionResponse(
    val code: Int,
    val data: QuestionData,
    val message: String
)

data class QuestionData(
    val date: List<Int> = emptyList(),
    val title: String = "",
    val phrase: String = "",
    val spareTitle: String = "",
    val sparePhrase: String = "",
    val userQuestionType: String = ""
    val code: Int,
    val message: String,
    val data: Question
)

data class Question(
    val date: List<Int> = emptyList(),
    val title: String = "",
    val phrase: String = "",
    val spareTitle: String = "",
    val sparePhrase: String = "",
    val userQuestionType: String = ""
)