package com.gebeya.sebsab_mobile.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.gebeya.sebsab_mobile.data.network.entity.UserResponse
import com.gebeya.sebsab_mobile.domain.repository.Response
import com.gebeya.sebsab_mobile.domain.repository.WorkerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AnswerPageViewModel @Inject constructor(
    val workerRepository: WorkerRepository,
): ViewModel() {

    private val _result: MutableStateFlow<String?> = MutableStateFlow(null)
    val result: StateFlow<String?> get() = _result
    val isloading= mutableStateOf(false)


    suspend fun sumbitAnswer(userResponse: UserResponse): String{
        isloading.value= true
        return try{
            val response = workerRepository.submitAnswer(userResponse)
            print("response ${response.errorMessage}")
            _result.value = when (response) {
                is Response.Fail -> null
                is Response.Success -> "Answer Submitted"

                else -> {null}
            }
            _result.value ?: ""
        }finally {
            isloading.value=false
        }
    }
}