package com.example.myapplication.android.Components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.myapplication.Models.Ingredient

@Composable
fun IngredientForm(onSubmit: (Ingredient) -> Unit, showDialog: Boolean, onDismiss: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    if (showDialog) {
        Dialog(
            onDismissRequest = onDismiss,
            content = {
                Surface(
                    modifier = Modifier
                        .width(280.dp)
                        .height(240.dp),
                    elevation = 24.dp
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(text = "Add an ingredient")
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text(text = "Name") }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(
                            value = amount,
                            onValueChange = { amount = it },
                            label = { Text(text = "Amount") }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { onSubmit(Ingredient(name, amount)); onDismiss()},
                            modifier = Modifier.fillMaxWidth(),
                            enabled = name.isNotBlank() && amount.isNotBlank()
                        ) {
                            Text(text = "Submit")
                        }
                    }
                }
            }
        )
    }
}