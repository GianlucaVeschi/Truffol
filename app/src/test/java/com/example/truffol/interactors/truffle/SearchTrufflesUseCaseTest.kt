package com.example.truffol.interactors.truffle

import com.example.truffol.cache.AppDatabaseFake
import com.example.truffol.cache.TruffleDaoFake
import com.example.truffol.db.model.TruffleEntityMapper
import com.example.truffol.domain.model.Truffle
import com.example.truffol.domain.util.DataState
import com.example.truffol.interactors.responses.MockWebServerResponses
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

        // instantiate the system under test
        searchTrufflesUseCase = SearchTrufflesUseCase(
            truffleDao = truffleDao,
            truffleService = truffleService,
            entityMapper = entityMapper,
            dtoMapper = dtoMapper
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
    fun `Simulate a full successful case`(): Unit = runBlocking {

        // condition the response
        setMockWebServerSuccessfulResponse()

        // confirm the cache is empty to start
        assert(truffleDao.getAllTruffles().isEmpty())

        val flowItems = runSystemUnderTest()

        // confirm the cache is no longer empty
        assert(truffleDao.getAllTruffles().isNotEmpty())

        // first emission should be `loading`
        assert(flowItems[0].loading)

        // Second emission should be the list of truffles
        val truffles = flowItems[1].data
        assert(truffles?.size ?: 0 > 0)

        // confirm they are actually truffle objects
        assert(truffles?.get(index = 0) is Truffle)

        assert(!flowItems[1].loading) // loading should be false now
    }

    @Test
    fun `Simulate a full failure case`(): Unit = runBlocking {

        // condition the response
        setMockWebServerFailedResponse()

        // confirm the cache is empty to start
        assert(truffleDao.getAllTruffles().isEmpty())

        val flowItems = runSystemUnderTest()

        // confirm the cache is still empty
        assert(truffleDao.getAllTruffles().isEmpty())

        // first emission should be `loading`
        assert(flowItems[0].loading)

        // Second emission should be the a null list of truffles
        val truffles = flowItems[1].data
        assert(truffles == null)

        assert(!flowItems[1].loading) // loading should be false now
    }

    @Test
    fun `Confirm truffleService call is successful`(): Unit = runBlocking {
        // condition the response
        setMockWebServerSuccessfulResponse()

        // Run call
        val response = truffleService.getTruffleList()

        // confirm the cache is empty to start
        assert(response.isSuccessful)
    }

    @Test
    fun `Confirm the cache is empty to start`(): Unit = runBlocking {
        // condition the response
        setMockWebServerSuccessfulResponse()

        // confirm the cache is empty to start
        assert(truffleDao.getAllTruffles().isEmpty())
    }

    @Test
    fun `Confirm the cache is no longer empty`(): Unit = runBlocking {
        // condition the response
        setMockWebServerSuccessfulResponse()

        val outputFlow = runSystemUnderTest()

        // confirm the cache is no longer empty
        assert(truffleDao.getAllTruffles().isNotEmpty())
    }

    @Test
    fun `Confirm first emission is loading`(): Unit = runBlocking {
        // condition the response
        setMockWebServerSuccessfulResponse()

        val flowItems = runSystemUnderTest()

        // first emission should be `loading`
        assert(flowItems[0].loading)
    }

    @Test
    fun `Second emission should be the list of truffles`(): Unit = runBlocking {
        // condition the response
        setMockWebServerSuccessfulResponse()

        val flowItems = runSystemUnderTest()

        // Second emission should be the list of truffles
        val trufflesList = flowItems[1].data
        assert(trufflesList?.size ?: 0 > 0)
    }

    @Test
    fun `Confirm emitted items are actually truffle objects`(): Unit = runBlocking {
        // condition the response
        setMockWebServerSuccessfulResponse()

        val flowItems = runSystemUnderTest()

        // confirm they are actually truffle objects
        val trufflesList = flowItems.drop(1).first()
        assert(trufflesList.data is List<Truffle>)
    }

    @Test
    fun `Confirm loading is false after the values are emitted`(): Unit = runBlocking {
        // condition the response
        setMockWebServerSuccessfulResponse()

        // confirm the cache is empty to start
        assert(truffleDao.getAllTruffles().isEmpty())

        val flowItems = runSystemUnderTest()

        assert(!flowItems[1].loading) // loading should be false now
    }

    /**
     * Simulate a bad request
     */

    @Test
    fun `Confirm truffleService call is NOT successful`(): Unit = runBlocking {
        // condition the response
        setMockWebServerFailedResponse()

        // Run call
        val response = truffleService.getTruffleList()

        // confirm the cache is empty to start
        assert(!response.isSuccessful)
    }

    @Test
    fun getTrufflesFromNetwork_emitHttpError(): Unit = runBlocking {
        // condition the response
        setMockWebServerFailedResponse()

        val flowItems = runSystemUnderTest()
        val error = flowItems[1].error

        // Second emission should be the exception
        assert(error != null)
    }

    private fun runSystemUnderTest() = runBlocking {
        searchTrufflesUseCase.run().toList()
    }

    private fun setMockWebServerSuccessfulResponse() {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(truffleListResponse)
        )
    }

    private fun setMockWebServerFailedResponse() {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
                .setBody("{}")
        )
    }
}