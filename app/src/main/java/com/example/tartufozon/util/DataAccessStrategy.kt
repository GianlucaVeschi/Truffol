package com.example.tartufozon.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.tartufozon.network.util.Resource
import kotlinx.coroutines.Dispatchers

class DataAccessStrategy {

    fun <T, A> performGetOperation(
        databaseQuery: () -> LiveData<T>,
        networkCall: suspend () -> Resource<A>,
        saveCallResult: suspend (A) -> Unit
    ): LiveData<Resource<T>> = liveData(Dispatchers.IO) {

        //Check this out if you have a database
        //https://github.com/GianlucaVeschi/Thalia_Kitty_App_Challenge/blob/master/app/src/main/java/com/example/thaliakittyappchallenge/ui/MainRepository.kt
    }
}