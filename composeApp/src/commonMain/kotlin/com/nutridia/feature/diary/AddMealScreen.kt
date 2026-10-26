// ORIGEM: CODEX | Data de criacao: 2026-10-20
package com.nutridia.feature.diary

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nutridia.core.components.*
import com.nutridia.data.*
import com.nutridia.navigation.Destination

@Composable
fun AddMealScreen(state: AppState) {
    var tab by remember { mutableStateOf("Recentes") }
    Screen(state.selectedMeal, state::back, action = {
        IconAction("📷", "Registrar por foto") { state.unsupported("Câmera e registro por foto") }
    }, bottom = {
        Box(Modifier.padding(16.dp)) {
            Tile {
                Row {
                    Column(Modifier.weight(1f)) {
                        Text("${state.pendingItems.size} itens selecionados")
                        Text("${number(state.pendingItems.sumOf { it.kcal }, 0)} kcal", style = MaterialTheme.typography.titleLarge)
                    }
                    Button(state::saveMeal, enabled = state.pendingItems.isNotEmpty()) { Text("Salvar refeição") }
                }
            }
        }
    }) {
        OutlineButton("🔍  Buscar alimento", { state.navigate(Destination.FoodSearch) }, Modifier.fillMaxWidth())
        Chips(listOf("Recentes", "Favoritos", "Minhas receitas"), setOf(tab)) { tab = it }
        if (tab == "Minhas receitas") {
            DemoData.contents.filter { it.recipe }.forEach { item ->
                ListTile(item.symbol, item.title, "${item.kcal} kcal", onClick = { state.selectedContent = item; state.navigate(Destination.RecipeDetail) })
            }
        } else {
            val foods = if (tab == "Favoritos") state.foods.filter { it.id in state.favorites } else state.foods.take(3)
            if (foods.isEmpty()) EmptyState("Seus alimentos favoritos aparecerão aqui. Marque a estrela no detalhe de um alimento.")
            foods.forEach { food -> ListTile(food.symbol, food.name, "100 g · ${number(food.kcal, 0)} kcal",
                onClick = { state.selectedFood = food; state.navigate(Destination.FoodDetail) }) {
                IconAction("+", "Adicionar ${food.name}") { state.addFood(food, 100.0) }
            } }
            SectionTitle("Favoritos")
            ListTile("⭐", "Meu almoço padrão", "4 itens · demonstração", true) {
                Button({ state.pendingItems = state.pendingItems + DemoData.meals[1].items }) { Text("Repetir") }
            }
        }
        if (state.pendingItems.isNotEmpty()) {
            SectionTitle("Selecionados")
            state.pendingItems.forEachIndexed { index, item ->
                ListTile(item.food.symbol, item.food.name, "${number(item.grams, 0)} g · ${number(item.kcal, 0)} kcal") {
                    IconAction("×", "Remover item selecionado") { state.pendingItems = state.pendingItems.filterIndexed { i, _ -> i != index } }
                }
            }
        }
    }
}
