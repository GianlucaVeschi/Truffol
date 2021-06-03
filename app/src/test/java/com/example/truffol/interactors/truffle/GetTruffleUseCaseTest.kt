package com.example.truffol.interactors.truffle

import com.example.truffol.cache.AppDatabaseFake
import com.example.truffol.cache.TruffleDaoFake
import com.example.truffol.db.ShopDao
import com.example.truffol.db.TruffleDao
import com.example.truffol.db.model.TruffleEntityMapper
import com.example.truffol.domain.model.Truffle
import com.example.truffol.interactors.responses.MockWebServerResponses
import com.example.truffol.interactors.shop.GetShopUseCase
import com.example.truffol.network.ShopService
import com.example.truffol.network.TruffleService
import com.example.truffol.network.model.TruffleDtoMapper
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test;
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class GetTruffleUseCaseTest {

    private val appDatabase = AppDatabaseFake()
    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl
    private val Truffle_ID = 1

    // system under test
    private lateinit var getTruffleUseCase: GetTruffleUseCase

    // Dependencies
    private lateinit var searchTrufflesUseCase: SearchTrufflesUseCase
    private lateinit var truffleService: TruffleService
    private lateinit var truffleDao: TruffleDao
    private val dtoMapper = TruffleDtoMapper()
    private val entityMapper = TruffleEntityMapper()

    @BeforeEach
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        baseUrl = mockWebServer.url("/")
        truffleService = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(TruffleService::class.java)

        truffleDao = TruffleDaoFake(appDatabaseFake = appDatabase)

        searchTrufflesUseCase = SearchTrufflesUseCase(
            truffleDao = truffleDao,
            truffleService = truffleService,
            entityMapper = entityMapper,
            dtoMapper = dtoMapper
        )

        // instantiate the system under test
        getTruffleUseCase = GetTruffleUseCase(
            truffleDao = truffleDao,
            entityMapper = entityMapper,
            truffleService = truffleService,
            truffleDtoMapper = dtoMapper,
        )
    }

    /**
     * 1. Get some truffles from the network and insert into cache
     * 2. Try to retrieve truffles by their specific truffle id
     */
    @Test
    fun `Correct behaviour of the UseCase`(): Unit = runBlocking {
        // condition the response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(MockWebServerResponses.truffleResponse)
        )

        // confirm the cache is empty to start
        assert(truffleDao.getAllTruffles().isEmpty())

        // get truffle from network and insert into cache
        val searchResult = getTruffleUseCase.run(Truffle_ID)

    }


    /**
     * 1. Try to get a truffle that does not exist in the cache
     * Result should be:
     * 1. truffle is retrieved from network and inserted into cache
     * 2. truffle is returned as flow from cache
     */
    @Test
    fun attemptGetNullTruffleFromCache_getTruffleById(): Unit = runBlocking {
        // condition the response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(MockWebServerResponses.truffleResponse)
        )


    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }
}