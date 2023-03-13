package com.example.myapplication.android.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.Models.Ingredient
import com.example.myapplication.Models.Recipe
import com.example.myapplication.android.Components.IngredientForm
import com.example.myapplication.android.Components.IngredientListView
import com.example.myapplication.android.Components.RecipesListView
import com.example.myapplication.android.SQLite.DBHandler

@Composable
fun RecipeScreen(navController: NavController, recipeName: String?){
    val db = DBHandler(LocalContext.current)
    val ingredients = remember { mutableStateListOf<Ingredient>() }

    LaunchedEffect(Unit) {
        val retrievedIngredients = db.readRecipeIngredients(Recipe(recipeName!!))
        ingredients.addAll(retrievedIngredients)
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Arrow Back",
                modifier = Modifier
                    .clickable {
                        navController.popBackStack()
                    }
                    .size(40.dp))
            ArtistCard("RecipeScreen")
            Text(recipeName ?: "")

            IngredientListView(ingredients)

            AddIngredientToRecipeButton(dbHandler = db, recipeName = recipeName!!) {
                ingredients.clear(); ingredients.addAll(db.readRecipeIngredients(Recipe(recipeName)))
            }
        }
    }
}

@Composable
fun AddIngredientToRecipeButton(dbHandler: DBHandler, recipeName: String, onRecipeAdded: () -> Unit){
    var openDialogState by remember { mutableStateOf(false) }
    IngredientForm(onSubmit = {ingredient: Ingredient -> dbHandler.addIngredientToRecipe(ingredient, Recipe(recipeName)); onRecipeAdded()}, showDialog = openDialogState) {
        openDialogState = false
    }
    Button(onClick = {
        //navController.navigate(NFTicketScreen.OtherScreen.route + "/$customText")
        openDialogState = true
    }) {
        Text(text = "Add Ingredient")
    }
}