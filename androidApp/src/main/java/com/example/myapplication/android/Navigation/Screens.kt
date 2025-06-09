package com.recipehelper.Navigation

sealed class Screens(val route: String) {
    object HomeScreen : Screens("home_screen")
    object RecipeScreen : Screens("recipe_screen")
    object ShopScreen : Screens("shop_screen")
}