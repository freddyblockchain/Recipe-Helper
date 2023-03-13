package com.example.myapplication.android.Components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.myapplication.Models.Recipe

@Composable
fun RecipesListView(onClick: (recipeName: String) -> Unit, recipes: List<Recipe>){
    Column {
        recipes.forEach {
            RecipeCard(recipe = it, onClick = onClick)
        }
    }
}