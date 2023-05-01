package com.recipehelper.SQLite
import com.example.myapplication.Models.RecipeIngredient
import android.database.Cursor
import com.example.myapplication.Models.*

fun DBHandler.readIngredient(ingredientId: Int): Ingredient? {
    val db = this.readableDatabase
    val ingredientCursor: Cursor = db.rawQuery(
        "SELECT * FROM ${DBConstants.INGREDIENT_TABLE} WHERE (ingredient_id = ?)",
        arrayOf(ingredientId.toString())
    )

    val result = getDatabaseDataFromCursor({
        Ingredient(
            it.getString(1),
        )
    }, ingredientCursor)
    return result.firstOrNull()
}

fun DBHandler.readIngredients(): ArrayList<Ingredient> {
    val db = this.readableDatabase
    val ingredientCursor: Cursor = db.rawQuery("SELECT * FROM ${DBConstants.INGREDIENT_TABLE}", null)

    val result = getDatabaseDataFromCursor({
        Ingredient(
            it.getString(1),
        )
    }, ingredientCursor)
    return result
}

fun DBHandler.readRecipes(): ArrayList<Recipe> {
    val db = this.readableDatabase
    val recipeCursor: Cursor = db.rawQuery("SELECT * FROM ${DBConstants.RECIPE_TABLE}", null)

    val result = getDatabaseDataFromCursor({
        Recipe(
            it.getString(1),
        )
    }, recipeCursor)


    return result
}

fun DBHandler.doesRecipeIngredientExist(ingredientId: Int, recipeId: Int): Boolean{
    val db = this.readableDatabase
    val recipeIngredientCursor: Cursor = db.rawQuery(
        "SELECT * FROM ${DBConstants.RECIPE_INGREDIENT_TABLE} WHERE (ingredient_id = ? AND recipe_id = ?)",
        arrayOf(ingredientId.toString(), recipeId.toString())
    )
    val recipeIngredientEntry = getDatabaseDataFromCursor({
        Pair(it.getInt(0), it.getInt(1))
    }, recipeIngredientCursor)

    return recipeIngredientEntry.isNotEmpty()
}

fun DBHandler.readRecipeIngredients(recipe: Recipe): ArrayList<RecipeIngredient> {
    val db = this.readableDatabase
    val recipeId = getIdFromName(DBConstants.RECIPE_TABLE, recipe.name)!!
    val recipeIngredientsQuery = """
    SELECT ${DBConstants.INGREDIENT_TABLE}.${DBConstants.NAME_COL}, ${DBConstants.RECIPE_INGREDIENT_TABLE}.${DBConstants.AMOUNT_COL}
    FROM ${DBConstants.RECIPE_INGREDIENT_TABLE}
    JOIN ${DBConstants.INGREDIENT_TABLE} ON ${DBConstants.RECIPE_INGREDIENT_TABLE}.ingredient_id = ${DBConstants.INGREDIENT_TABLE}.${DBConstants.ID_COL}
    WHERE ${DBConstants.RECIPE_INGREDIENT_TABLE}.recipe_id = ?
"""
    val recipeIngredientsCursor = db.rawQuery(recipeIngredientsQuery, arrayOf(recipeId.toString()))
    val recipeIngredients = getDatabaseDataFromCursor({
        RecipeIngredient(it.getString(0), it.getInt(1))
    }, recipeIngredientsCursor)
    return recipeIngredients
}

fun DBHandler.getIdFromName(tableName: String, name: String): Int? {
    val db = this.readableDatabase
    val query = "SELECT id FROM $tableName WHERE ${DBConstants.NAME_COL} = ?"
    val selectionArgs = arrayOf(name)
    val cursor = db.rawQuery(query, selectionArgs)

    var id: Int? = null
    if (cursor.moveToFirst()) {
        id = cursor.getInt(0)
    }
    cursor.close()
    return id
}