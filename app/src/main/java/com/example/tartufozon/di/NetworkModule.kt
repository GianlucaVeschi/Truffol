package com.example.tartufozon.di

import com.example.tartufozon.network.LocalTruffleService
import com.example.tartufozon.network.RemoteDataSource
import com.example.tartufozon.network.TruffleService
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val LOCAL_DB = "http://10.0.2.2:3000/"
const val POSTMAN_DB = "https://761b9ae7-1a9c-4756-ace0-1bae12bfbead.mock.pstmn.io/"

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(loggingInterceptor)
            .addNetworkInterceptor(StethoInterceptor())
            .connectTimeout(30, TimeUnit.SECONDS) //Backend is really slow
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitService(okHttpClient: OkHttpClient): TruffleService {
        return Retrofit.Builder()
            .baseUrl(POSTMAN_DB)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(TruffleService::class.java)
    }

    @Singleton
    @Provides
    fun provideLocalRetrofitService(okHttpClient: OkHttpClient): LocalTruffleService {
        return Retrofit.Builder()
            .baseUrl(LOCAL_DB)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(LocalTruffleService::class.java)
    }

    @Singleton
    @Provides
    fun provideNetworkDataSource(
        truffleService: TruffleService,
        localTruffleService: LocalTruffleService
    ): RemoteDataSource {
        return RemoteDataSource(truffleService, localTruffleService)
    }

}