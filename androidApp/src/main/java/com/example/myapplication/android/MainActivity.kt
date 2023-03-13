package com.example.myapplication.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.Greeting
import com.example.myapplication.Models.Ingredient
import com.example.myapplication.android.Navigation.Navigation
import com.example.myapplication.android.SQLite.DBHandler
import com.example.myapplication.android.screens.GreetingView
import com.example.myapplication.android.screens.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                setUpDatabase()
                Navigation()
            }
        }
    }

    @Preview
    @Composable
    fun DefaultPreview() {
        MyApplicationTheme {
            Navigation()
        }
    }

    @Composable
    fun setUpDatabase(){
        val db = DBHandler(LocalContext.current)
        //db.addIngredient(Ingredient("finger"))
    }
}
