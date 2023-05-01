package com.recipehelper.SQLite

class DBConstants {
    companion object {
        // creating a constant variables for our database.
        // below variable is for our database name.
        const val DB_NAME = "AppDb"

        // below int is our database version
        const val DB_VERSION = 1

        // below variable is for our table name.
        const val INGREDIENT_TABLE = "Ingredients"

        const val RECIPE_TABLE = "Recipes"

        const val RECIPE_INGREDIENT_TABLE = "RecipeIngredient"

        // below variable is for our id column.
        const val ID_COL = "id"

        // below variable is for our course name column
        const val NAME_COL = "name"

        // below variable id for our course duration column.
        const val AMOUNT_COL = "amount"
    }
}