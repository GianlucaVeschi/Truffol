package com.example.truffol.interactors.truffle

import com.example.truffol.cache.AppDatabaseFake
import com.example.truffol.cache.TruffleDaoFake
import com.example.truffol.db.model.TruffleEntityMapper
import com.example.truffol.domain.model.Truffle
import com.example.truffol.interactors.responses.MockWebServerResponses.truffleListResponse
import com.example.truffol.network.TruffleService
import com.example.truffol.network.model.TruffleDtoMapper
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class SearchTrufflesUseCaseTest {

    private val appDatabase = AppDatabaseFake()
    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl

    // system under test
    private lateinit var searchTrufflesUseCase: SearchTrufflesUseCase

    // Dependencies
    private lateinit var truffleService: TruffleService
    private lateinit var truffleDao: TruffleDaoFake
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

        // instantiate the system in test
        searchTrufflesUseCase = SearchTrufflesUseCase(
            truffleDao = truffleDao,
            truffleService = truffleService,
            entityMapper = entityMapper,
            dtoMapper = dtoMapper
        )
    }

    @Test
    fun mockWebServerSetup() {
        // condition the response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(truffleListResponse)
        )
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }

    /**
     * 1. Are the truffles retrieved from the network?
     * 2. Are the truffles inserted into the cache?
     * 3. Are the truffles then emitted as a flow from the cache?
     */
    @Test
    fun getTrufflesFromNetwork_emitTrufflesFromCache(): Unit = runBlocking {

        // condition the response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(truffleListResponse)
        )

        // confirm the cache is empty to start
        assert(truffleDao.getAllTruffles().isEmpty())

        val flowItems = searchTrufflesUseCase.run().toList()

        // confirm the cache is no longer empty
        assert(truffleDao.getAllTruffles().isNotEmpty())

        // first emission should be `loading`
        assert(flowItems[0].loading)

        // Second emission should be the list of truffles
        val truffles = flowItems[1].data
        assert(truffles?.size?: 0 > 0)

        // confirm they are actually truffle objects
        assert(truffles?.get(index = 0) is Truffle)

        assert(!flowItems[1].loading) // loading should be false now
    }

    /**
     * Simulate a bad request
     */
    @Test
    fun getTrufflesFromNetwork_emitHttpError(): Unit = runBlocking {

        // condition the response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .setBody("{}")
        )

        val flowItems = searchTrufflesUseCase.run().toList()

        // first emission should be `loading`
        assert(flowItems[0].loading)

        // Second emission should be the exception
        val error = flowItems[1].error
        assert(error != null)

        assert(!flowItems[1].loading) // loading should be false now
    }
}