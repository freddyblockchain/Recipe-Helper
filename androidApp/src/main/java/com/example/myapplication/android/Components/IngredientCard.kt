package com.example.myapplication.android.Components

import android.graphics.Color.parseColor
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.Models.Ingredient
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun IngredientCard(ingredient: Ingredient){
        CartBar() {
                Text(
                    ingredient.name,
                    modifier = Modifier.padding(start = 10.dp),
                    fontSize = 25.sp,
                    style = TextStyle(fontFamily = androidx.compose.ui.text.font.FontFamily.Serif),
                )
        }
}