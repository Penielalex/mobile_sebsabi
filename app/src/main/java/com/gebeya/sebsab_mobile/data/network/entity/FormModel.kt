package com.gebeya.sebsab_mobile.data.network.entity

data class FormModel(
    val id: Int,
    val title: String,
    val description: String,
    val usageLimit: Int,
    val status: Status
)
