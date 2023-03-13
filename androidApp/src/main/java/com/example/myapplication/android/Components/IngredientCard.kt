package com.example.myapplication.android.Components

import android.graphics.Color.parseColor
import android.graphics.fonts.FontFamily
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.Models.Ingredient

@Composable
fun IngredientCard(ingredient: Ingredient){
        CartBar {
                Text(
                    ingredient.name,
                    modifier = Modifier.padding(start = 10.dp),
                    fontSize = 25.sp,
                    style = TextStyle(fontFamily = androidx.compose.ui.text.font.FontFamily.Serif),
                )
                Icon(
                    Icons.Outlined.Check,
                    contentDescription = "Ingredient Collected Button",
                    modifier = Modifier
                        .fillMaxHeight(0.8f)
                        .size(50.dp)
                        .clickable { }
                        .padding(end = 10.dp),
                    tint = Color(parseColor("#006400")),
                )
        }
}