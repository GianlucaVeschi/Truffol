package com.example.tartufozon.presentation.ui.truffleview.list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tartufozon.domain.model.Truffle
import com.example.tartufozon.interactors.truffle.SearchTrufflesUseCase
import com.example.tartufozon.presentation.ui.truffleview.repo.TruffleRepositoryImpl
import com.example.tartufozon.presentation.ui.util.DialogQueue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TruffleListViewModel @Inject constructor(
    private val searchTrufflesUseCase: SearchTrufflesUseCase
) : ViewModel() {

    val trufflesList: MutableState<List<Truffle>> = mutableStateOf(ArrayList())
    val query = mutableStateOf("")
    val selectedCategory: MutableState<TruffleCategory?> = mutableStateOf(null)
    var categoryScrollPosition: Float = 0f
    val loading = mutableStateOf(false)
    val dialogQueue = DialogQueue()

    init {
        onTriggerEvent(TruffleListEvent.GetTruffleList)
    }

    fun onTriggerEvent(event: TruffleListEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    //Pseudo use cases go here...
                    is TruffleListEvent.GetShuffledTruffleList -> {
                        getShuffledTruffleList()
                    }
                    is TruffleListEvent.GetTruffleList -> {
                        getTruffleListUseCase()
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

    private fun getShuffledTruffleList() {
        resetSearchState()
        searchTrufflesUseCase.run().onEach { dataState ->
            loading.value = dataState.loading

            dataState.data?.let { list ->
                trufflesList.value = list.shuffled()
            }

            dataState.error?.let { error ->
                Timber.e("newSearch: ${error}")
                dialogQueue.appendErrorMessage("An error appeared",error)
            }
        }.launchIn(viewModelScope)
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

    private fun getTruffleListUseCase() {
        resetSearchState()
        searchTrufflesUseCase.run().onEach { dataState ->
            loading.value = dataState.loading

            dataState.data?.let { list ->
                trufflesList.value = list
            }

            dataState.error?.let { error ->
                Timber.e("newSearch: ${error}")
                dialogQueue.appendErrorMessage("An error appeared",error)
            }
        }.launchIn(viewModelScope)
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

    companion object{
        private const val TAG = "TruffleListViewModel"
    }
}