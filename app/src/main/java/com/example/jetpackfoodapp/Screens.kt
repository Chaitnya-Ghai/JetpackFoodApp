package com.example.jetpackfoodapp

sealed  class Screen(val route: String) {
    object RecipeScreen:Screen("RecipeScreen")
    object RecipeDetailScreen:Screen("RecipeDetailScreen")
}