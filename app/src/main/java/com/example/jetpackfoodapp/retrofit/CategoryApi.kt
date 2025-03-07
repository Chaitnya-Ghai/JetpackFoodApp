package com.example.jetpackfoodapp.retrofit

import com.example.jetpackfoodapp.api_dataClasses.CategoryList
import retrofit2.http.GET

interface CategoryApi {
    @GET("categories.php")
    suspend fun getCategories() : CategoryList
}