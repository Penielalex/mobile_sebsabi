package com.gebeya.sebsab_mobile.domain.repository

import com.gebeya.sebsab_mobile.data.network.entity.Token

interface PreferencesRepository {

    fun saveAuthenticationToken(token: Token)
    fun getAuthenticationToken(): Token?

    fun deleteToken()

     fun isUserLoggedIn(): Boolean
}