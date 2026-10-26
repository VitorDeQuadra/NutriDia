// ORIGEM: CODEX | Data de criacao: 2026-10-20
package com.nutridia.feature.goals

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nutridia.core.components.*
import com.nutridia.data.*
import com.nutridia.navigation.*

@Composable
fun ProgressScreen(state: AppState) {
    var period by remember { mutableStateOf("Semana") }
    var recording by remember { mutableStateOf(false) }
    val primary = MaterialTheme.colorScheme.primary
    val muted = MaterialTheme.colorScheme.outline.copy(alpha = .3f)
    val values = if (period == "Semana") listOf(1650, 1900, 1150, 1800, 1870, 2100, state.consumed)
        else List(30) { 1250 + (it * 173 % 850) }.dropLast(1) + state.consumed
    Screen("Progresso", state::back, bottom = { BottomNavigation(state, Destination.Diary) }) {
        Chips(listOf("Semana", "Mês"), setOf(period)) { period = it }
        Tile {
            Row { Text("Calorias por dia", Modifier.weight(1f), fontWeight = FontWeight.Bold); Text("meta ${state.calories}", style = MaterialTheme.typography.labelSmall) }
            Canvas(Modifier.fillMaxWidth().height(130.dp).semantics {
                contentDescription = "Gráfico demonstrativo de calorias: ${values.joinToString()} kcal; meta ${state.calories}"
            }) {
                val scale = (maxOf(values.maxOrNull() ?: 0, state.calories) * 1.15f).coerceAtLeast(1f)
                val goalY = size.height * (1 - state.calories / scale)
                drawLine(primary.copy(alpha = .6f), Offset(0f, goalY), Offset(size.width, goalY), 2f,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 6f)))
                val cell = size.width / values.size
                values.forEachIndexed { index, value ->
                    val height = size.height * value / scale
                    drawRoundRect(if (value < 1200) muted else primary, topLeft = Offset(index * cell + cell * .09f, size.height - height),
                        size = androidx.compose.ui.geometry.Size(cell * .8f, height), cornerRadius = androidx.compose.ui.geometry.CornerRadius(4f))
                }
            }
            Row { (if (period == "Semana") listOf("S", "T", "Q", "Q", "S", "S", "D") else listOf("1", "5", "10", "15", "20", "25", "30")).forEach { Text(it, Modifier.weight(1f), style = MaterialTheme.typography.labelSmall) } }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Tile(Modifier.weight(1f), highlighted = true) { Text("🔥"); Text("12 dias", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold); Text("Sequência atual · recorde 18", style = MaterialTheme.typography.bodySmall) }
            Tile(Modifier.weight(1f)) { Text("⚖️"); Text("${number(state.weight - 75)} kg", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold); Text("No mês · ${number(state.weight)} kg hoje", style = MaterialTheme.typography.bodySmall) }
        }
        Tile {
            Row { Text("Evolução do peso", Modifier.weight(1f), fontWeight = FontWeight.Bold); TextButton({ recording = true }) { Text("Registrar") } }
            val weights = listOf(75.0, 74.6, 74.2, 74.0, 73.3, 73.0, state.weight)
            Canvas(Modifier.fillMaxWidth().height(125.dp).semantics { contentDescription = "Evolução ilustrativa do peso de 75 a ${number(state.weight)} kg" }) {
                val min = (weights.minOrNull() ?: 68.0) - .5
                val max = (weights.maxOrNull() ?: 75.0) + .5
                val path = Path()
                weights.forEachIndexed { index, value ->
                    val x = index.toFloat() / (weights.size - 1) * size.width
                    val y = ((max - value) / (max - min) * size.height).toFloat()
                    if (index == 0) path.moveTo(x, y) else path.lineTo(x, y)
                    if (index == weights.lastIndex) drawCircle(primary, 5.dp.toPx(), Offset(x, y))
                }
                drawPath(path, primary, style = androidx.compose.ui.graphics.drawscope.Stroke(width = 3.dp.toPx()))
            }
            Row { Text("6 set", Modifier.weight(1f)); Text("6 out") }
        }
        SectionTitle("Conquistas recentes")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Metric("🏅", "7 dias", Modifier.weight(1f)); Metric("💧", "Água", Modifier.weight(1f)); Metric("🥇", "1º desafio", Modifier.weight(1f))
        }
        Notice("Gráficos e sequência ilustrativos. As refeições e o peso registrados nesta sessão atualizam os valores atuais; ainda não há histórico persistente.")
    }
    if (recording) TextDialog("Registrar peso", number(state.weight), "Peso em kg", true, { recording = false }, validate = {
        val value = it.replace(',', '.').toDoubleOrNull()
        if (value == null || !value.isFinite() || value !in 20.0..500.0) "Informe um peso entre 20 e 500 kg." else null
    }) {
        state.weight = it.replace(',', '.').toDouble(); recording = false
    }
}
