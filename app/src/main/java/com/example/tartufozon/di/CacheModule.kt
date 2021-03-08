package com.example.tartufozon.di

import androidx.room.Room
import com.example.tartufozon.BaseApplication
import com.example.tartufozon.db.AppDatabase
import com.example.tartufozon.db.ShopDao
import com.example.tartufozon.db.model.ShopEntityMapper
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
    fun provideShopEntityMapper() : ShopEntityMapper {
        return ShopEntityMapper()
    }

    /*@Singleton
    @Provides
    fun provideTruffleEntityMapper() : TruffleEntityMapper {
        return TruffleEntityMapper()
    }*/
}