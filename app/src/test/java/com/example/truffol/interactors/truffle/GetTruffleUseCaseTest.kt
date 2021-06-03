package com.example.truffol.interactors.truffle

import com.example.truffol.cache.AppDatabaseFake
import com.example.truffol.cache.TruffleDaoFake
import com.example.truffol.db.ShopDao
import com.example.truffol.db.TruffleDao
import com.example.truffol.db.model.TruffleEntityMapper
import com.example.truffol.domain.model.Truffle
import com.example.truffol.domain.util.DataState
import com.example.truffol.interactors.responses.MockWebServerResponses
import com.example.truffol.interactors.shop.GetShopUseCase
import com.example.truffol.network.ShopService
import com.example.truffol.network.TruffleService
import com.example.truffol.network.model.TruffleDtoMapper
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.Flow
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
    private val TRUFFLE_ID = 1

    // system under test
    private lateinit var getTruffleUseCase: GetTruffleUseCase

    // Dependencies
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

        // instantiate the system under test
        getTruffleUseCase = GetTruffleUseCase(
            truffleDao = truffleDao,
            entityMapper = entityMapper,
            truffleService = truffleService,
            truffleDtoMapper = dtoMapper,
        )
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `Successful behaviour of the UseCase`(): Unit = runBlocking {
        // Given
        setMockWebServerSuccessfulResponse()

        // run use case
        val truffleAsFlow: List<DataState<Truffle>> = runSystemUnderTest()

        // first emission should be `loading`
        assert(truffleAsFlow[0].loading)

        // second emission should be the truffle
        val truffle = truffleAsFlow[1].data
        assert(truffle?.truffleId == TRUFFLE_ID)

        // confirm it is actually a truffle object
        assert(truffle is Truffle)

        // 'loading' should be false now
        assert(!truffleAsFlow[1].loading)
    }

    @Test
    fun `Failing behaviour of the UseCase`(): Unit = runBlocking {
        // Given
        setMockWebServerFailedResponse()

        // run use case
        val truffleAsFlow: List<DataState<Truffle>> = runSystemUnderTest()

        // first emission should be `loading`
        assert(truffleAsFlow[0].loading)

        // second emission should be the truffle
        val truffle = truffleAsFlow[1].data
        assertFalse(truffle?.truffleId == TRUFFLE_ID)

        // confirm it is actually a truffle object
        assertFalse(truffle is Truffle)

        // 'loading' should be false now
        assert(!truffleAsFlow[1].loading)
    }

    @Test
    fun `First emission should be Loading`(): Unit = runBlocking {
        // Given
        setMockWebServerSuccessfulResponse()

        // When
        val truffleAsFlow: List<DataState<Truffle>> = runSystemUnderTest()

        // Then
        assert(truffleAsFlow[0].loading)
    }

    @Test
    fun `Second emission should be the truffle`(): Unit = runBlocking {
        // Given
        setMockWebServerSuccessfulResponse()

        // When
        val truffleAsFlow: List<DataState<Truffle>> = runSystemUnderTest()

        // Then
        val truffle = truffleAsFlow[1].data
        assert(truffle?.truffleId == TRUFFLE_ID)
    }

    @Test
    fun `Confirm second emitted object is a Truffle`(): Unit = runBlocking {
        // Given
        setMockWebServerSuccessfulResponse()

        // When
        val truffleAsFlow: List<DataState<Truffle>> = runSystemUnderTest()

        // Then
        val truffle = truffleAsFlow[1].data
        assert(truffle is Truffle)
    }

    @Test
    fun `Confirm Loading is false After truffle is emitted`(): Unit = runBlocking {
        // Given
        setMockWebServerSuccessfulResponse()

        // When
        val truffleAsFlow: List<DataState<Truffle>> = runSystemUnderTest()

        // Then
        assertFalse(truffleAsFlow[1].loading)
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
        // TODO: 03.06.21 ...
    }

    private fun runSystemUnderTest(): List<DataState<Truffle>> = runBlocking {
        getTruffleUseCase.run(TRUFFLE_ID).toList()
    }

    private fun setMockWebServerSuccessfulResponse() {
        // condition the response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(MockWebServerResponses.truffleResponse)
        )
    }

    private fun setMockWebServerFailedResponse() {
        // condition the response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
                .setBody("Empty response")
        )
    }
}