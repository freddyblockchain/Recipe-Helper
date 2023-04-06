package com.example.myapplication.android.Components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.gestures.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp
import com.example.myapplication.Models.RecipeIngredient
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShopIngredientCard(ingredient: RecipeIngredient, onSwipe: (ingredient: RecipeIngredient) -> Unit) {
    val cardHeight = 70.dp
    val swipeableState = rememberSwipeableState(0)
    val maxWidth =
        with(LocalDensity.current) { (LocalConfiguration.current.screenWidthDp.dp).toPx() }
    val anchors = mapOf(0f to 0, maxWidth to 1) // Maps anchor points (in px) to states
    val scope = rememberCoroutineScope()


    DisposableEffect(swipeableState.currentValue) {
        onDispose {
            if(swipeableState.currentValue == 1){
                onSwipe(ingredient)
            }
        }
    }


    Box(
        modifier = Modifier
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                orientation = Orientation.Horizontal,
            )
            .offset() { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp, top = 16.dp)
                .height(cardHeight)
                .border(2.dp, Color.Black),

            elevation = 10.dp,
            backgroundColor = Color.LightGray,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(
                    ingredient.ingredientName,
                    modifier = Modifier.padding(start = 10.dp),
                    fontSize = 25.sp,
                    style = TextStyle(fontFamily = FontFamily.Serif),
                )
                Icon(
                    Icons.Outlined.Check,
                    contentDescription = "Ingredient Collected Button",
                    modifier = Modifier
                        .fillMaxHeight(0.8f)
                        .size(50.dp)
                        .clickable { onSwipe(ingredient) }
                        .padding(end = 10.dp),
                    tint = Color(android.graphics.Color.parseColor("#006400")),
                )
            }
        }
    }
}