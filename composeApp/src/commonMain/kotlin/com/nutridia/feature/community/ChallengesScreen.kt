// ORIGEM: CODEX | Data de criacao: 2026-10-20
package com.nutridia.feature.community

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.nutridia.core.components.*
import com.nutridia.data.*
import com.nutridia.navigation.*

@Composable
fun ChallengesScreen(state: AppState) {
    var tab by remember { mutableStateOf("Ativos") }
    val filtered = state.challenges.filter { when (tab) { "Ativos" -> it.joined && it.completed < it.days; "Disponíveis" -> !it.joined; else -> it.completed == it.days } }
    Screen("Desafios", state::back, bottom = { BottomNavigation(state, Destination.Community) }) {
        Chips(listOf("Ativos", "Disponíveis", "Concluídos"), setOf(tab)) { tab = it }
        if (filtered.isEmpty()) EmptyState("Nenhum desafio nesta categoria. Explore os disponíveis para participar.")
        filtered.forEach { challenge ->
            Tile(highlighted = challenge.id == 1, onClick = { state.selectedChallengeId = challenge.id; state.navigate(Destination.ChallengeDetail) }) {
                Text(challenge.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text("${challenge.participants} participantes · ${challenge.completed} de ${challenge.days} dias")
                LinearProgressIndicator(progress = { challenge.completed.toFloat() / challenge.days }, modifier = Modifier.fillMaxWidth())
                Row {
                    Text(if (challenge.joined) "Acompanhe sua constância" else "${challenge.days} dias", Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                    Button({ if (challenge.joined) state.markChallenge(challenge.id) else state.joinChallenge(challenge.id) },
                        enabled = !challenge.markedToday && challenge.completed < challenge.days) {
                        Text(if (challenge.markedToday) "Hoje ✓" else if (challenge.joined) "Marcar hoje" else "Entrar")
                    }
                }
            }
        }
        if (tab == "Ativos") {
            SectionTitle("Começam em breve")
            state.challenges.filter { !it.joined }.forEach { challenge ->
                ListTile(challenge.symbol, challenge.name, "${challenge.days} dias · demonstração", onClick = {
                    state.selectedChallengeId = challenge.id; state.navigate(Destination.ChallengeDetail)
                }) { OutlineButton("Entrar", { state.joinChallenge(challenge.id) }) }
            }
        }
    }
}
