package com.example.jetpackfoodapp

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.jetpackfoodapp.api_dataClasses.Meal
import com.example.jetpackfoodapp.viewModels.MainActivityViewModel


@Composable
fun MealsScreen(
    modifier: Modifier = Modifier,
    id: String,
    viewModel: MainActivityViewModel = viewModel()
) {
    val viewState = viewModel.mealsApiState.value

    LaunchedEffect(id) {
        Log.d("MEALS_FETCH", "Fetching meals for ID: $id")
        viewModel.fetchMealsByCategory(id)
    }

    Box(modifier.fillMaxSize()) {
        when (viewState) {
            is SealedClass.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is SealedClass.Error -> {
                Text(
                    text = viewState.mesg ?: "Unknown error",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Red
                )
            }

            is SealedClass.Success -> {
                val meals = viewState.data ?: emptyList()
                if (meals.isEmpty()) {
                    Text(
                        text = "No meals available",
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    LazyColumn {
                        items(meals) { meal ->
                            MealLayout(meal)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun MealLayout(meal: Meal) {
    Column(
        modifier = Modifier.padding(18.dp).fillMaxSize().clickable{true} ,
        horizontalAlignment = Alignment.CenterHorizontally ,
        )
    {
        Image(
            painter = rememberAsyncImagePainter(meal.strMealThumb) ,
            contentDescription = "Category Image",
            modifier = Modifier.wrapContentSize().aspectRatio(1f),
            contentScale= ContentScale.Fit
        )
        Text(text = meal.strMeal , modifier = Modifier.padding(8.dp))
    }
}