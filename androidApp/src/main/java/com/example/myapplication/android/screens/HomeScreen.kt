package com.recipehelper.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.Models.Recipe
import com.recipehelper.Components.RecipeForm
import com.recipehelper.Components.RecipesListView
import com.recipehelper.Navigation.NFTicketScreen
import com.recipehelper.SQLite.DBHandler
import com.recipehelper.SQLite.addRecipe
import com.recipehelper.SQLite.deleteRecipe
import com.recipehelper.SQLite.readRecipes

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
        Column(Modifier.padding(top = 40.dp)) {
            Text(
                "Recipes",
                textAlign = TextAlign.Center,
                fontSize = 40.sp,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
            RecipesListView(onClick = navigateToRecipe(navController), recipes, onDelete = deleteRecipe(dbHandler = db, recipes))
            AddRecipeButton(db) { recalculateRecipes(recipes, db) }
        }
    }
}

fun recalculateRecipes(recipes: MutableList<Recipe>, db: DBHandler){
    recipes.clear();
    recipes.addAll(getRecipesFromDb(db))
}
@Composable
fun ColumnScope.AddRecipeButton(dbHandler: DBHandler,onRecipeAdded: () -> Unit){
    var openDialogState by remember { mutableStateOf(false) }
    RecipeForm(onSubmit = {recipe: Recipe -> dbHandler.addRecipe(recipe); onRecipeAdded()}, showDialog = openDialogState) {
        openDialogState = false
    }
    Button(onClick = {
        //navController.navigate(NFTicketScreen.OtherScreen.route + "/$customText")
        openDialogState = true
    }, modifier = Modifier.align(CenterHorizontally)) {
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

fun deleteRecipe(dbHandler: DBHandler, recipes: MutableList<Recipe>): (Recipe) -> Unit {
    return { recipe ->
        dbHandler.deleteRecipe(recipe)
        recalculateRecipes(recipes, dbHandler)
    }
}
