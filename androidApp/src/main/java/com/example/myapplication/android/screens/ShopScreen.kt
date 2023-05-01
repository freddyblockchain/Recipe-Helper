package com.recipehelper.screens

import RecipeIngredientSaver
import com.example.myapplication.Models.RecipeIngredient
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.Models.Recipe
import com.recipehelper.Components.IngredientForm
import com.recipehelper.Components.ShopListView
import com.recipehelper.SQLite.DBHandler
import com.recipehelper.SQLite.readRecipeIngredients

@Composable
fun ShopScreen(navController: NavController, recipeName: String?) {
    val db = DBHandler(LocalContext.current)
    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation
    val recipeIngredients = rememberSaveable(saver = RecipeIngredientSaver) {
        val retrievedIngredients = db.readRecipeIngredients(Recipe(recipeName!!))
        val stateList = mutableStateListOf<RecipeIngredient>()
        stateList.addAll(retrievedIngredients)
        stateList
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
        val ingredientNames = recipeIngredients.map { it.ingredientName }
        if (recipeIngredient.ingredientName !in ingredientNames) {
            recipeIngredients.add(recipeIngredient)
        }
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

fun removeIngredientFunction(recipeIngredients: MutableList<RecipeIngredient>): (RecipeIngredient) -> Unit {
    return { recipeIngredient ->
        recipeIngredients.remove(recipeIngredient)
    }
}
