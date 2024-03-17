package com.gebeya.sebsab_mobile.data.network.repository

import com.gebeya.sebsab_mobile.data.network.api.WorkerApi
import com.gebeya.sebsab_mobile.data.network.entity.AuthRequest
import com.gebeya.sebsab_mobile.data.network.entity.FormModel
import com.gebeya.sebsab_mobile.data.network.entity.Proposal
import com.gebeya.sebsab_mobile.data.network.entity.Token
import com.gebeya.sebsab_mobile.data.network.entity.UserResponse
import com.gebeya.sebsab_mobile.data.network.entity.Worker
import com.gebeya.sebsab_mobile.data.network.entity.WorkerUpdate
import com.gebeya.sebsab_mobile.domain.repository.PreferencesRepository
import com.gebeya.sebsab_mobile.domain.repository.Response
import com.gebeya.sebsab_mobile.domain.repository.WorkerRepository
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

class WokerRepositoryImpl(
    val workerApi: WorkerApi,
    val preferencesRepository: PreferencesRepository
): WorkerRepository {
    override suspend fun registerWorker(worker: Worker): Response<String?> {
        try {
            val response = workerApi.registerWorker(worker)
            if (response.isSuccessful) {
                // Check if the response is successful before extracting the body
                val responseBodyString = response.body()?.string()
                return Response.Success(data = responseBodyString)
            } else {
                return Response.Fail(errorMessage = "Unsuccessful response: ${response.code()}")
            }
        } catch (e: IOException) {
            return Response.Fail(errorMessage = e.message ?: "")
        } catch (e: HttpException) {
            return Response.Fail(errorMessage = e.message ?: "")
        } catch (t: Throwable) {
            return Response.Fail(errorMessage = t.message ?: "")
        } catch (e: SocketTimeoutException) {
            return Response.Fail(errorMessage = e.message ?: "")
        }
    }

    override suspend fun getGigWorkerJobStatus(formId: Long): Response<Long> {
        try {
            preferencesRepository.getAuthenticationToken()?.let {
                val progress = workerApi.getGigWorkerJobStatus(formId, "Bearer ${it.token}")
                return Response.Success(progress)
            }
            return Response.Fail("You need to login")
        } catch (e: IOException) {
            return Response.Fail(errorMessage = e.message ?: "no network")
        } catch (e: HttpException) {
            return Response.Fail(errorMessage = e.message ?: "server error")
        } catch (e: Throwable) {
            return Response.Fail(errorMessage = e.message ?: "unknown error")
        }

    }

    override suspend fun login(authRequest: AuthRequest): Response<Token> {
        try {
            val authenticationToken = workerApi.getToken(authRequest)
            preferencesRepository.saveAuthenticationToken(authenticationToken)
            return Response.Success(data = authenticationToken)
        } catch (e: IOException) {
            return Response.Fail(errorMessage = e.message ?: "no network")
        } catch (e: HttpException) {
            return Response.Fail(errorMessage = e.message ?: "server error")
        } catch (e: Throwable) {
            return Response.Fail(errorMessage = e.message ?: "unknown error")
        }
    }

    override suspend fun getFormsByStatus(): Response<List<FormModel>> {
        try {
            preferencesRepository.getAuthenticationToken()?.let {
                val forms = workerApi.getFormsByStatus( "Bearer ${it.token}")
                return Response.Success(forms)
            }
            return Response.Fail("You need to login")
        } catch (e: IOException) {
            return Response.Fail(errorMessage = e.message ?: "no network")
        } catch (e: HttpException) {
            return Response.Fail(errorMessage = e.message ?: "server error")
        } catch (e: Throwable) {
            return Response.Fail(errorMessage = e.message ?: "unknown error")
        }
    }

    override suspend fun submitProposal(formId:Long, proposal: Proposal): Response<String> {
        try {
            preferencesRepository.getAuthenticationToken()?.let {
                val response = workerApi.submitProposal(formId,proposal,"Bearer ${it.token}")
                val responseBodyString = response.body().toString()
                return Response.Success(data = responseBodyString)
            }?: return Response.Fail(errorMessage = "Unsuccessful response")
        } catch (e: IOException) {
            return Response.Fail(errorMessage = e.message ?: "")
        } catch (e: HttpException) {
            return Response.Fail(errorMessage = e.message ?: "")
        } catch (t: Throwable) {
            return Response.Fail(errorMessage = t.message ?: "")
        } catch (e: SocketTimeoutException) {
            return Response.Fail(errorMessage = e.message ?: "")
        }
    }

    override suspend fun getProfile(): Response<Worker> {
        try {
            preferencesRepository.getAuthenticationToken()?.let {
                val user = workerApi.getGigWorkerById("Bearer ${it.token}")
                return Response.Success(user)
            }
            return Response.Fail("You need to login")
        } catch (e: IOException) {
            return Response.Fail(errorMessage = e.message ?: "no network")
        } catch (e: HttpException) {
            return Response.Fail(errorMessage = e.message ?: "server error")
        } catch (e: Throwable) {
            return Response.Fail(errorMessage = e.message ?: "unknown error")
        }
    }

    override suspend fun getClaimed(): List<Map<String, Any>> {
        try {
            preferencesRepository.getAuthenticationToken()?.let {
                val forms = workerApi.getAllFormByClaimed("Bearer ${it.token}")

                return forms
            }
            return emptyList()
        } catch (e: IOException) {

            return emptyList()
        } catch (e: HttpException) {
            return emptyList()
        } catch (e: Throwable) {
            return emptyList()
        }
    }
    override suspend fun getCompleted(): List<Map<String, Any>> {
        try {
            preferencesRepository.getAuthenticationToken()?.let {
                val forms = workerApi.getAllFormByCompleted("Bearer ${it.token}")

                return forms
            }
            return emptyList()
        } catch (e: IOException) {

            return emptyList()
        } catch (e: HttpException) {
            return emptyList()
        } catch (e: Throwable) {
            return emptyList()
        }
    }

    override suspend fun getApplied(): List<Map<String, Any>> {
        try {
            preferencesRepository.getAuthenticationToken()?.let {
                val forms = workerApi.getAllFormsByApplied("Bearer ${it.token}")

                return forms
            }
            return emptyList()
        } catch (e: IOException) {

            return emptyList()
        } catch (e: HttpException) {
            return emptyList()
        } catch (e: Throwable) {
            return emptyList()
        }
    }

    override suspend fun submitAnswer(userResponse: UserResponse): Response<Map<String, Any>> {
        try {
            preferencesRepository.getAuthenticationToken()?.let {
                val answer= workerApi.submitResponse(userResponse,"Bearer ${it.token}")
                return Response.Success(answer)
            }
            return Response.Fail("You need to login")
        } catch (e: IOException) {
            return Response.Fail(errorMessage = e.message ?: "no network")
        } catch (e: HttpException) {
            return Response.Fail(errorMessage = e.message ?: "server error")
        } catch (e: Throwable) {
            return Response.Fail(errorMessage = e.message ?: "unknown error")
        }
    }

    override suspend fun UpdateProfile(workerUpdate: WorkerUpdate): Response<WorkerUpdate> {
        try {
            preferencesRepository.getAuthenticationToken()?.let {
                val user = workerApi.updateGigworker(workerUpdate,"Bearer ${it.token}")
                return Response.Success(user)
            }
            return Response.Fail("You need to login")
        } catch (e: IOException) {
            return Response.Fail(errorMessage = e.message ?: "no network")
        } catch (e: HttpException) {
            return Response.Fail(errorMessage = e.message ?: "server error")
        } catch (e: Throwable) {
            return Response.Fail(errorMessage = e.message ?: "unknown error")
        }
    }

    override suspend fun searchForms(title: String):  List<Map<String, Any>>?{

        try {
            preferencesRepository.getAuthenticationToken()?.let {
                val response = workerApi.searchForms(title,10,"Bearer ${it.token}")
                if (response.isSuccessful) {
                    val body = response.body()
                    val contentList = body?.getAsJsonArray("content")
                    return contentList?.map { contentItem ->
                        val id = contentItem.asJsonObject.get("id").asInt
                        val title = contentItem.asJsonObject.get("title").asString
                        val description = contentItem.asJsonObject.get("description").asString
                        val usageLimit = contentItem.asJsonObject.get("usageLimit").asInt

                        mapOf(
                            "id" to id,
                            "title" to title,
                            "description" to description,
                            "usageLimit" to usageLimit
                        )
                    }}else {
                    // Handle API call failure
                    return null
                }
            }
            return null
        } catch (e: IOException) {
            return null
        }

    }


}