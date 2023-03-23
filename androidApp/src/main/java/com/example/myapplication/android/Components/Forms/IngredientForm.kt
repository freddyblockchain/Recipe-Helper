package com.example.myapplication.android.Components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    var suggestedIngredients = remember { mutableStateListOf("") }
    val allIngredients = getAllIngredientsFromDb().map { it.name }

    val ingredientValueChanged = { input: String ->
        if (input.length >= 1) {
            val suggestedIngredientsFound = allIngredients.filter {
                val commonPrefix = it.lowercase().commonPrefixWith(input.lowercase())
                commonPrefix.length == input.length
            }

            if (suggestedIngredientsFound.isNotEmpty()) {
                suggestedIngredients.clear()
                suggestedIngredients.addAll(suggestedIngredientsFound)
            } else {
                suggestedIngredients.clear()
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
                        BoxWithConstraints(modifier = Modifier
                            .fillMaxWidth(1f)
                            .height(120.dp)) {
                            Column() {

                                OutlinedTextField(
                                    value = inputName,
                                    onValueChange = ingredientValueChanged,
                                    label = { Text(text = "Name") }
                                )
                                Column() {
                                    if (inputName.isNotEmpty() && suggestedIngredients.isNotEmpty()) {
                                        SearchItemGrid(
                                            itemTexts = suggestedIngredients,
                                            onItemClick = { inputName = suggestedIngredients[it] })
                                    }
                                }
                            }

                        }
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
/*
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
}*/

@Composable
fun SearchItemGrid(itemTexts: List<String>, onItemClick: (Int) -> Unit) {
        for (i in 0..1) {
            Row{
                for (j in 0..1) {
                    val index = i * 2 + j
                    SearchItem(itemTexts = itemTexts, index = index, onItemClick = onItemClick)
                }
            }
        }
}

@Composable
fun SearchItem(itemTexts: List<String>, index: Int, onItemClick: (Int) -> Unit) {
    if (index in itemTexts.indices) {
        Text(
            text = itemTexts[index],
            fontSize = 18.sp,
            color = Color.Black,
            fontStyle = FontStyle.Italic,
            modifier = Modifier
                .clickable(onClick = { onItemClick(index) })
                .padding(end = 16.dp)
        )
    }
}