package com.recipehelper.Components

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
import com.recipehelper.getMainArea

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecipesListView(onClick: (recipeName: String) -> Unit, recipes: List<Recipe>, onDelete: (recipe: Recipe) -> Unit) {
    Box(modifier = Modifier.height(getMainArea().dp)) {
        LazyColumn(
            modifier = Modifier
        ) {
            items(recipes) { recipe ->
                RecipeCard(recipe = recipe, onClick = onClick, onDelete = onDelete)
            }
        }
    }
}