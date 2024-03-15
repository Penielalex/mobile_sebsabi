package com.gebeya.sebsab_mobile.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.gebeya.sebsab_mobile.data.network.entity.AuthRequest
import com.gebeya.sebsab_mobile.domain.repository.Response
import com.gebeya.sebsab_mobile.domain.repository.WorkerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LoginPageViewModel @Inject constructor(
    val workerRepository: WorkerRepository

):ViewModel(){

    val emailError = mutableStateOf("")
    val passwordError = mutableStateOf("")
    val isloading= mutableStateOf(false)
    private val _result: MutableStateFlow<String?> = MutableStateFlow(null)
    val result: StateFlow<String?> get() = _result


    fun validateInput(

        email: String,
        password: String,

    ){

        if(email.isNullOrEmpty() || email.isNullOrBlank()){
            emailError.value = "Email is required"

        }else if(!email.matches(Regex("^\\S+@\\S+\\.\\S+\$"))){

            emailError.value = "Incorrect Email"
        }else{

            emailError.value = ""
        }

        if(password.isNullOrEmpty() || password.isNullOrBlank()){
            passwordError.value = "Password is required"
        }else{
            passwordError.value = ""
        }


    }

    suspend fun login(authRequest: AuthRequest): String{
        isloading.value= true
        return try {
            val result = workerRepository.login(authRequest)
            print("ere wooooo${result.errorMessage}")
            _result.value = when (result) {
                is Response.Fail -> null
                is Response.Success -> "User logged In"

                else -> {null}
            }
            _result.value ?: "" // Return the result or an empty string if null
        } finally {
            isloading.value = false
            print("ere wooooo")
        }


}}