package com.gebeya.sebsab_mobile.data.network.entity

data class Answer(
    val questionId: Long,
    val selectedOptions: List<Long>?,
    val answerText: String?,
    val rating: Int?,

)
