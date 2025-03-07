package com.example.jetpackfoodapp.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackfoodapp.dataClasses.RecipeState
import com.example.jetpackfoodapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch

class MainActivityViewModel: ViewModel() {
    private val _categories = mutableStateOf(RecipeState())
    val categoriesApiState: State<RecipeState> = _categories
//    In Jetpack Compose, State<T> is an observable state holder
//    that allows the UI to automatically recompose when the state changes.
    init {
        fetchCategories()
    }

    private fun fetchCategories() {
//        as suspend functions are only called from coroutines
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getCategories()
                _categories.value = _categories.value.copy(isLoading = false , categories = response.categories , error = null)
            }catch (e:Exception){
                _categories.value = _categories.value.copy(isLoading = false , error = "Fetching Categories went wrong ${ e.message }")
            }


        }
    }
}