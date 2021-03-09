package com.example.tartufozon.di

import com.example.tartufozon.db.ShopDao
import com.example.tartufozon.db.TruffleDao
import com.example.tartufozon.db.model.ShopEntityMapper
import com.example.tartufozon.db.model.TruffleEntityMapper
import com.example.tartufozon.domain.model.Truffle
import com.example.tartufozon.interactors.GetShopUseCase
import com.example.tartufozon.interactors.GetTruffleUseCase
import com.example.tartufozon.interactors.SearchShopsUseCase
import com.example.tartufozon.interactors.SearchTrufflesUseCase
import com.example.tartufozon.network.ShopService
import com.example.tartufozon.network.TruffleService
import com.example.tartufozon.network.model.ShopDtoMapper
import com.example.tartufozon.network.model.TruffleDtoMapper
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
            truffleDtoMapper = dtoMapper
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