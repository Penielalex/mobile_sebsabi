package com.gebeya.sebsab_mobile.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gebeya.sebsab_mobile.data.network.entity.Worker
import com.gebeya.sebsab_mobile.data.network.entity.WorkerUpdate
import com.gebeya.sebsab_mobile.domain.repository.Response
import com.gebeya.sebsab_mobile.domain.repository.WorkerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfilePageViewModel @Inject constructor(
    val workerRepository: WorkerRepository,
): ViewModel() {
    private val _worker =  MutableStateFlow<Worker?>(null)
    val worker: StateFlow<Worker?> = _worker.asStateFlow()
    val isloading= mutableStateOf(false)

    private val _result: MutableStateFlow<String?> = MutableStateFlow(null)
    val result:  StateFlow<String?> get() = _result

    init {
        viewModelScope.launch {
            try {
                getProfile()
            } catch (e: Exception) {
                // Handle exception
                println("Exception: ${e.message}")
            }
        }
    }

    private suspend fun getProfile() {
        val result = workerRepository.getProfile()
        when (result) {
            is Response.Fail -> println("Network Error: ${result.errorMessage}")
            is Response.Success -> _worker.value = result.data
        }
        println(worker.value)
    }

    suspend fun updateProfile(workerUpdate:WorkerUpdate):String{
        isloading.value= true
        return try{
            val response= workerRepository.UpdateProfile(workerUpdate)
            print("response ${response.errorMessage}")
            _result.value = when (response) {
                is Response.Fail -> null
                is Response.Success -> "User Updated"

                else -> {null}
            }
            _result.value ?: "" // Return the result or an empty string if null
        } finally {
            isloading.value = false
        }


    }
}

