package com.example.myapplication.android.SQLite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myapplication.Models.Ingredient
import com.example.myapplication.Models.Recipe
import com.example.myapplication.Models.RecipeIngredient
import com.example.myapplication.android.SQLite.DBConstants.Companion.ID_COL
import com.example.myapplication.android.SQLite.DBConstants.Companion.INGREDIENT_TABLE
import com.example.myapplication.android.SQLite.DBConstants.Companion.NAME_COL
import com.example.myapplication.android.SQLite.DBConstants.Companion.RECIPE_INGREDIENT_TABLE
import com.example.myapplication.android.SQLite.DBConstants.Companion.RECIPE_TABLE

class DBHandler(context: Context?) :
    SQLiteOpenHelper(context, DBConstants.DB_NAME, null, DBConstants.DB_VERSION + 7) {
    // below method is for creating a database by running a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // on below line we are creating an sqlite query and we are
        // setting our column names along with their data types.
        val ingredientsQuery = ("CREATE TABLE " + INGREDIENT_TABLE + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " UNIQUE,"
                + DBConstants.AMOUNT_COL + " TEXT)")

        val recipesQuery = ("CREATE TABLE " + RECIPE_TABLE + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT UNIQUE)")

        val recipeIngredientQuery = ("CREATE TABLE $RECIPE_INGREDIENT_TABLE ("
                + "ingredient_id INTEGER,"
                + "recipe_id INTEGER,"
                + "FOREIGN KEY (ingredient_id) REFERENCES $INGREDIENT_TABLE(id),"
                + "FOREIGN KEY (recipe_id) REFERENCES $RECIPE_TABLE(id))")

        // at last we are calling a exec sql method to execute above sql query
        println("query is: $ingredientsQuery")
        db.execSQL(ingredientsQuery)
        db.execSQL(recipesQuery)
        db.execSQL(recipeIngredientQuery)
    }

    private fun addIngredient(
        ingredient: Ingredient
    ) {
        val db = this.writableDatabase
        val ingredients = readIngredients()
        val values = ContentValues()
        if (ingredient !in ingredients) {
            values.put(NAME_COL, ingredient.name)
            values.put(DBConstants.AMOUNT_COL, ingredient.amount)
            db.insert(INGREDIENT_TABLE, null, values)
        }
        db.close()
    }

    fun addIngredientToRecipe(ingredient: Ingredient, recipe: Recipe) {
        addIngredient(ingredient)

        val db = this.writableDatabase
        val ingredientId = getIdFromName(INGREDIENT_TABLE, ingredient.name)!!
        val recipeId = getIdFromName(RECIPE_TABLE, recipe.name)!!
        val recipeIngredient = readRecipeIngredient(ingredientId = ingredientId, recipeId = recipeId)
        val values = ContentValues()
        if (recipeIngredient == null) {
            values.put("ingredient_id", ingredientId)
            values.put("recipe_id", recipeId)
            db.insert(RECIPE_INGREDIENT_TABLE, null, values)
        }
        db.close()
    }

    fun addRecipe(recipe: Recipe) {
        val db = this.writableDatabase
        val recipes = readRecipes()
        if (recipe !in recipes) {
            val values = ContentValues()
            values.put(NAME_COL, recipe.name)
            db.insert(RECIPE_TABLE, null, values)
        }
        db.close()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS $INGREDIENT_TABLE")
        db.execSQL("DROP TABLE IF EXISTS $RECIPE_TABLE")
        db.execSQL("DROP TABLE IF EXISTS $RECIPE_INGREDIENT_TABLE")
        onCreate(db)
    }

    fun readIngredients(): ArrayList<Ingredient> {
        val db = this.readableDatabase
        val ingredientCursor: Cursor = db.rawQuery("SELECT * FROM $INGREDIENT_TABLE", null)

        val result = getDatabaseDataFromCursor({
            Ingredient(
                it.getString(1),
                it.getString(2),
            )
        }, ingredientCursor)
        return result
    }

    fun readRecipes(): ArrayList<Recipe> {
        val db = this.readableDatabase
        val recipeCursor: Cursor = db.rawQuery("SELECT * FROM $RECIPE_TABLE", null)

        val result = getDatabaseDataFromCursor({
            Recipe(
                it.getString(1),
            )
        }, recipeCursor)


        return result
    }

    private fun readRecipeIngredient(ingredientId: Int, recipeId: Int): RecipeIngredient?{
        val db = this.readableDatabase
        val recipeIngredientCursor: Cursor = db.rawQuery(
            "SELECT * FROM $RECIPE_INGREDIENT_TABLE WHERE (ingredient_id = ? AND recipe_id = ?)",
            arrayOf(ingredientId.toString(), recipeId.toString())
        )

        val recipeIngredients = getDatabaseDataFromCursor({
            RecipeIngredient(
                it.getInt(0), it.getInt(1)
            )
        }, recipeIngredientCursor)

        return recipeIngredients.firstOrNull()
    }

    fun readRecipeIngredients(recipe: Recipe): ArrayList<Ingredient> {
        val db = this.readableDatabase
        val recipeId = getIdFromName(RECIPE_TABLE, recipe.name)!!
        val recipeIngredientCursor: Cursor = db.rawQuery(
            "SELECT * FROM $RECIPE_INGREDIENT_TABLE WHERE recipe_id = ?",
            arrayOf(recipeId.toString())
        )

        val recipeIngredients = getDatabaseDataFromCursor({
            RecipeIngredient(
                it.getInt(0), it.getInt(1)
            )
        }, recipeIngredientCursor)

        val ingredientIds = recipeIngredients.map { it.ingredientId }
        val placeholders = ingredientIds.joinToString(",", transform = { "?" })
        val selectionArgs = ingredientIds.map { it.toString() }.toTypedArray()
        val ingredientCursor: Cursor = db.rawQuery(
            "SELECT * FROM $INGREDIENT_TABLE WHERE $ID_COL IN ($placeholders)",
            selectionArgs
        )

        val ingredients = getDatabaseDataFromCursor(
            {
                Ingredient(
                    it.getString(1), it.getString(2)
                )
            },
            ingredientCursor
        )
        return ingredients
    }

    fun getIdFromName(tableName: String, name: String): Int? {
        val db = this.readableDatabase
        val query = "SELECT id FROM $tableName WHERE $NAME_COL = ?"
        val selectionArgs = arrayOf(name)
        val cursor = db.rawQuery(query, selectionArgs)

        var id: Int? = null
        if (cursor.moveToFirst()) {
            id = cursor.getInt(0)
        }
        cursor.close()
        return id
    }
}

fun <T> getDatabaseDataFromCursor(creationFunction: (Cursor) -> T, cursor: Cursor): ArrayList<T> {
    val dataList: ArrayList<T> = ArrayList()

    if (cursor.moveToFirst()) {
        do {
            dataList.add(
                creationFunction(cursor)
            )
        } while (cursor.moveToNext())
    }
    cursor.close()

    return dataList
}
