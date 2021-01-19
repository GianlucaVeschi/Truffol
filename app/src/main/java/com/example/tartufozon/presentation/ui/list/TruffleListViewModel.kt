package com.example.tartufozon.presentation.ui.list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tartufozon.domain.model.Truffle
import com.example.tartufozon.presentation.ui.repo.TruffleRepositoryImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber


class TruffleListViewModel @ViewModelInject
constructor(
    private val truffleRepositoryImpl: TruffleRepositoryImpl
) : ViewModel() {

    val trufflesList: MutableState<List<Truffle>> = mutableStateOf(ArrayList())
    val query = mutableStateOf("")
    val selectedCategory: MutableState<TruffleCategory?> = mutableStateOf(null)
    var categoryScrollPosition: Float = 0f
    val loading = mutableStateOf(false)

    init {
        onTriggerEvent(TruffleListEvent.GetTruffleListEvent)

        //DEBUG LOCAL DB
        onTriggerEvent(TruffleListEvent.GetLocalTruffleList)
    }

    fun onTriggerEvent(event: TruffleListEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is TruffleListEvent.GetTruffleListEvent -> {
                        getTruffleList()
                    }
                    //Additional pseudo use cases go here...
                    is TruffleListEvent.GetShuffledTruffleList -> {
                        getShuffledTruffleList()
                    }
                    is TruffleListEvent.GetLocalTruffleList -> {
                        getLocalTruffleList()
                    }
                }
            } catch (e: Exception) {
                Timber.e("launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            } finally {
                Timber.d("launchJob: finally called.")
            }
        }
    }

    //Pseudo Use Case #1
    private suspend fun getTruffleList() {
        loading.value = true
        delay(2000) //Fake Delay

        val tmpTrufflesList = truffleRepositoryImpl.getTruffleList()
        trufflesList.value = tmpTrufflesList.tartufi // TODO: 30.12.20 : Remove tmp list

        loading.value = false

    }

    //Pseudo Use Case #2
    private suspend fun getShuffledTruffleList() {
        resetSearchState()
        loading.value = true
        delay(2000) //Fake Delay

        val tmpTrufflesList = truffleRepositoryImpl.getTruffleList()
        trufflesList.value = tmpTrufflesList.tartufi.shuffled()
        loading.value = false

    }

    //Pseudo Use Case #3
    private suspend fun getLocalTruffleList() {

        val tmpTrufflesList = truffleRepositoryImpl.getLocalTruffleList()
        Timber.d("Gianluca $tmpTrufflesList")
    }

    fun onQueryChanged(query: String) {
        this.query.value = query
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getTruffleCategory(category)
        selectedCategory.value = newCategory
        onQueryChanged(category)
    }

    fun onChangeCategoryScrollPosition(position: Float) {
        categoryScrollPosition = position
    }

    /**
     * Called when a new search is executed.
     */
    private fun resetSearchState() {
        trufflesList.value = listOf()
        if (selectedCategory.value?.value != query.value) clearSelectedCategory()
    }

    private fun clearSelectedCategory() {
        selectedCategory.value = null
    }

}