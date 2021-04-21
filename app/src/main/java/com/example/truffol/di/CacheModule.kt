package com.example.truffol.di

import androidx.room.Room
import com.example.truffol.BaseApplication
import com.example.truffol.db.AppDatabase
import com.example.truffol.db.ShopDao
import com.example.truffol.db.TruffleDao
import com.example.truffol.db.model.ShopEntityMapper
import com.example.truffol.db.model.TruffleEntityMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideDb(app: BaseApplication): AppDatabase {
        return Room
            .databaseBuilder(app, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideShopDao(db: AppDatabase): ShopDao {
        return db.shopDao()
    }

    @Singleton
    @Provides
    fun provideTruffleDao(db: AppDatabase): TruffleDao {
        return db.truffleDao()
    }

    @Singleton
    @Provides
    fun provideShopEntityMapper() : ShopEntityMapper {
        return ShopEntityMapper()
    }

    @Singleton
    @Provides
    fun provideTruffleEntityMapper() : TruffleEntityMapper {
        return TruffleEntityMapper()
    }
}