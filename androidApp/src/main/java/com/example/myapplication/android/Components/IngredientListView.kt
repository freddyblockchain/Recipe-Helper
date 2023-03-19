package com.example.myapplication.android.Components

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.Models.Ingredient
import com.example.myapplication.android.SQLite.DBHandler
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun IngredientListView(ingredients: List<Ingredient>) {
    val scrollState = rememberScrollState()
    val coroutine = rememberCoroutineScope()
    Box(modifier = Modifier.height(430.dp)) {
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
            items(ingredients) {
                IngredientCard(ingredient = it)
            }
        }
    }
}