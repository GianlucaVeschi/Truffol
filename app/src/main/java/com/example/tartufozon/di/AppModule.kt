package com.example.tartufozon.di

import android.content.Context
import com.example.tartufozon.MyApplication
import com.example.tartufozon.network.RemoteDataSource
import com.example.tartufozon.presentation.ui.repo.TruffleRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): MyApplication{
        return app as MyApplication
    }

    @Singleton
    @Provides
    fun provideRandomString(): String{
        return "Hey look a random string!!!!! Gianluca"
    }

    @Singleton
    @Provides
    fun provideTruffleRepository(remoteDataSource: RemoteDataSource ) = TruffleRepositoryImpl(remoteDataSource)
}