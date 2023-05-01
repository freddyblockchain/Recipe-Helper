package com.recipehelper.SQLite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myapplication.Models.Ingredient
import com.example.myapplication.Models.Recipe
import com.example.myapplication.Models.RecipeIngredient
import com.recipehelper.SQLite.DBConstants.Companion.ID_COL
import com.recipehelper.SQLite.DBConstants.Companion.INGREDIENT_TABLE
import com.recipehelper.SQLite.DBConstants.Companion.NAME_COL
import com.recipehelper.SQLite.DBConstants.Companion.RECIPE_INGREDIENT_TABLE
import com.recipehelper.SQLite.DBConstants.Companion.RECIPE_TABLE

class DBHandler(context: Context?) :
    SQLiteOpenHelper(context, DBConstants.DB_NAME, null, DBConstants.DB_VERSION + 14) {
    // below method is for creating a database by running a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // on below line we are creating an sqlite query and we are
        // setting our column names along with their data types.
        val ingredientsQuery = ("CREATE TABLE " + INGREDIENT_TABLE + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT UNIQUE)")

        val recipesQuery = ("CREATE TABLE " + RECIPE_TABLE + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT UNIQUE)")

        val recipeIngredientQuery = ("CREATE TABLE $RECIPE_INGREDIENT_TABLE ("
                + "ingredient_id INTEGER,"
                + "recipe_id INTEGER,"
                + DBConstants.AMOUNT_COL + " Integer,"
                + "FOREIGN KEY (ingredient_id) REFERENCES $INGREDIENT_TABLE(id),"
                + "FOREIGN KEY (recipe_id) REFERENCES $RECIPE_TABLE(id))")

        // at last we are calling a exec sql method to execute above sql query
        println("query is: $ingredientsQuery")
        db.execSQL(ingredientsQuery)
        db.execSQL(recipesQuery)
        db.execSQL(recipeIngredientQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS $INGREDIENT_TABLE")
        db.execSQL("DROP TABLE IF EXISTS $RECIPE_TABLE")
        db.execSQL("DROP TABLE IF EXISTS $RECIPE_INGREDIENT_TABLE")
        onCreate(db)
    }

    fun <T> getDatabaseDataFromCursor(
        creationFunction: (Cursor) -> T,
        cursor: Cursor
    ): ArrayList<T> {
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
}

