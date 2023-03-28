package com.example.myapplication.Models

@kotlinx.serialization.Serializable
data class Ingredient(val name: String)

data class IngredientModel(val ingredientId: Int, val name: String)

@kotlinx.serialization.Serializable
data class Recipe(val name: String)

data class RecipeIngredient(val ingredientName: String,  val amount: Int?)
data class RecipeIngredientModel(val ingredientId: Int, val recipeId: Int, val amount: Int?)