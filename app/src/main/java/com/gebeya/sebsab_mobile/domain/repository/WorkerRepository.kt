package com.gebeya.sebsab_mobile.domain.repository

import com.gebeya.sebsab_mobile.data.network.entity.AuthRequest
import com.gebeya.sebsab_mobile.data.network.entity.FormModel
import com.gebeya.sebsab_mobile.data.network.entity.Proposal
import com.gebeya.sebsab_mobile.data.network.entity.Token
import com.gebeya.sebsab_mobile.data.network.entity.UserResponse
import com.gebeya.sebsab_mobile.data.network.entity.Worker
import com.gebeya.sebsab_mobile.data.network.entity.WorkerUpdate
import com.google.gson.JsonObject

interface WorkerRepository {

    suspend fun registerWorker(worker:Worker): Response<String?>

    suspend fun getGigWorkerJobStatus(formId: Long): Response<Long>
    suspend fun login(authRequest: AuthRequest): Response<Token>

    suspend fun getFormsByStatus() : Response<List<FormModel>>

    suspend fun submitProposal(formId:Long,proposal: Proposal): Response<String>

    suspend fun getProfile(): Response<Worker>

    suspend fun getClaimed(): List<Map<String, Any>>
    suspend fun getApplied(): List<Map<String, Any>>
    suspend fun submitAnswer(userResponse: UserResponse): Response<Map<String, Any>>

    suspend fun UpdateProfile(workerUpdate: WorkerUpdate): Response<WorkerUpdate>

    suspend fun searchForms(title: String): List<Map<String, Any>>?
    suspend fun getCompleted(): List<Map<String, Any>>

    suspend fun checkBalance(): JsonObject?




}