package com.gebeya.sebsab_mobile.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gebeya.sebsab_mobile.domain.repository.Response
import com.gebeya.sebsab_mobile.domain.repository.WorkerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobsPageViewModel @Inject constructor(
    val workerRepository: WorkerRepository
) : ViewModel() {
    init {
        getApplied()
        getClaimed()

    }

    val formsState = MutableStateFlow<List<Map<String, Any>>>(emptyList())
    val formsApplied = MutableStateFlow<List<Map<String, Any>>>(emptyList())
    private val _selectedForm = MutableStateFlow<Map<String, Any>?>(null)
    val selectedForm: StateFlow<Map<String, Any>?> = _selectedForm
    var formProgress = mutableStateOf<Long?>(null)

    fun getClaimed() {
        viewModelScope.launch {
            try {
                val formsList = workerRepository.getClaimed()
                formsState.value = formsList
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getProgress(formId: Long){
        viewModelScope.launch{
            try{
            val result = workerRepository.getGigWorkerJobStatus(formId)
            print(result.data)
            when(result){
                is Response.Fail -> println("Network Error: ${result.errorMessage}")
                is Response.Success -> formProgress.value = result.data
            }
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getApplied() {
        viewModelScope.launch {
            try {
                val formsList = workerRepository.getApplied()
                formsApplied.value = formsList
                print("aaaaaaaaaaaa");
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getFormById(formId: Int) {
        viewModelScope.launch {
            formsState.collect { claimedForms ->
                try {
                    // Find the form with the matching ID
                    val selectedForm = claimedForms.find { form ->
                        form["id"] == formId.toDouble() // Assuming ID is a Double in your data
                    }

                    // Update the selectedForm state
                    _selectedForm.value = selectedForm
                    println("selected form: $selectedForm")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
