package com.example.myapplication.android.Components
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.Models.Ingredient

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecipeIngredientCard(
    recipe: Recipe,
    ingredient: Ingredient,
    onDelete: (ingredient: Ingredient, recipe: Recipe) -> Unit
) {
    Row(horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().height(90.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Text(
            ingredient.name,
            modifier = Modifier.padding(start = 10.dp),
            fontSize = 30.sp,
            style = TextStyle(fontFamily = androidx.compose.ui.text.font.FontFamily.Serif),
        )

        Text(
            ingredient.amount ?: "",
            modifier = Modifier.padding(start = 10.dp),
            fontSize = 30.sp,
            style = TextStyle(fontFamily = androidx.compose.ui.text.font.FontFamily.Serif),
        )

        Icon(
            Icons.Outlined.Delete,
            contentDescription = "Ingredient Collected Button",
            modifier = Modifier
                .clickable { onDelete(ingredient, recipe) }
                .padding(end = 10.dp)
                .size(30.dp),
            tint = Color.Red
        )
    }
}