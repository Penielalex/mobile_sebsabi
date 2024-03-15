package com.gebeya.sebsab_mobile.data.network.entity

data class WorkerUpdate(

    val firstName: String?,
    val lastName: String?,
    val email: String?,
    val qualification: String?,
    val dob: String?,
    val password: String?,
    val age: Int?,
    val isActive: Status?
)
