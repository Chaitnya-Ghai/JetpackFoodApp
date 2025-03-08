package com.example.jetpackfoodapp

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.jetpackfoodapp.api_dataClasses.Category
@Composable
fun CategoryDetailScreen(category: Category, navigateToMealsScreen: (String) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = category.strCategory.toString())

            Image(
                painter = rememberAsyncImagePainter(category.strCategoryThumb),
                contentDescription = "Category Image",
                modifier = Modifier
                    .wrapContentSize()
                    .aspectRatio(1f),
                contentScale = ContentScale.Fit
            )

            Text(
                text = category.strCategoryDescription.toString(),
                textAlign = TextAlign.Justify,
                modifier = Modifier
                    .padding(bottom = 80.dp) // Extra space for the FAB
            )
        }

        ExtendedFloatingActionButton(
            onClick = {
                Log.d("NAVIGATION", "Navigating with ID: ${category.strCategory}")
                navigateToMealsScreen(category.strCategory.toString())
            },
            icon = { Icon(Icons.Default.ArrowForward, contentDescription = "Go") },
            text = { Text(text = "Go to Meals") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        )
    }
}
