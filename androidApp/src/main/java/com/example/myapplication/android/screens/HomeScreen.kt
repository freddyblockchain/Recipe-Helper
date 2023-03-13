package com.example.myapplication.android.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.myapplication.Models.Recipe
import com.example.myapplication.android.Components.RecipeForm
import com.example.myapplication.android.Components.RecipesListView
import com.example.myapplication.android.Navigation.NFTicketScreen
import com.example.myapplication.android.SQLite.DBHandler

@Composable
fun HomeScreen(navController: NavController){
    val recipes = remember { mutableStateListOf<Recipe>() }
    val db = DBHandler(LocalContext.current)

    LaunchedEffect(Unit) {
        val retrievedRecipes = db.readRecipes()
        recipes.addAll(retrievedRecipes)
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column {
            RecipesListView(onClick = navigateToRecipe(navController), recipes)
            AddRecipeButton(db) { recipes.clear(); recipes.addAll(getRecipesFromDb(db)) }
        }
    }
}


@Composable
fun ArtistCard(text: String) {
    Column {
        Text(text)
    }
}
@Preview
@Composable
fun ArtistPreview(){
    val helloText = "helloText"
    ArtistCard(text = helloText)
}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}
@Composable
fun AddRecipeButton(dbHandler: DBHandler,onRecipeAdded: () -> Unit){
    var openDialogState by remember { mutableStateOf(false) }
    RecipeForm(onSubmit = {recipe: Recipe -> dbHandler.addRecipe(recipe); onRecipeAdded()}, showDialog = openDialogState) {
        openDialogState = false
    }
    Button(onClick = {
        //navController.navigate(NFTicketScreen.OtherScreen.route + "/$customText")
        openDialogState = true
    }) {
        Text(text = "Add Recipe")
    }
}
fun getRecipesFromDb(dbHandler: DBHandler) :List<Recipe>{
    return dbHandler.readRecipes()
}

fun navigateToRecipe(navController: NavController): (String) -> Unit {
    return { recipeName ->
        navController.navigate("${NFTicketScreen.RecipeScreen.route}/${recipeName}")
    }
}
