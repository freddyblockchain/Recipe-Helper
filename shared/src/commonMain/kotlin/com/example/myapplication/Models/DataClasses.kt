package com.example.myapplication.Models

@kotlinx.serialization.Serializable
data class Ingredient(val name: String, val amount: String? = null)

@kotlinx.serialization.Serializable
data class Recipe(val name: String)

data class RecipeIngredient(val ingredientId: Int, val recipeId: Int)