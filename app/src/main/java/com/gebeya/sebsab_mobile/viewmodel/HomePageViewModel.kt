package com.gebeya.sebsab_mobile.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gebeya.sebsab_mobile.data.network.entity.FormModel
import com.gebeya.sebsab_mobile.data.network.entity.Proposal
import com.gebeya.sebsab_mobile.domain.repository.Response
import com.gebeya.sebsab_mobile.domain.repository.WorkerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomePageViewModel @Inject constructor(
    val workerRepository: WorkerRepository
):ViewModel() {

    var formList: List<FormModel> by mutableStateOf(listOf())
    var form: FormModel? by mutableStateOf(null)
    val isloading= mutableStateOf(false)
    private val _result: MutableStateFlow<String?> = MutableStateFlow(null)
    val result: StateFlow<String?> get() = _result
    init {
       getFormsByStatus()
    }

    fun getFormsByStatus(){
        viewModelScope.launch{
            val result = workerRepository.getFormsByStatus()
            print(result.data)
            when(result){
                is Response.Fail -> println("Network Error: ${result.errorMessage}")
                is Response.Success -> formList = result.data ?: listOf()
            }
        }
    }
    fun getFormById(formId: Int): FormModel? {
        return formList.firstOrNull { it.id == formId }
    }

    suspend fun submitProposal(formId: Long, proposal: Proposal): String{
        isloading.value= true
        return try {
            val response = workerRepository.submitProposal(formId,proposal)
            _result.value = when (response) {
                is Response.Fail -> null
                is Response.Success -> "Proposal Submitted"

                else -> {null}
            }
            _result.value ?: "" // Return the result or an empty string if null
        } finally {
            isloading.value = false
        }

    }


}