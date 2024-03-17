package com.gebeya.sebsab_mobile.di


import com.gebeya.sebsab_mobile.data.network.api.WorkerApi
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideWorkerApi(): WorkerApi {
        val timeoutInSeconds = 60L // Set your desired timeout value in seconds

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(timeoutInSeconds, TimeUnit.SECONDS)
            .readTimeout(timeoutInSeconds, TimeUnit.SECONDS)
            .writeTimeout(timeoutInSeconds, TimeUnit.SECONDS)
            .build()
        return Retrofit.Builder()
            .baseUrl("http://192.168.101.7:8080")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(okHttpClient)
            .build()
            .create(WorkerApi::class.java)
    }

}