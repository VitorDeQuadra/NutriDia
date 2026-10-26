package com.nutridia.feature.diary

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.nutridia.core.components.*
import com.nutridia.data.*
import com.nutridia.navigation.Destination

@Composable
fun FoodSearchScreen(state: AppState) {
    var query by remember { mutableStateOf("") }
    var custom by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var kcal by remember { mutableStateOf("") }
    val results = state.foods.filter { query.trim().length >= 2 && it.name.contains(query.trim(), ignoreCase = true) }
    Screen("Buscar alimento", state::back, action = { IconAction("×", "Limpar busca") { query = "" } }) {
        EntryField(query, { query = it }, "🔍 Nome do alimento")
        Text(if (query.trim().length < 2) "Digite pelo menos 2 caracteres" else "${results.size} resultados na base demonstrativa")
        results.forEach { food ->
            ListTile(food.symbol, food.name, "100 g · ${number(food.kcal, 0)} kcal · P ${number(food.protein, 0)} g${if (food.custom) " · Meu alimento" else ""}",
                onClick = { state.selectedFood = food; state.navigate(Destination.FoodDetail) })
        }
        if (query.trim().length >= 2 && results.isEmpty()) EmptyState("Nenhum alimento encontrado. Tente outro nome ou cadastre um alimento personalizado.")
        OutlineButton("+  Cadastrar alimento personalizado", { custom = true }, Modifier.fillMaxWidth())
        Notice("Amostra local de alimentos. A base TACO completa ainda não está integrada.")
    }
    if (custom) AlertDialog(onDismissRequest = { custom = false }, title = { Text("Alimento personalizado") }, text = {
        Column {
            EntryField(name, { name = it }, "Nome")
            EntryField(kcal, { kcal = it }, "kcal por 100 g", numeric = true)
            Text("Macronutrientes não informados serão considerados zero nesta demonstração.")
        }
    }, confirmButton = { TextButton({
        val food = Food((state.foods.maxOfOrNull { it.id } ?: 0) + 1, name.trim(), "🍽️", kcal.replace(',', '.').toDouble(), 0.0, 0.0, 0.0, true)
        state.foods = state.foods + food; custom = false; state.selectedFood = food; state.navigate(Destination.FoodDetail)
    }, enabled = name.isNotBlank() && (kcal.replace(',', '.').toDoubleOrNull()?.let { it.isFinite() && it >= 0 } == true)) { Text("Salvar") } },
        dismissButton = { TextButton({ custom = false }) { Text("Cancelar") } })
}
