package com.gebeya.sebsab_mobile.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpPageViewModel @Inject constructor(

):ViewModel() {

    val nameError = mutableStateOf("")
    val emailError = mutableStateOf("")
    val passwordError = mutableStateOf("")
    val confirmPasswordError = mutableStateOf("")

    fun validateInput(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ){
        if(name.isNullOrEmpty() || name.isNullOrBlank()){
            nameError.value = "Full Name is required"
        }else{
            nameError.value = ""
        }
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
        if(confirmPassword.isNullOrEmpty() || confirmPassword.isNullOrBlank()){
            confirmPasswordError.value = "Please confirm password"
        }else{
            if(password != confirmPassword){
                confirmPasswordError.value = "Password not the same"}else{confirmPasswordError.value = ""

            }
        }

    }
}