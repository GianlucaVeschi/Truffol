package com.example.truffol.interactors.shop

import com.example.truffol.cache.AppDatabaseFake
import com.example.truffol.cache.ShopDaoFake
import com.example.truffol.db.model.ShopEntityMapper
import com.example.truffol.domain.model.Shop
import com.example.truffol.interactors.responses.MockWebServerResponses
import com.example.truffol.network.ShopService
import com.example.truffol.network.model.ShopDtoMapper
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

class SearchShopsUseCaseTest {

    private val appDatabase = AppDatabaseFake()
    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl

    // system under test
    private lateinit var searchShopsUseCase: SearchShopsUseCase

    // Dependencies
    private lateinit var shopService: ShopService
    private lateinit var shopDao: ShopDaoFake
    private val dtoMapper = ShopDtoMapper()
    private val entityMapper = ShopEntityMapper()

    @BeforeEach
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        baseUrl = mockWebServer.url("/")
        shopService = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(ShopService::class.java)

        shopDao = ShopDaoFake(appDatabaseFake = appDatabase)

        // instantiate the system under test
        searchShopsUseCase = SearchShopsUseCase(
            shopDao = shopDao,
            shopService = shopService,
            entityMapper = entityMapper,
            dtoMapper = dtoMapper
        )
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }

    /**
     * 1. Are the shops retrieved from the network?
     * 2. Are the shops inserted into the cache?
     * 3. Are the shops then emitted as a flow from the cache?
     */
    @Test
    fun `Simulate a full successful case`(): Unit = runBlocking {

        // condition the response
        setMockWebServerSuccessfulResponse()

        // confirm the cache is empty to start
        assert(shopDao.getAllShops().isEmpty())

        val flowItems = runSystemUnderTest()

        // confirm the cache is no longer empty
        assert(shopDao.getAllShops().isNotEmpty())

        // first emission should be `loading`
        assert(flowItems[0].loading)

        // Second emission should be the list of shops
        val shops = flowItems[1].data
        assert(shops?.size ?: 0 > 0)

        // confirm they are actually shop objects
        assert(shops?.get(index = 0) is Shop)

        assert(!flowItems[1].loading) // loading should be false now
    }

    @Test
    fun `Simulate a full failure case`(): Unit = runBlocking {

        // condition the response
        setMockWebServerFailedResponse()

        // confirm the cache is empty to start
        assert(shopDao.getAllShops().isEmpty())

        val flowItems = runSystemUnderTest()

        // confirm the cache is still empty
        assert(shopDao.getAllShops().isEmpty())

        // first emission should be `loading`
        assert(flowItems[0].loading)

        // Second emission should be the a null list of shops
        val shops = flowItems[1].data
        assert(shops == null)

        assert(!flowItems[1].loading) // loading should be false now
    }

    @Test
    fun `Confirm shopService call is successful`(): Unit = runBlocking {
        // condition the response
        setMockWebServerSuccessfulResponse()

        // Run call
        val response = shopService.getShopList()

        // confirm the cache is empty to start
        assert(response.isSuccessful)
    }

    @Test
    fun `Confirm the cache is empty to start`(): Unit = runBlocking {
        // condition the response
        setMockWebServerSuccessfulResponse()

        // confirm the cache is empty to start
        assert(shopDao.getAllShops().isEmpty())
    }

    @Test
    fun `Confirm the cache is no longer empty`(): Unit = runBlocking {
        // condition the response
        setMockWebServerSuccessfulResponse()

        val outputFlow = runSystemUnderTest()

        // confirm the cache is no longer empty
        assert(shopDao.getAllShops().isNotEmpty())
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
    fun `Second emission should be the list of shops`(): Unit = runBlocking {
        // condition the response
        setMockWebServerSuccessfulResponse()

        val flowItems = runSystemUnderTest()

        // Second emission should be the list of shops
        val shopsList = flowItems[1].data
        assert(shopsList?.size ?: 0 > 0)
    }

    @Test
    fun `Confirm emitted items are actually shop objects`(): Unit = runBlocking {
        // condition the response
        setMockWebServerSuccessfulResponse()

        val flowItems = runSystemUnderTest()

        // confirm they are actually shop objects
        val shopsList = flowItems.drop(1).first()
        assert(shopsList.data is List<Shop>)
    }

    @Test
    fun `Confirm loading is false after the values are emitted`(): Unit = runBlocking {
        // condition the response
        setMockWebServerSuccessfulResponse()

        // confirm the cache is empty to start
        assert(shopDao.getAllShops().isEmpty())

        val flowItems = runSystemUnderTest()

        assert(!flowItems[1].loading) // loading should be false now
    }

    /**
     * Simulate a bad request
     */

    @Test
    fun `Confirm shopService call is NOT successful`(): Unit = runBlocking {
        // condition the response
        setMockWebServerFailedResponse()

        // Run call
        val response = shopService.getShopList()

        // confirm the cache is empty to start
        assert(!response.isSuccessful)
    }

    @Test
    fun getShopsFromNetwork_emitHttpError(): Unit = runBlocking {
        // condition the response
        setMockWebServerFailedResponse()

        val flowItems = runSystemUnderTest()
        val error = flowItems[1].error

        // Second emission should be the exception
        assert(error != null)
    }

    private fun runSystemUnderTest() = runBlocking {
        searchShopsUseCase.run().toList()
    }

    private fun setMockWebServerSuccessfulResponse() {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(MockWebServerResponses.shopListResponse)
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