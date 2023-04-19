package com.example.myapplication.android.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import com.example.myapplication.Models.Recipe

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecipeCard(
    recipe: Recipe,
    onClick: (recipeName: String) -> Unit,
    onDelete: (recipe: Recipe) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .clickable { onClick(recipe.name) }
            .padding(
                start = 5.dp,
                end = 5.dp,
                bottom = 10.dp
            )
            .fillMaxWidth()
            .height(60.dp)
            .background(Color(120, 170, 220), RectangleShape)
            .border(1.dp, Color.Black, RectangleShape),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            recipe.name,
            modifier = Modifier.padding(start = 10.dp),
            fontSize = 30.sp,
            style = TextStyle(fontFamily = androidx.compose.ui.text.font.FontFamily.Serif),
        )
        Icon(
            Icons.Outlined.Delete,
            contentDescription = "Ingredient Collected Button",
            modifier = Modifier
                .clickable { onDelete(recipe) }
                .padding(end = 10.dp)
                .size(30.dp),
            tint = Color.Red
        )
    }
}