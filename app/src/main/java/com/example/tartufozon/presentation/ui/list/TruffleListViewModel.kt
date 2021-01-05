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
        getTruffleList()
    }

    fun getTruffleList() {
        viewModelScope.launch {
            loading.value = true
            delay(2000) //Fake Delay

            val tmpTrufflesList = truffleRepositoryImpl.getTruffleList()
            trufflesList.value = tmpTrufflesList.tartufi // TODO: 30.12.20 : Remove tmp list
            loading.value = false
        }
    }

    fun getReversedTruffleList() {
        viewModelScope.launch {
            resetSearchState()
            loading.value = true
            delay(2000) //Fake Delay

            val tmpTrufflesList = truffleRepositoryImpl.getTruffleList()
            trufflesList.value = tmpTrufflesList.tartufi.asReversed()
            loading.value = false
        }
    }

    fun onQueryChanged(query: String){
        this.query.value = query
    }

    fun onSelectedCategoryChanged(category: String){
        val newCategory = getTruffleCategory(category)
        selectedCategory.value = newCategory
        onQueryChanged(category)
    }

    fun onChangeCategoryScrollPosition(position: Float){
        categoryScrollPosition = position
    }

    /**
     * Called when a new search is executed.
     */
    private fun resetSearchState(){
        trufflesList.value = listOf()
        if(selectedCategory.value?.value != query.value) clearSelectedCategory()
    }

    private fun clearSelectedCategory(){
        selectedCategory.value = null
    }
}