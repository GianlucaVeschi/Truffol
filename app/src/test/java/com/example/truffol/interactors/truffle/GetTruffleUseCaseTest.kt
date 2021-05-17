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
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class GetTruffleUseCaseTest {

    private val appDatabase = AppDatabaseFake()
    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl
    private val DUMMY_TOKEN = "gg335v5453453" // can be anything
    private val DUMMY_QUERY = "This doesn't matter" // can be anything

    // system in test

    private lateinit var getTruffleUseCase: GetTruffleUseCase
    private val Truffle_ID = 1

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
        baseUrl = mockWebServer.url("/api/recipe/")
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

        // instantiate the system in test
        getTruffleUseCase = GetTruffleUseCase(
            truffleDao = truffleDao,
            entityMapper = entityMapper,
            truffleService = truffleService,
            truffleDtoMapper = dtoMapper,
        )
    }

    /**
     * 1. Get some truffles from the network and insert into cache
     * 2. Try to retrieve recipes by their specific recipe id
     */
    @Test
    fun getTrufflesFromNetwork_getTruffleById(): Unit = runBlocking {
        // condition the response
        //mockWebServer.enqueue(
        //    MockResponse()
        //        .setResponseCode(HttpURLConnection.HTTP_OK)
        //        .setBody(MockWebServerResponses.truffleResponse)
        //)

        // confirm the cache is empty to start
        assert(truffleDao.getAllTruffles().isEmpty())

        // get recipes from network and insert into cache
        //val searchResult = searchTrufflesUseCase.run().toList()

        // confirm the cache is no longer empty
        //assert(truffleDao.getAllTruffles().isNotEmpty())

        // run use case
        //val truffleAsFlow = getTruffleUseCase.run(Truffle_ID).toList()

        // first emission should be `loading`
        //assert(truffleAsFlow[0].loading)

        // second emission should be the recipe
        //val truffle = truffleAsFlow[1].data
        //assert(truffle?.id == Truffle_ID)

        // confirm it is actually a Recipe object
        //assert(truffle is Truffle)

        // 'loading' should be false now
        //assert(!truffleAsFlow[1].loading)
    }


    /**
     * 1. Try to get a recipe that does not exist in the cache
     * Result should be:
     * 1. Recipe is retrieved from network and inserted into cache
     * 2. Recipe is returned as flow from cache
     */
    @Test
    fun attemptGetNullTruffleFromCache_getTruffleById(): Unit = runBlocking {
        // condition the response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(MockWebServerResponses.truffleResponse)
        )

        // confirm the cache is empty to start
        //assert(recipeDao.getAllRecipes(1, 30).isEmpty())

        // run use case
        //val recipeAsFlow = getRecipe.execute(RECIPE_ID, DUMMY_TOKEN, true).toList()

        // first emission should be `loading`
        //assert(recipeAsFlow[0].loading)

        // second emission should be the recipe
        //val recipe = recipeAsFlow[1].data
        //assert(recipe?.id == RECIPE_ID)

        // confirm the recipe is in the cache now
        //assert(recipeDao.getRecipeById(RECIPE_ID)?.id == RECIPE_ID)

        // confirm it is actually a Recipe object
        //assert(recipe is Recipe)

        // 'loading' should be false now
        //assert(!recipeAsFlow[1].loading)
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }
}