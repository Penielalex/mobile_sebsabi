package com.gebeya.sebsab_mobile.data.network.api

import com.gebeya.sebsab_mobile.data.network.entity.AuthRequest
import com.gebeya.sebsab_mobile.data.network.entity.FormModel
import com.gebeya.sebsab_mobile.data.network.entity.Proposal
import com.gebeya.sebsab_mobile.data.network.entity.Token
import com.gebeya.sebsab_mobile.data.network.entity.UserResponse
import com.gebeya.sebsab_mobile.data.network.entity.Worker
import com.gebeya.sebsab_mobile.data.network.entity.WorkerUpdate
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


const val AUTHORIZATION = "Authorization"

interface WorkerApi {



    @POST("/api/core/gig-worker/signup")
    suspend fun registerWorker(
        @Body worker: Worker
    ): Response<ResponseBody>

    @POST("api/auth/login")
    suspend fun getToken(@Body authRequest: AuthRequest): Token

    @GET("/api/core/gig-worker/view/forms")
    suspend fun getFormsByStatus(

        @Header("Authorization") token: String): List<FormModel>
    @GET("/api/core/gig-worker/view/form/status/{formId}")
    suspend fun getGigWorkerJobStatus(
        @Path("formId") formId: Long,
        @Header("Authorization") token: String
    ): Long

    @POST("/api/core/gig-worker/view/forms/proposal/submit")
    suspend fun submitProposal(
        @Query("form_id") formId: Long,
        @Body proposal:Proposal,
        @Header("Authorization") token: String)
    : Response<String>

    @GET("/api/core/gig-worker/view/profile")
    suspend fun getGigWorkerById(@Header("Authorization") token: String): Worker

    @GET("/api/core/gig-worker/view/forms/claimed")
    suspend fun getAllFormByClaimed(
        @Header("Authorization") token: String
    ): List<Map<String, Any>>

    @GET("/api/core/gig-worker/view/forms/applied")
    suspend fun getAllFormsByApplied(
        @Header("Authorization") token: String
    ): List<Map<String, Any>>

    @PUT("/api/core/gig-worker/view/profile/update")
    suspend fun updateGigworker(
        @Body workerupdate: WorkerUpdate,
        @Header("Authorization") token: String
    ): WorkerUpdate

    @POST("/api/core/gig-worker/view/forms/formQuestion/submit-response")
    suspend fun submitResponse(@Body userResponse: UserResponse,
                               @Header("Authorization") token: String):Map<String, Any>



}