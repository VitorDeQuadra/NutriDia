// ORIGEM: CODEX | Data de criacao: 2026-10-20
package com.nutridia.feature.community

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nutridia.core.components.*
import com.nutridia.data.AppState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChallengeDetailScreen(state: AppState) {
    val challenge = state.selectedChallenge
    var tab by remember { mutableStateOf("Sobre") }
    Screen(challenge.name, state::back) {
        Chips(listOf("Sobre", "Classificação"), setOf(tab)) { tab = it }
        if (tab == "Sobre") {
            Tile(highlighted = true) {
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(challenge.symbol, fontSize = 42.sp)
                    Text("Dia ${minOf(challenge.completed + 1, challenge.days)} de ${challenge.days}", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Text(if (challenge.completed == challenge.days) "Desafio concluído! 🏅" else "Faltam ${challenge.days - challenge.completed} dias para a medalha")
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        repeat(challenge.days) { index ->
                            Surface(shape = PillShape, color = if (index < challenge.completed) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer) {
                                Text(if (index < challenge.completed) "✓" else "${index + 1}", Modifier.padding(horizontal = 10.dp, vertical = 8.dp), fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
            PrimaryButton(if (!challenge.joined) "Participar do desafio" else if (challenge.markedToday) "Dia de hoje registrado ✓" else "Marcar o dia de hoje", {
                if (challenge.joined) state.markChallenge(challenge.id) else state.joinChallenge(challenge.id)
            }, enabled = !challenge.markedToday && challenge.completed < challenge.days)
            SectionTitle("Sobre o desafio")
            Text(challenge.description)
        }
        SectionTitle("Classificação")
        ListTile("1", "Renata A.", "") { Text("100%") }
        ListTile("2", "Carlos Dias", "") { Text("100%") }
        if (state.showRanking && challenge.joined) ListTile("28", "Você", "Posição ilustrativa", true) { Text("${challenge.completed * 100 / challenge.days}%") }
        else Notice("Sua posição está oculta ou você ainda não participa deste desafio.")
        Notice("Classificação demonstrativa. A marcação é limitada a uma vez nesta sessão, pois ainda não há sincronização por data.")
    }
}
