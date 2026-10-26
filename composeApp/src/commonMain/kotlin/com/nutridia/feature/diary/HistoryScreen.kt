package com.nutridia.feature.diary

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nutridia.core.components.*
import com.nutridia.data.*
import com.nutridia.navigation.*

private val months = listOf("Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro")
private fun leap(year: Int) = year % 400 == 0 || year % 4 == 0 && year % 100 != 0
private fun daysInMonth(year: Int, month: Int) = when (month) { 2 -> if (leap(year)) 29 else 28; 4, 6, 9, 11 -> 30; else -> 31 }
private fun firstWeekday(year: Int, month: Int): Int {
    val offsets = intArrayOf(0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4)
    val y = if (month < 3) year - 1 else year
    return (y + y / 4 - y / 100 + y / 400 + offsets[month - 1] + 1) % 7
}

@Composable
fun HistoryScreen(state: AppState) {
    var year by remember { mutableStateOf(2026) }
    var month by remember { mutableStateOf(10) }
    var day by remember { mutableStateOf(4) }
    fun move(delta: Int) { month += delta; if (month == 0) { month = 12; year-- }; if (month == 13) { month = 1; year++ }; day = 1 }
    Screen("Histórico", state::back, bottom = { BottomNavigation(state, Destination.Diary) }) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconAction("‹", "Mês anterior") { move(-1) }
            Text("${months[month - 1]} $year", Modifier.weight(1f), fontWeight = FontWeight.Bold)
            IconAction("›", "Próximo mês") { move(1) }
        }
        Row { listOf("D", "S", "T", "Q", "Q", "S", "S").forEach { Box(Modifier.weight(1f), contentAlignment = Alignment.Center) { Text(it) } } }
        val start = firstWeekday(year, month)
        val days = daysInMonth(year, month)
        repeat((start + days + 6) / 7) { week ->
            Row {
                repeat(7) { column ->
                    val date = week * 7 + column - start + 1
                    Box(Modifier.weight(1f).aspectRatio(1f).padding(2.dp), contentAlignment = Alignment.Center) {
                        if (date in 1..days) {
                            val recorded = year == 2026 && month == 10 && date in listOf(2, 3, 4, 5, 6)
                            val color = when { date == day -> MaterialTheme.colorScheme.primary; recorded -> MaterialTheme.colorScheme.primaryContainer; else -> MaterialTheme.colorScheme.background }
                            Box(Modifier.fillMaxSize().clip(CircleShape).background(color).clickable { day = date }, contentAlignment = Alignment.Center) {
                                Text("$date", color = if (date == day) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface)
                            }
                        }
                    }
                }
            }
        }
        Text("● Meta cumprida   ○ Registrado   · Sem registro", style = MaterialTheme.typography.labelSmall)
        SectionTitle("$day de ${months[month - 1]} de $year")
        val current = year == 2026 && month == 10 && day == 6
        val historical = year == 2026 && month == 10 && day in 2..5
        if (current) {
            state.meals.filter { it.items.isNotEmpty() }.forEach { ListTile(it.symbol, it.type, it.items.joinToString { item -> item.food.name }) { Text("${number(it.kcal, 0)}") } }
            Tile(highlighted = true) { Text("Total do dia     ${state.consumed} / ${state.calories} kcal", fontWeight = FontWeight.Bold); Text("Água: ${state.water} ml") }
        } else if (historical) {
            ListTile("☕", "Café da manhã", "Tapioca com queijo") { Text("280") }
            ListTile("🍽️", "Almoço", "Macarrão integral, atum") { Text("540") }
            ListTile("🌙", "Jantar", "Sopa de legumes") { Text("310") }
            Tile(highlighted = true) { Text("Total do dia     1.130 / 1.780 kcal", fontWeight = FontWeight.Bold); Text("Histórico ilustrativo · água: 2.300 ml") }
        } else EmptyState("Nenhum registro neste dia. Selecione de 2 a 6 de outubro de 2026 para explorar os dados demonstrativos.")
    }
}
