package com.example.myapplication.android.Components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.Models.Ingredient
import com.example.myapplication.Models.Recipe

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecipesIngredientListView(recipe: Recipe, recipes: List<Ingredient>, onDelete: (ingredient: Ingredient, recipe: Recipe) -> Unit) {
    Box(modifier = Modifier.height(430.dp)) {
        LazyColumn(
            modifier = Modifier
        ) {
            items(recipes) {
                RecipeIngredientCard(recipe,ingredient = it, onDelete = onDelete)
            }
        }
    }
}