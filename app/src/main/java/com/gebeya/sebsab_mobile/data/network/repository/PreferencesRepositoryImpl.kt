package com.gebeya.sebsab_mobile.data.network.repository

import android.app.Application
import android.preference.PreferenceManager
import com.gebeya.sebsab_mobile.data.network.entity.Token
import com.gebeya.sebsab_mobile.domain.repository.PreferencesRepository
import com.google.gson.Gson

class PreferencesRepositoryImpl(
    application: Application
):PreferencesRepository
{
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(application)
    val gson = Gson()

    override fun saveAuthenticationToken(token: Token) {
        val tokenJson = gson.toJson(token)
        println("Login result json: $tokenJson")
        sharedPreferences.edit().putString("authentication_token",tokenJson).apply()
    }

    override fun getAuthenticationToken(): Token? {
        val tokenJson = sharedPreferences.getString("authentication_token", null)
        if(tokenJson != null){
            return gson.fromJson(tokenJson, Token::class.java)
        }else{
            return null
        }
    }

    override fun deleteToken() {
        val editor = sharedPreferences.edit()
        editor.remove("authentication_token")
        editor.apply()
    }

    override fun isUserLoggedIn(): Boolean {
        val token: Token? = getAuthenticationToken()
        return token != null
    }

}