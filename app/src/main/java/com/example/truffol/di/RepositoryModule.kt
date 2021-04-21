package com.example.truffol.di

import com.example.truffol.network.RemoteDataSource
import com.example.truffol.presentation.ui.shopview.repo.ShopRepositoryImpl
import com.example.truffol.presentation.ui.truffleview.repo.TruffleRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideTruffleRepositoryImpl(remoteDataSource: RemoteDataSource) : TruffleRepositoryImpl{
        return TruffleRepositoryImpl(remoteDataSource)
    }

    @Singleton
    @Provides
    fun provideShopRepositoryImpl(remoteDataSource: RemoteDataSource) : ShopRepositoryImpl {
        return ShopRepositoryImpl(remoteDataSource)
    }
}