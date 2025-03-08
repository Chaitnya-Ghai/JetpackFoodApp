package com.example.jetpackfoodapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.jetpackfoodapp.api_dataClasses.Category
import com.example.jetpackfoodapp.viewModels.MainActivityViewModel

@Composable
fun RecipeScreen(
    modifier: Modifier = Modifier,
    viewModel: MainActivityViewModel = viewModel(),
    navigateToDetail: (Category) -> Unit
) {
    val viewState by viewModel.categoriesApiState  // Correct categoryState observation

    Box(modifier.fillMaxSize()) {
        when(viewState) {
            is SealedClass.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is SealedClass.Error -> {
                val message = (viewState as SealedClass.Error).mesg ?: "Unknown Error"
                Text(
                    text = message,
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Red
                )
            }
            is SealedClass.Success ->{
                val categories = (viewState as SealedClass.Success<List<Category>>).data ?: emptyList()
                CategoryScreen(categories = categories, navigateToDetail)            }
        }
    }
}

@Composable
fun CategoryScreen(categories: List<Category>, navigateToDetail: (Category) -> Unit = {}) {
    LazyVerticalGrid(GridCells.Fixed(2), modifier = Modifier.fillMaxSize().padding(5.dp)) {
        items(categories.filter { it.strCategory != "Beef" && it.strCategory != "Chicken" }) {
            category ->
            CategoryItem(category = category , { navigateToDetail(category) })
        }
    }
}

@Composable
fun CategoryItem(category: Category , onClick: () -> Unit ) {
    Column(
        modifier = Modifier.padding(8.dp).fillMaxSize() ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
                .clickable { onClick() },
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.elevatedCardElevation(8.dp) // Adds elevation
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally // Centers text horizontally
            ) {
                Image(
                    painter = rememberAsyncImagePainter(category.strCategoryThumb),
                    contentDescription = "Image of ${category.strCategory}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f), // Image takes up most space
                    contentScale = ContentScale.Fit// Adjusts scaling
                )
                Text(
                    text = category.strCategory.toString(),
                    modifier = Modifier.padding(8.dp),
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}
