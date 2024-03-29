package com.gebeya.sebsab_mobile.domain.repository

sealed class Response<T>(
    val data: T? = null,
    val errorMessage: String? = null
) {
    class Success<T>(data: T): Response<T>(data = data)
    class Fail<T>(errorMessage: String): Response<T>(errorMessage = errorMessage)
}
