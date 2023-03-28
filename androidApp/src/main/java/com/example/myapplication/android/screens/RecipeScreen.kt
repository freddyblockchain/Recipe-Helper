package com.example.myapplication.android.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.example.myapplication.android.Components.RecipeIngredientListView
import com.example.myapplication.android.Navigation.NFTicketScreen
import com.example.myapplication.android.SQLite.DBHandler
import com.example.myapplication.android.SQLite.addIngredientToRecipe
import com.example.myapplication.android.SQLite.deleteRecipeIngredient
import com.example.myapplication.android.SQLite.readRecipeIngredients

@Composable
fun RecipeScreen(navController: NavController, recipeName: String?) {
    val db = DBHandler(LocalContext.current)
    val recipeIngredients = remember { mutableStateListOf<RecipeIngredient>() }

    LaunchedEffect(Unit) {
        recalculateIngredients(recipeIngredients, db, recipeName!!)
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column() {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Arrow Back",
                modifier = Modifier
                    .clickable {
                        navController.popBackStack()
                    }
                    .size(40.dp))
            Text(
                recipeName ?: "",
                textAlign = TextAlign.Center,
                fontSize = 40.sp,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )

            RecipeIngredientListView(Recipe(recipeName!!), recipeIngredients, onDelete = deleteRecipeIngredient(db, recipeIngredients))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                AddIngredientToRecipeButton(dbHandler = db, recipeName = recipeName) {
                    recalculateIngredients(recipeIngredients, db, recipeName)
                }
                StartShopviewButton(recipeName, navController)
            }

        }
    }
}

private fun recalculateIngredients(
    recipeIngredients: MutableList<RecipeIngredient>,
    db: DBHandler,
    recipeName: String
) {
    recipeIngredients.clear(); recipeIngredients.addAll(
        db.readRecipeIngredients(
            Recipe(
                recipeName
            )
        )
    )
}

@Composable
fun AddIngredientToRecipeButton(
    dbHandler: DBHandler,
    recipeName: String,
    onRecipeAdded: () -> Unit
) {
    var openDialogState by remember { mutableStateOf(false) }
    IngredientForm(onSubmit = { recipeIngredient: RecipeIngredient ->
        dbHandler.addIngredientToRecipe(
            recipeIngredient,
            Recipe(recipeName)
        );
        onRecipeAdded();
    }, showDialog = openDialogState) {
        openDialogState = false
    }
    Button(onClick = {
        openDialogState = true
    }) {
        Text(text = "Add Ingredient")
    }
}

@Composable
fun StartShopviewButton(recipeName: String, navController: NavController) {
    Button(onClick = {
        navController.navigate(NFTicketScreen.ShopScreen.route + "/$recipeName")
    }) {
        Text(text = "Start Shopping")
    }
}

fun deleteRecipeIngredient(dbHandler: DBHandler, recipeIngredients: MutableList<RecipeIngredient>): (RecipeIngredient, Recipe) -> Unit {
    return { recipeIngredient, recipe ->
        dbHandler.deleteRecipeIngredient(recipeIngredient, recipe)
        recalculateIngredients(recipeIngredients, dbHandler, recipe.name)
    }
}