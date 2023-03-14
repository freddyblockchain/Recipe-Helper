package com.example.myapplication.android.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.Models.Ingredient
import com.example.myapplication.Models.Recipe
import com.example.myapplication.android.Components.IngredientForm
import com.example.myapplication.android.Components.IngredientListView
import com.example.myapplication.android.Components.RecipesListView
import com.example.myapplication.android.SQLite.DBHandler

@Composable
fun ShopScreen(navController: NavController, recipeName: String?) {
    val db = DBHandler(LocalContext.current)
    val ingredients = remember { mutableStateListOf<Ingredient>() }

    LaunchedEffect(Unit) {
        val retrievedIngredients = db.readRecipeIngredients(Recipe(recipeName!!))
        ingredients.addAll(retrievedIngredients)
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

            IngredientListView(ingredients)
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
            ) {
                AddItemToCartButton(dbHandler = db, recipeName = recipeName!!) {
                    ingredients.clear(); ingredients.addAll(
                    db.readRecipeIngredients(
                        Recipe(
                            recipeName
                        )
                    )
                )
                }
                FinishShoppingButton(navController = navController)
            }

        }
    }
}

@Composable
fun AddItemToCartButton(
    dbHandler: DBHandler, recipeName: String, onRecipeAdded: () -> Unit
) {
    /*var openDialogState by remember { mutableStateOf(false) }
    IngredientForm(onSubmit = { ingredient: Ingredient ->
        dbHandler.addIngredientToRecipe(
            ingredient,
            Recipe(recipeName)
        ); onRecipeAdded()
    }, showDialog = openDialogState) {
        openDialogState = false
    }
    Button(onClick = {
        //navController.navigate(NFTicketScreen.OtherScreen.route + "/$customText")
        openDialogState = true
    }) {
        Text(text = "Add Ingredient")
    }*/
    Button(onClick = {}) {
        Text(text = "Add Item")
    }
}

@Composable
fun FinishShoppingButton(navController: NavController) {
    Button(onClick = { navController.popBackStack() }) {
        Text(text = "Finish Shopping")
    }
}
