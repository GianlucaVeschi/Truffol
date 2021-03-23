package com.example.tartufozon.interactors.truffle

import com.example.tartufozon.interactors.responses.MockWebServerResponses.truffleListResponse
import com.example.tartufozon.network.TruffleService
import com.google.gson.GsonBuilder
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class SearchTrufflesUseCaseTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl

    // Dependencies
    private lateinit var truffleService: TruffleService

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
}