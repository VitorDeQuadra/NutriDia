// ORIGEM: CODEX | Data de criacao: 2026-10-20
package com.nutridia.feature.goals

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nutridia.core.components.*
import com.nutridia.data.AppState
import com.nutridia.data.DemoData
import com.nutridia.navigation.Destination

@Composable
fun InitialGoalsScreen(state: AppState) {
    var edit by remember { mutableStateOf("") }
    var input by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    Screen("Suas metas", state::back, action = { Text("Etapa 2 de 3", style = MaterialTheme.typography.bodySmall) }, bottom = {
        Box(Modifier.padding(16.dp)) { PrimaryButton("Continuar", {
            state.onboardingComplete = true; state.root(Destination.Diary)
            state.notify("Demonstração local: alterações não são salvas ao fechar o aplicativo.")
        }) }
    }) {
        LinearProgressIndicator(progress = { .66f }, modifier = Modifier.fillMaxWidth())
        Text("Qual é o seu objetivo?", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        listOf(Triple("📉", "Emagrecimento", "Déficit de 15% sobre o gasto estimado"),
            Triple("⚖️", "Manutenção", "Manter o peso atual"), Triple("📈", "Ganho de massa", "Superávit de 15%")).forEach { (symbol, title, detail) ->
            ListTile(symbol, title, detail, state.objective == title, { state.objective = title }) {
                if (state.objective == title) Text("✓")
            }
        }
        SectionTitle("Metas sugeridas")
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Tile(Modifier.weight(1f), onClick = { edit = "Calorias"; input = state.calories.toString() }) {
                Text("Calorias/dia"); Text("${state.calories} kcal", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            }
            Tile(Modifier.weight(1f), onClick = { edit = "Água"; input = state.waterGoal.toString() }) {
                Text("Água/dia"); Text("${state.waterGoal} ml", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            }
        }
        Text("Valores ilustrativos editáveis. O cálculo Mifflin-St Jeor exige idade, altura e outros dados ainda não coletados neste fluxo.", style = MaterialTheme.typography.bodySmall)
        SectionTitle("Restrições alimentares · opcional")
        Chips(DemoData.restrictions, state.restrictions) { state.restrictions = if (it in state.restrictions) state.restrictions - it else state.restrictions + it }
        Notice("⚠ O NutriDia não substitui acompanhamento de nutricionista ou médico.")
    }
    if (edit.isNotEmpty()) AlertDialog(onDismissRequest = { edit = ""; error = null }, title = { Text(edit) }, text = {
        EntryField(input, { input = it; error = null }, if (edit == "Calorias") "kcal por dia" else "ml por dia", numeric = true, error = error)
    }, confirmButton = { TextButton({
        val value = input.toIntOrNull()
        if (value == null || value <= 0) error = "Informe um número positivo"
        else if (edit == "Calorias" && value < 1200) error = "Use a tela Metas para confirmar valores abaixo de 1.200 kcal."
        else { if (edit == "Calorias") state.calories = value else state.waterGoal = value; edit = "" }
    }) { Text("Salvar") } }, dismissButton = { TextButton({ edit = ""; error = null }) { Text("Cancelar") } })
}
