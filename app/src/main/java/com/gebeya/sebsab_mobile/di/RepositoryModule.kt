package com.gebeya.sebsab_mobile.di

import android.app.Application
import com.gebeya.sebsab_mobile.data.network.api.WorkerApi
import com.gebeya.sebsab_mobile.data.network.repository.PreferencesRepositoryImpl
import com.gebeya.sebsab_mobile.data.network.repository.WokerRepositoryImpl
import com.gebeya.sebsab_mobile.domain.repository.PreferencesRepository
import com.gebeya.sebsab_mobile.domain.repository.WorkerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideWorkerRepository(workerApi: WorkerApi, preferencesRepository: PreferencesRepository): WorkerRepository{
        return WokerRepositoryImpl(workerApi, preferencesRepository)
    }

    @Provides
    @Singleton
    fun providePreferencesRepository(application: Application): PreferencesRepository {
        return PreferencesRepositoryImpl(application)
    }
}