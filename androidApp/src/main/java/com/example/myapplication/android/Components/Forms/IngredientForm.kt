package com.example.myapplication.android.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.myapplication.Models.Ingredient
import com.example.myapplication.android.SQLite.DBHandler

@Composable
fun IngredientForm(onSubmit: (Ingredient) -> Unit, showDialog: Boolean, onDismiss: () -> Unit) {
    var inputName by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("1") }
    var suggestedIngredient by remember { mutableStateOf("") }
    val allIngredients = getAllIngredientsFromDb().map { it.name }

    val ingredientValueChanged = { input: String ->
        if (input.length >= 1) {
            val firstFoundIngredient = allIngredients.firstOrNull {
                val commonPrefix = it.lowercase().commonPrefixWith(input.lowercase())
                commonPrefix.length == input.length
            }

            if (firstFoundIngredient != null) {
                suggestedIngredient = firstFoundIngredient
            } else {
                suggestedIngredient = ""
            }
        }
        inputName = input
    }
    if (showDialog) {
        Dialog(
            onDismissRequest = onDismiss,
            content = {
                Surface(
                    elevation = 24.dp
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(text = "Add an ingredient")
                        BoxWithConstraints(modifier = Modifier.fillMaxWidth(1f).height(88.dp)) {
                            Spacer(modifier = Modifier.height(32.dp))
                            OutlinedTextField(
                                value = inputName,
                                onValueChange = ingredientValueChanged,
                                label = { Text(text = "Name") },
                                modifier = Modifier.align(Alignment.TopCenter)
                            )
                            if (inputName.isNotEmpty() && suggestedIngredient.isNotEmpty()) {
                                SearchItem(
                                    ItemText = suggestedIngredient,
                                    onItemClick = { inputName = suggestedIngredient })
                            }

                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(
                            value = amount,
                            onValueChange = { amount = it },
                            label = { Text(text = "Amount") }
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Button(
                            onClick = { onSubmit(Ingredient(inputName, amount)); onDismiss() },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = inputName.isNotBlank()
                        ) {
                            Text(text = "Submit")
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun getAllIngredientsFromDb(): List<Ingredient> {
    val dbHandler = DBHandler(LocalContext.current);
    return dbHandler.readIngredients()
}

@Composable
fun BoxScope.SearchItem(ItemText: String, onItemClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .clickable(onClick = { onItemClick(ItemText) })
            .align(Alignment.BottomStart)
            .padding(start = 8.dp)
    ) {
        Text(text = ItemText, fontSize = 22.sp, color = Color.Black, fontStyle = FontStyle.Italic)
    }
}