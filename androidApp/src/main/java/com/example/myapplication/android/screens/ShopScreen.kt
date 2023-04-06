package com.example.myapplication.android.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.Models.Ingredient
import com.example.myapplication.Models.Recipe
import com.example.myapplication.Models.RecipeIngredient
import com.example.myapplication.android.Components.IngredientForm
import com.example.myapplication.android.Components.ShopListView
import com.example.myapplication.android.SQLite.DBHandler
import com.example.myapplication.android.SQLite.addIngredientToRecipe
import com.example.myapplication.android.SQLite.readRecipeIngredients

@Composable
fun ShopScreen(navController: NavController, recipeName: String?) {
    val db = DBHandler(LocalContext.current)
    val recipeIngredients = remember { mutableStateListOf<RecipeIngredient>() }

    LaunchedEffect(Unit) {
        val retrievedIngredients = db.readRecipeIngredients(Recipe(recipeName!!))
        recipeIngredients.addAll(retrievedIngredients)
    }
    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
    ) {
        Column() {
            Text(
                "Shopping List",
                textAlign = TextAlign.Center,
                fontSize = 40.sp,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )

            ShopListView(recipeIngredients, removeIngredientFunction(recipeIngredients))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
            ) {
                AddItemToCartButton(recipeIngredients = recipeIngredients)
                FinishShoppingButton(navController = navController)
            }

        }
    }
}

@Composable
fun AddItemToCartButton(
    recipeIngredients: MutableList<RecipeIngredient>
) {
    var openDialogState by remember { mutableStateOf(false) }
    IngredientForm(onSubmit = { recipeIngredient: RecipeIngredient ->
        recipeIngredients.add(recipeIngredient)
    }, showDialog = openDialogState) {
        openDialogState = false
    }
    Button(onClick = {
        openDialogState = true
    }) {
        Text(text = "Add Item")
    }
}

@Composable
fun FinishShoppingButton(navController: NavController) {
    Button(onClick = { navController.popBackStack() }) {
        Text(text = "Finish Shopping")
    }
}

fun removeIngredientFunction(recipeIngredients: MutableList<RecipeIngredient>): (RecipeIngredient) -> Unit{
    return { recipeIngredient ->
        recipeIngredients.remove(recipeIngredient)
    }
}
