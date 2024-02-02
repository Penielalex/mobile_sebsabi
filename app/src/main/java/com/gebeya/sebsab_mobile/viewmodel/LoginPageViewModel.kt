package com.gebeya.sebsab_mobile.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginPageViewModel @Inject constructor(

):ViewModel(){

    val emailError = mutableStateOf("")
    val passwordError = mutableStateOf("")


    fun validateInput(

        email: String,
        password: String,

    ){

        if(email.isNullOrEmpty() || email.isNullOrBlank()){
            emailError.value = "Email is required"
        }else{
            emailError.value = ""
        }
        if(password.isNullOrEmpty() || password.isNullOrBlank()){
            passwordError.value = "Password is required"
        }else{
            passwordError.value = ""
        }


    }
}