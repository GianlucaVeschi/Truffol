package com.example.tartufozon.di

import com.example.tartufozon.network.RemoteDataSource
import com.example.tartufozon.presentation.ui.shopview.ShopRepositoryImpl
import com.example.tartufozon.presentation.ui.truffleview.repo.TruffleRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
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