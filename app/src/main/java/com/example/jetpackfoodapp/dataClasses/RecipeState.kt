package com.example.jetpackfoodapp.dataClasses

import com.example.jetpackfoodapp.api_dataClasses.Category

data class RecipeState(
    val isLoading: Boolean = false,
    val categories: List<Category> = emptyList(),
    val error: String? = null
)
