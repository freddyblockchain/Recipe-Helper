package com.example.myapplication.android.SQLite

import android.content.ContentValues
import com.example.myapplication.Models.Ingredient
import com.example.myapplication.Models.Recipe
import com.example.myapplication.Models.RecipeIngredient

fun DBHandler.deleteRecipe(recipe: Recipe) {
    val db = this.writableDatabase
    val recipeId = getIdFromName(DBConstants.RECIPE_TABLE, recipe.name) ?: return
    db.delete(DBConstants.RECIPE_TABLE, "${DBConstants.ID_COL} = ?", arrayOf(recipeId.toString()))
    db.delete(DBConstants.RECIPE_INGREDIENT_TABLE, "recipe_id = ?", arrayOf(recipeId.toString()))
    db.close()
}

fun DBHandler.deleteRecipeIngredient(recipeIngredient: RecipeIngredient, recipe: Recipe) {
    val db = this.writableDatabase
    val recipeId = getIdFromName(DBConstants.RECIPE_TABLE, recipe.name) ?: return
    val ingredientId = getIdFromName(DBConstants.INGREDIENT_TABLE, recipeIngredient.ingredientName) ?: return
    db.delete(
        DBConstants.RECIPE_INGREDIENT_TABLE,
        "recipe_id = ? AND ingredient_id = ?",
        arrayOf(recipeId.toString(), ingredientId.toString()))
    db.close()
}

private fun DBHandler.addIngredient(
    ingredient: Ingredient
) {
    val db = this.writableDatabase
    val ingredients = readIngredients()
    val values = ContentValues()
    if (ingredient !in ingredients) {
        values.put(DBConstants.NAME_COL, ingredient.name)
        db.insert(DBConstants.INGREDIENT_TABLE, null, values)
    }
    db.close()
}

fun DBHandler.addIngredientToRecipe(recipeIngredient: RecipeIngredient, recipe: Recipe) {
    addIngredient(Ingredient(recipeIngredient.ingredientName))

    val db = this.writableDatabase
    val ingredientId = getIdFromName(DBConstants.INGREDIENT_TABLE, recipeIngredient.ingredientName)!!
    val recipeId = getIdFromName(DBConstants.RECIPE_TABLE, recipe.name)!!
    val values = ContentValues()
    if (!doesRecipeIngredientExist(ingredientId = ingredientId, recipeId = recipeId)) {
        values.put("ingredient_id", ingredientId)
        values.put("recipe_id", recipeId)
        values.put("amount", recipeIngredient.amount)
        db.insert(DBConstants.RECIPE_INGREDIENT_TABLE, null, values)
    }
    db.close()
}

fun DBHandler.addRecipe(recipe: Recipe) {
    val db = this.writableDatabase
    val recipes = readRecipes()
    if (recipe !in recipes) {
        val values = ContentValues()
        values.put(DBConstants.NAME_COL, recipe.name)
        db.insert(DBConstants.RECIPE_TABLE, null, values)
    }
    db.close()
}