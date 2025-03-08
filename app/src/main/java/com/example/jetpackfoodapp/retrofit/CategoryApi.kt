package com.example.jetpackfoodapp.retrofit

import com.example.jetpackfoodapp.api_dataClasses.CategoryList
import com.example.jetpackfoodapp.api_dataClasses.MealsByCategoryList
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoryApi {
    @GET("categories.php")
    suspend fun getCategories() : CategoryList

    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String) : MealsByCategoryList

}