package com.example.truffol.di

import com.example.truffol.db.ShopDao
import com.example.truffol.db.TruffleDao
import com.example.truffol.db.model.ShopEntityMapper
import com.example.truffol.db.model.TruffleEntityMapper
import com.example.truffol.interactors.shop.GetShopUseCase
import com.example.truffol.interactors.truffle.GetTruffleUseCase
import com.example.truffol.interactors.shop.SearchShopsUseCase
import com.example.truffol.interactors.truffle.SearchTrufflesUseCase
import com.example.truffol.network.ShopService
import com.example.truffol.network.TruffleService
import com.example.truffol.network.model.ShopDtoMapper
import com.example.truffol.network.model.TruffleDtoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object InteractorsModule {

    @ViewModelScoped
    @Provides
    fun provideSearchShopsUseCase(
        shopService: ShopService,
        shopDao: ShopDao,
        shopEntityMapper: ShopEntityMapper,
        shopDtoMapper: ShopDtoMapper,
    ): SearchShopsUseCase {
        return SearchShopsUseCase(
            shopService = shopService,
            shopDao = shopDao,
            entityMapper = shopEntityMapper,
            dtoMapper = shopDtoMapper
        )
    }

    @ViewModelScoped
    @Provides
    fun provideSearchTrufflesUseCase(
        truffleService: TruffleService,
        truffleDao: TruffleDao,
        entityMapper: TruffleEntityMapper,
        dtoMapper: TruffleDtoMapper,
    ): SearchTrufflesUseCase {
        return SearchTrufflesUseCase(
            truffleDao = truffleDao,
            truffleService = truffleService,
            entityMapper = entityMapper,
            dtoMapper = dtoMapper
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetTruffleUseCase(
        truffleService: TruffleService,
        truffleDao: TruffleDao,
        entityMapper: TruffleEntityMapper,
        dtoMapper: TruffleDtoMapper,
    ): GetTruffleUseCase {
        return GetTruffleUseCase(
            truffleDao = truffleDao,
            truffleService = truffleService,
            entityMapper = entityMapper,
            dtoMapper = dtoMapper
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetShopUseCase(
        shopService: ShopService,
        shopDao: ShopDao,
        entityMapper: ShopEntityMapper,
        dtoMapper: ShopDtoMapper,
    ): GetShopUseCase {
        return GetShopUseCase(
            shopDao = shopDao,
            shopService = shopService,
            entityMapper = entityMapper,
            shopDtoMapper = dtoMapper
        )
    }
}