package com.example.myapplication.android.Components
import com.example.myapplication.Models.Recipe

import android.graphics.Color.parseColor
import android.graphics.fonts.FontFamily
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
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
import androidx.navigation.NavController
import com.example.myapplication.Models.Ingredient

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecipeCard(recipe: Recipe, onClick: (recipeName: String) -> Unit){
    CartBar(onClick = {onClick(recipe.name)}) {
        Text(
            recipe.name,
            modifier = Modifier.padding(start = 10.dp),
            fontSize = 25.sp,
            style = TextStyle(fontFamily = androidx.compose.ui.text.font.FontFamily.Serif),
        )
    }
}