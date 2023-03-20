package com.example.myapplication.android.Components

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.Models.Recipe

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecipesListView(onClick: (recipeName: String) -> Unit, recipes: List<Recipe>) {
    val scrollState = rememberScrollState()
    Box(modifier = Modifier.height(430.dp)) {
        LazyColumn(
            modifier = Modifier
                .scrollable(
                    scrollState,
                    orientation = Orientation.Vertical,
                    enabled = true
                )
        ) {
            items(recipes) {
                RecipeCard(recipe = it, onClick = onClick) {}
            }
        }
    }
}