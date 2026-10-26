package com.nutridia.feature.goals

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nutridia.core.components.*
import com.nutridia.data.*
import kotlin.math.roundToInt

@Composable
fun GoalsScreen(state: AppState) {
    var objective by remember { mutableStateOf(state.objective) }
    var kcal by remember { mutableStateOf(state.calories.toFloat()) }
    var water by remember { mutableStateOf(state.waterGoal.toFloat()) }
    var portion by remember { mutableStateOf(state.waterPortion.toString()) }
    var current by remember { mutableStateOf(number(state.weight)) }
    var target by remember { mutableStateOf(number(state.targetWeight)) }
    var confirmLow by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    fun save() {
        val p = portion.toIntOrNull()
        val c = current.replace(',', '.').toDoubleOrNull()
        val t = target.replace(',', '.').toDoubleOrNull()
        if (p == null || p !in 1..2000 || c == null || t == null || !c.isFinite() || !t.isFinite() || c !in 20.0..500.0 || t !in 20.0..500.0) {
            error = "Informe porção entre 1 e 2.000 ml e pesos entre 20 e 500 kg."
            return
        }
        state.objective = objective; state.calories = kcal.roundToInt(); state.waterGoal = water.roundToInt()
        state.waterPortion = p; state.weight = c; state.targetWeight = t
        state.back(); state.notify("Metas atualizadas nesta sessão. O histórico ilustrativo mantém a meta anterior.")
    }
    Screen("Metas", state::back, action = { Button({ if (kcal < 1200) confirmLow = true else save() }) { Text("Salvar") } }) {
        SectionTitle("Objetivo")
        Chips(listOf("Emagrecimento", "Manutenção", "Ganho de massa"), setOf(objective)) { objective = it }
        SectionTitle("Metas diárias")
        Text("Calorias                         ${kcal.roundToInt()} kcal", color = MaterialTheme.colorScheme.primary)
        Slider(kcal, { kcal = it }, valueRange = 800f..4000f, steps = 63)
        Text("Água                              ${water.roundToInt()} ml", color = MaterialTheme.colorScheme.primary)
        Slider(water, { water = it }, valueRange = 500f..5000f, steps = 89)
        EntryField(portion, { portion = it }, "Porção padrão de água (ml)", numeric = true)
        SectionTitle("Peso")
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            EntryField(current, { current = it }, "Atual (kg)", Modifier.weight(1f), numeric = true)
            EntryField(target, { target = it }, "Alvo (kg)", Modifier.weight(1f), numeric = true)
        }
        OutlineButton("🔄  Recalcular metas a partir do peso atual", {
            state.notify("O recálculo completo exige idade, altura e dados de atividade ainda não coletados. Ajuste as metas manualmente.")
        }, Modifier.fillMaxWidth())
        error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        Notice("⚠ Metas abaixo de 1.200 kcal exigem confirmação e acompanhamento profissional.")
    }
    if (confirmLow) AlertDialog(onDismissRequest = { confirmLow = false }, title = { Text("Confirmar meta abaixo de 1.200 kcal?") },
        text = { Text("Essa meta exige acompanhamento profissional. O NutriDia não substitui orientação nutricional.") },
        confirmButton = { TextButton({ confirmLow = false; save() }) { Text("Confirmar meta") } },
        dismissButton = { TextButton({ confirmLow = false }) { Text("Revisar") } })
}
