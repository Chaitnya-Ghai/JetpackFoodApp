package com.example.jetpackfoodapp.viewModels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackfoodapp.api_dataClasses.Category
import com.example.jetpackfoodapp.api_dataClasses.Meal
import com.example.jetpackfoodapp.SealedClass
import com.example.jetpackfoodapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    private val _categories = mutableStateOf<SealedClass<List<Category>>>(SealedClass.Loading())
    val categoriesApiState: State<SealedClass<List<Category>>> = _categories

    private val _meals = mutableStateOf<SealedClass<List<Meal>>>(SealedClass.Loading())
    val mealsApiState: State<SealedClass<List<Meal>>> = _meals

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            _categories.value = SealedClass.Loading()
            try {
                val response = RetrofitInstance.api.getCategories()
                Log.d("API_RESPONSE", "Categories: ${response.categories}")
                _categories.value = SealedClass.Success(response.categories)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Fetching Categories went wrong: ${e.message}")
                _categories.value = SealedClass.Error("Fetching Categories went wrong: ${e.message}")
            }
        }
    }

    fun fetchMealsByCategory(categoryId: String) {
        viewModelScope.launch {
            _meals.value = SealedClass.Loading()
            try {
                val response = RetrofitInstance.api.getMealsByCategory(categoryId)
                Log.d("API_RESPONSE", "Meals: ${response.meals}")
                _meals.value = SealedClass.Success(response.meals)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Fetching Meals went wrong: ${e.message}")
                _meals.value = SealedClass.Error("Fetching Meals went wrong: ${e.message}")
            }
        }
    }
}
