package com.example.jetpackfoodapp

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.jetpackfoodapp.api_dataClasses.Category
import com.example.jetpackfoodapp.viewModels.MainActivityViewModel

@Composable
fun FoodApp(navController: NavHostController, modifier: Modifier) {
    val recipeMvvm: MainActivityViewModel = viewModel()
    NavHost(navController = navController, startDestination = Screen.RecipeScreen.route) {
        composable(Screen.RecipeScreen.route) {
            RecipeScreen(
                modifier = modifier,
                viewModel = recipeMvvm,
                navigateToDetail = {
                    navController.currentBackStackEntry?.savedStateHandle?.set("category", it)
                    navController.navigate(Screen.RecipeDetailScreen.route)
                }
            )
        }
        composable(route = Screen.RecipeDetailScreen.route) {
            val category = navController
                .previousBackStackEntry
                ?.savedStateHandle
                ?.get<Category>("category") ?: Category()

            CategoryDetailScreen(category) {
                navController.currentBackStackEntry?.savedStateHandle?.set("categoryId", category.strCategory)
                navController.navigate(Screen.MealsScreen.route)
            }
        }
        composable(route = Screen.MealsScreen.route) {
            val categoryId = navController
                .previousBackStackEntry
                ?.savedStateHandle
                ?.get<String>("categoryId") ?: "nothing"

            Log.d("CATEGORY_ID", "Category ID received: $categoryId")
            MealsScreen(modifier = modifier, id = categoryId, viewModel = recipeMvvm)
        }
    }
}

