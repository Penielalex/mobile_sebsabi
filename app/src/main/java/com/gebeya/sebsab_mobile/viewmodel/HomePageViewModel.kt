package com.gebeya.sebsab_mobile.viewmodel

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
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
    val contentListLiveData: MutableLiveData<List<Map<String, Any>>?> = MutableLiveData()
    var form: FormModel? by mutableStateOf(null)
    val isloading= mutableStateOf(false)
    private val _result: MutableStateFlow<String?> = MutableStateFlow(null)
    val result: StateFlow<String?> get() = _result
    var balance = mutableStateOf("")


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
                else -> {}
            }
        }
    }

    fun searchForms(title: String) {
        viewModelScope.launch {
            val contentList = workerRepository.searchForms(title)

            if (contentList != null) {
                // Handle the content list
                contentListLiveData.postValue(contentList)
            } else {
                contentListLiveData.postValue(null)
            }
        }
    }

     @SuppressLint("SuspiciousIndentation")
     fun checkBalanceForGigWorker() {
         viewModelScope.launch{
        val response = workerRepository.checkBalance()
             println("ere oooo $response")
             if (response != null) {
                 balance.value = response.get("amount").asString
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