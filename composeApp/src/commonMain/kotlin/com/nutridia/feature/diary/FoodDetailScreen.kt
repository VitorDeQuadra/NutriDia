// ORIGEM: CODEX | Data de criacao: 2026-10-20
package com.nutridia.feature.diary

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nutridia.core.components.*
import com.nutridia.data.*
import com.nutridia.navigation.Destination

@Composable
fun FoodDetailScreen(state: AppState) {
    val food = state.selectedFood
    var quantity by remember { mutableStateOf("150") }
    var unit by remember { mutableStateOf("gramas") }
    val grams = quantity.replace(',', '.').toDoubleOrNull()?.times(if (unit == "porções de 100 g") 100 else 1)
    val valid = grams != null && grams.isFinite() && grams > 0 && grams <= 10000
    val item = MealItem(food, if (valid) grams!! else 0.0)
    Screen(food.name, state::back, action = { IconAction(if (food.id in state.favorites) "★" else "☆", "Favoritar alimento") { state.toggleFavorite(food.id) } },
        bottom = { Box(Modifier.padding(16.dp)) { PrimaryButton("Adicionar ao ${state.selectedMeal.lowercase()}", {
            state.addFood(food, grams!!)
            state.returnTo(Destination.AddMeal)
        }, enabled = valid) } }) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
            EntryField(quantity, { quantity = it }, "Quantidade", Modifier.weight(1f), numeric = true,
                error = if (!valid) "Informe uma porção válida" else null)
        }
        Chips(listOf("gramas", "porções de 100 g"), setOf(unit)) { unit = it; quantity = if (unit == "gramas") "150" else "1" }
        Tile {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(number(item.kcal, 0), fontSize = 40.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Text("kcal em ${number(item.grams, 0)} g")
            }
        }
        SectionTitle("Macronutrientes")
        ProgressLine("Proteínas", "${number(item.protein)} g", (item.protein / 55).toFloat())
        ProgressLine("Carboidratos", "${number(item.carbs)} g", (item.carbs / 70).toFloat())
        ProgressLine("Gorduras", "${number(item.fat)} g", (item.fat / 30).toFloat())
        SectionTitle("Adicionar em")
        Chips(state.meals.map { it.type }, setOf(state.selectedMeal)) { state.selectedMeal = it }
    }
}
