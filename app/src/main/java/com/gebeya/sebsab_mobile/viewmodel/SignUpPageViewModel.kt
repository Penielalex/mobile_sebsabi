package com.gebeya.sebsab_mobile.viewmodel

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.gebeya.sebsab_mobile.data.network.entity.Worker
import com.gebeya.sebsab_mobile.domain.repository.PreferencesRepository
import com.gebeya.sebsab_mobile.domain.repository.Response
import com.gebeya.sebsab_mobile.domain.repository.WorkerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.Instant
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class SignUpPageViewModel @Inject constructor(
 val workerRepository: WorkerRepository,
    val preferencesRepository: PreferencesRepository

):ViewModel() {

    val nameError = mutableStateOf("")
    val emailError = mutableStateOf("")
    val passwordError = mutableStateOf("")
    val confirmPasswordError = mutableStateOf("")
    val qualificationError = mutableStateOf("")
    val dateError = mutableStateOf("")
    val isloading= mutableStateOf(false)

    private val _result: MutableStateFlow<String?> = MutableStateFlow(null)
    val result:  StateFlow<String?> get() = _result






    @RequiresApi(Build.VERSION_CODES.O)
    fun validateInput(
        name: String,
        email: String,
        password: String,
        confirmPassword: String,
        qualification:String,
        date: Instant
    ){
        if(name.isNullOrEmpty() || name.isNullOrBlank()){
            nameError.value = "Full Name is required"
        }else{
            nameError.value = ""
        }

        if(email.isNullOrEmpty() || email.isNullOrBlank()){
            emailError.value = "Email is required"

        }else if(!email.matches(Regex("^\\S+@\\S+\\.\\S+\$"))){

            emailError.value = "Incorrect Email"
        }else{

            emailError.value = ""
        }

        if(qualification.isNullOrEmpty() || qualification.isNullOrBlank()){
            qualificationError.value = "Qualification is required"
        }else{
            qualificationError.value = ""
        }
        if(password.isNullOrEmpty() || password.isNullOrBlank()){
            passwordError.value = "Password is required"
        }else if(password.length<6){
            passwordError.value = "password must be atleast 6 characters"
        }else{
            passwordError.value =""
        }
        if(confirmPassword.isNullOrEmpty() || confirmPassword.isNullOrBlank()){
            confirmPasswordError.value = "Please confirm password"
        }else{
            if(password != confirmPassword){
                confirmPasswordError.value = "Password not the same"}else{confirmPasswordError.value = ""

            }
        }
        val currentDate = Date()
        val calendarDOB = Calendar.getInstance().apply { time = Date.from(date) }
        val calendar18YearsAgo = Calendar.getInstance().apply { add(Calendar.YEAR, -18) }

        if(calendarDOB.before(calendar18YearsAgo) || calendarDOB == calendar18YearsAgo){
            dateError.value = ""
        }else{
            dateError.value = "User Must be above the age of 18"
        }

    }

    @SuppressLint("SuspiciousIndentation")
    suspend fun registerWorker(worker: Worker): String{
            isloading.value= true
           return try {
            val response = workerRepository.registerWorker(worker)
               print("response ${response.errorMessage}")
            _result.value = when (response) {
                is Response.Fail -> null
                is Response.Success -> "User Registered"

                else -> {null}
            }
            _result.value ?: "" // Return the result or an empty string if null
        } finally {
            isloading.value = false
        }

    }
     fun check():Boolean{
        return preferencesRepository.isUserLoggedIn()
    }

    fun logOut(){
        preferencesRepository.deleteToken()
    }
}