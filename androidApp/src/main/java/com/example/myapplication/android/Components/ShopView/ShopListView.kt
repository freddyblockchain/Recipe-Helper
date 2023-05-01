package com.recipehelper.Components

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.myapplication.Models.RecipeIngredient
import com.recipehelper.getMainArea
import kotlinx.coroutines.launch

@Composable
fun ShopListView(recipeIngredients: MutableList<RecipeIngredient>, removeIngredientFunction: (RecipeIngredient) -> Unit) {
    val scrollState = rememberScrollState()
    val coroutine = rememberCoroutineScope()

    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation
    Box(modifier = Modifier.height(getMainArea().dp)) {
        LazyColumn(modifier = Modifier
            .scrollable(
                scrollState,
                orientation = Orientation.Vertical,
                enabled = true
            )
            .pointerInput(Unit) {
                detectVerticalDragGestures { _, dragAmount ->
                    coroutine.launch { scrollState.scrollBy(dragAmount) }
                }
            }) {
            items(recipeIngredients, key = {ingredient -> ingredient.ingredientName}) {
                ShopIngredientCard(ingredient = it, removeIngredientFunction)
            }
        }
    }
}