package com.example.tartufozon.network

import com.example.tartufozon.network.util.Resource
import retrofit2.Response
import timber.log.Timber

abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Timber.d("Body not null")
                    return Resource.success(body)
                }
            }
            return throwResourceError(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return throwResourceError(e.message ?: e.toString())
        }
    }

    private fun <T> throwResourceError(message: String): Resource<T> {
        return Resource.error("Network call has failed for the following reason: $message")
    }

}
