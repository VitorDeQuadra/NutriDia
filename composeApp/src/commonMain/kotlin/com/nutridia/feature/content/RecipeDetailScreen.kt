package com.nutridia.feature.content

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nutridia.core.components.*
import com.nutridia.data.*

@Composable
fun RecipeDetailScreen(state: AppState) {
    val item = state.selectedContent
    var choosing by remember { mutableStateOf(false) }
    Scaffold(containerColor = MaterialTheme.colorScheme.background) { padding ->
      Column(Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState())) {
        Surface(Modifier.fillMaxWidth().height(196.dp), color = MaterialTheme.colorScheme.primaryContainer) {
            Box(contentAlignment = Alignment.Center) {
                Text(item.symbol, fontSize = 76.sp)
                Row(Modifier.fillMaxWidth().align(Alignment.TopCenter).padding(12.dp)) {
                    IconAction("‹", "Voltar", state::back)
                    Spacer(Modifier.weight(1f))
                    IconAction(if (item.id in state.savedContent) "★" else "☆", "Salvar conteúdo") { state.toggleSaved(item.id) }
                }
            }
        }
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(item.title, fontSize = 23.sp, fontWeight = FontWeight.Bold)
        Text("Por Dra. Beatriz · ${item.minutes} min${if (item.recipe) " · rende 1 porção" else " de leitura"}", style = MaterialTheme.typography.bodySmall)
        if (item.recipe) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Metric("${item.kcal}", "kcal", Modifier.weight(1f))
                Metric(if (item.id == 1) "12 g" else "—", "proteína", Modifier.weight(1f))
                Metric(if (item.id == 1) "38 g" else "—", "carbo.", Modifier.weight(1f))
            }
            SectionTitle("Ingredientes")
            Text(item.ingredients.joinToString("\n") { "· $it" }, lineHeight = 24.sp)
            SectionTitle("Modo de preparo")
        }
        Text(item.body, lineHeight = 24.sp)
        if (item.recipe) PrimaryButton("Adicionar ao diário", { choosing = true })
        }
      }
    }
    if (choosing) AlertDialog(onDismissRequest = { choosing = false }, title = { Text("Adicionar receita em") }, text = {
        Chips(state.meals.map { it.type }, setOf(state.selectedMeal)) { state.selectedMeal = it }
    }, confirmButton = { TextButton({
        val recipeFood = Food(1000 + item.id, item.title, item.symbol, item.kcal.toDouble(), if (item.id == 1) 12.0 else 0.0, if (item.id == 1) 38.0 else 0.0, if (item.id == 1) 8.9 else 0.0)
        state.pendingItems = state.pendingItems + MealItem(recipeFood, 100.0); state.saveMeal(); choosing = false
    }) { Text("Adicionar 1 porção") } }, dismissButton = { TextButton({ choosing = false }) { Text("Cancelar") } })
}
