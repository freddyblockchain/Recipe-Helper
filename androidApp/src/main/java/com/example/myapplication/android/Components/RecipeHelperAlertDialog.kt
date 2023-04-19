import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RecipeHelperAlertDialog(
    confirmText: String,
    dismissText: String,
    dialogTitle: String,
    dialogText: String,
    dialogState: MutableState<Boolean>,
    dialogAction: () -> Unit
) {

    val lessIntenseRed = Color(0xFFD32F2F)

    if (dialogState.value) {

        AlertDialog(
            onDismissRequest = {
                dialogState.value = false
            },
            title = {
                Text(dialogTitle)
            },
            text = {
                Text(dialogText)
            },
            buttons = {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            dialogState.value = false
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray)) {
                        Text(dismissText)
                    }
                    Spacer(Modifier.width(30.dp))
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            dialogState.value = false
                            dialogAction()
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = lessIntenseRed),
                        ) {
                        Text(confirmText)
                    }
                }
            }
        )
    }
}