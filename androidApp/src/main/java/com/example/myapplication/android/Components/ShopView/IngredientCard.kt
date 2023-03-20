package com.example.myapplication.android.Components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.Models.Ingredient

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun IngredientCard(ingredient: Ingredient){
        ShopCartBar() {
                Text(
                    ingredient.name,
                    modifier = Modifier.padding(start = 10.dp),
                    fontSize = 25.sp,
                    style = TextStyle(fontFamily = androidx.compose.ui.text.font.FontFamily.Serif),
                )
        }
}