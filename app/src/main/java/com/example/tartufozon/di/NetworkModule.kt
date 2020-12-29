package com.example.tartufozon.di

import com.example.tartufozon.network.TruffleService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitService(): TruffleService {
        return Retrofit.Builder()
            .baseUrl("https://761b9ae7-1a9c-4756-ace0-1bae12bfbead.mock.pstmn.io/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(TruffleService::class.java)
    }

}