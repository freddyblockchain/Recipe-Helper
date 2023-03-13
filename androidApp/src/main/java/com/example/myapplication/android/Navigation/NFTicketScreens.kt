package com.example.myapplication.android.Navigation

sealed class NFTicketScreen(val route: String) {
    object HomeScreen : NFTicketScreen("home_screen")
    object RecipeScreen : NFTicketScreen("recipe_screen")
}