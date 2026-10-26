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
import com.nutridia.navigation.*

@Composable
fun DiaryScreen(state: AppState) {
    var inspecting by remember { mutableStateOf<Meal?>(null) }
    var deleting by remember { mutableStateOf<MealItem?>(null) }
    var editing by remember { mutableStateOf<MealItem?>(null) }
    Screen("Hoje, 6 out", action = {
        IconAction("📅", "Calendário e histórico") { state.navigate(Destination.History) }
    }, bottom = { BottomNavigation(state, Destination.Diary) }) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Box(Modifier.size(136.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(progress = { (state.consumed.toFloat() / state.calories).coerceIn(0f, 1f) },
                    modifier = Modifier.fillMaxSize(), strokeWidth = 13.dp,
                    color = if (state.consumed > state.calories) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.outline.copy(alpha = .3f))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("${state.consumed}", fontSize = 26.sp, fontWeight = FontWeight.Bold)
                    Text("de ${state.calories} kcal", fontSize = 12.sp)
                }
            }
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(if (state.calories >= state.consumed) "Restam" else "Além da meta")
                Text("${kotlin.math.abs(state.calories - state.consumed)} kcal", fontSize = 21.sp,
                    fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                ProgressLine("Proteínas", "${number(state.protein, 0)} g", (state.protein / 120).toFloat())
                ProgressLine("Carboidratos", "${number(state.carbs, 0)} g", (state.carbs / 230).toFloat())
                ProgressLine("Gorduras", "${number(state.fat, 0)} g", (state.fat / 65).toFloat())
            }
        }
        Tile(highlighted = state.water >= state.waterGoal) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Glyph("💧")
                Column(Modifier.weight(1f)) { Text("Água", fontSize = 19.sp, fontWeight = FontWeight.Bold); Text("${state.water} de ${state.waterGoal} ml", fontSize = 12.sp) }
                IconAction("−", "Remover porção de água") { state.changeWater(-state.waterPortion) }
                FilledTonalButton({ state.changeWater(state.waterPortion) }, contentPadding = PaddingValues(10.dp)) { Text("+ ${state.waterPortion} ml", fontSize = 12.sp) }
            }
            if (state.water >= state.waterGoal) Text("Meta de água atingida ✓", color = MaterialTheme.colorScheme.primary)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            SectionTitle("Refeições")
            Spacer(Modifier.weight(1f))
            Text("🔥 12 dias seguidos", fontSize = 12.sp)
        }
        state.meals.forEach { meal ->
            if (meal.items.isEmpty()) OutlineButton("+  Adicionar ${meal.type.lowercase()}", { state.beginMeal(meal.type) }, Modifier.fillMaxWidth())
            else ListTile(meal.symbol, meal.type, meal.items.joinToString { it.food.name }, onClick = { inspecting = meal }) {
                Text(number(meal.kcal, 0), fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            OutlineButton("Progresso", { state.navigate(Destination.Progress) }, Modifier.weight(1f))
            OutlineButton("Metas", { state.navigate(Destination.Goals) }, Modifier.weight(1f))
        }
        Text("Dados demonstrativos · 6 de outubro de 2026 · sessão local", style = MaterialTheme.typography.labelSmall)
    }
    inspecting?.let { meal ->
        val current = state.meals.first { it.type == meal.type }
        AlertDialog(onDismissRequest = { inspecting = null }, title = { Text(current.type) }, text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                current.items.forEach { item ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("${item.food.name}\n${number(item.grams, 0)} g · ${number(item.kcal, 0)} kcal", Modifier.weight(1f))
                        TextButton({ editing = item }) { Text("Editar") }
                        IconAction("×", "Excluir ${item.food.name}") { deleting = item }
                    }
                }
            }
        }, confirmButton = { TextButton({ inspecting = null; state.beginMeal(current.type) }) { Text("Adicionar itens") } },
            dismissButton = { TextButton({ inspecting = null }) { Text("Fechar") } })
    }
    deleting?.let { item ->
        AlertDialog(onDismissRequest = { deleting = null }, title = { Text("Excluir registro?") }, text = { Text(item.food.name) },
            confirmButton = { TextButton({
                val type = inspecting?.type
                state.meals = state.meals.map { if (it.type == type) { val items = it.items.toMutableList(); items.remove(item); it.copy(items = items) } else it }
                deleting = null
            }) { Text("Excluir") } }, dismissButton = { TextButton({ deleting = null }) { Text("Cancelar") } })
    }
    editing?.let { item ->
        TextDialog("Editar quantidade", number(item.grams), "Gramas", numeric = true, onDismiss = { editing = null }, validate = {
            val grams = it.replace(',', '.').toDoubleOrNull()
            if (grams == null || !grams.isFinite() || grams <= 0 || grams > 10000) "Informe de 1 a 10.000 g." else null
        }) { input ->
            val grams = input.replace(',', '.').toDouble()
            val type = inspecting?.type
            state.meals = state.meals.map { meal ->
                if (meal.type == type) meal.copy(items = meal.items.mapIndexed { index, existing ->
                    if (index == meal.items.indexOf(item)) existing.copy(grams = grams) else existing
                }) else meal
            }
            editing = null
        }
    }
}
