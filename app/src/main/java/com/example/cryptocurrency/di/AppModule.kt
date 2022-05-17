package com.example.cryptocurrency.di

import com.example.cryptocurrency.data.service.RemoteRepository
import com.example.cryptocurrency.data.service.RemoteService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun providesRemoteService():RemoteService = RemoteService()

    @Provides
    @Singleton
    fun providesRemoteRepository(service : RemoteService): RemoteRepository = RemoteRepository(service)

}