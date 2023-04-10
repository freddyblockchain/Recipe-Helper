import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import com.example.myapplication.Models.RecipeIngredient

val RecipeIngredientSaver: Saver<MutableList<RecipeIngredient>, List<Map<String, Any?>>> = Saver(
    save = { recipeIngredientList ->
        val res = recipeIngredientList.map { recipeIngredient ->
            mapOf(
                "ingredientName" to recipeIngredient.ingredientName,
                "amount" to recipeIngredient.amount,
                "amountIsNull" to (recipeIngredient.amount == null)
            )
        }
        res
    },
    restore = { mapDataList ->
        val recipeIngredients = mapDataList.map {
            val ingredientName = it["ingredientName"] as String
            val amountIsNull = it["amountIsNull"] as Boolean
            val amount = if (amountIsNull) null else it["amount"] as Int
            RecipeIngredient(ingredientName, amount)
        }
        val stateList = mutableStateListOf<RecipeIngredient>()
        stateList.addAll(recipeIngredients)
        stateList
    }
)