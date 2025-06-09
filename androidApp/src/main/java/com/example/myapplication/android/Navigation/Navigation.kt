package com.recipehelper.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.recipehelper.screens.HomeScreen
import com.recipehelper.screens.RecipeScreen
import com.recipehelper.screens.ShopScreen

@Composable
fun Navigation(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.HomeScreen.route){
        composable(route = Screens.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
        composable(route = Screens.RecipeScreen.route + "/{recipeName}",
            arguments = listOf(navArgument(name = "recipeName") {
            type = NavType.StringType
        })) { entry ->
            RecipeScreen(
                navController = navController,
                entry.arguments?.getString("recipeName")
            )
        }
        composable(route = Screens.ShopScreen.route + "/{recipeName}",
            arguments = listOf(navArgument(name = "recipeName") {
                type = NavType.StringType
            })) { entry ->
            ShopScreen(
                navController = navController,
                entry.arguments?.getString("recipeName")
            )
        }
    }
}