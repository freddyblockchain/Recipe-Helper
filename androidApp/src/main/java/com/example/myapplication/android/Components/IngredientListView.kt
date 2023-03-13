package com.example.myapplication.android.Components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.Models.Ingredient
import com.example.myapplication.android.SQLite.DBHandler

@Composable
fun IngredientListView(ingredients: List<Ingredient>){
    Column() {
        ingredients.forEach {
            IngredientCard(ingredient = it)
        }
    }
}

@Composable
fun getIngredientsFromDb() :List<Ingredient>{
    val dbHandler = DBHandler(LocalContext.current);
    return dbHandler.readIngredients()
}