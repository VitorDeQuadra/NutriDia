package com.nutridia.feature.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nutridia.core.components.*
import com.nutridia.data.AppState
import com.nutridia.navigation.*

@Composable
fun ProfileScreen(state: AppState) {
    Screen("Perfil", action = { IconAction("⚙", "Configurações") { state.navigate(Destination.Settings) } },
        bottom = { BottomNavigation(state, Destination.Profile) }) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Avatar(state.name, large = true)
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(state.name, fontSize = 21.sp, fontWeight = FontWeight.Bold)
                Text("${state.objective} · desde set/2026", style = MaterialTheme.typography.bodySmall)
                Text((state.restrictions.take(2) + "🔥 12 dias").joinToString(" · "), style = MaterialTheme.typography.bodySmall)
            }
        }
        OutlineButton("Editar perfil", { state.navigate(Destination.EditProfile) }, Modifier.fillMaxWidth())
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Metric("34", "dias", Modifier.weight(1f)); Metric("${112 + state.recordedMeals}", "refeições", Modifier.weight(1f)); Metric("${state.challenges.count { it.joined }}", "desafios", Modifier.weight(1f))
        }
        SectionTitle("Medalhas")
        val badges = listOf("🥇" to "Início", "🏅" to "7 dias", "💧" to "Água", "🔒" to "30 dias",
            "🔒" to "100 ref.", "🔒" to "Desafio", "🔒" to "Receita", "🔒" to "Apoio")
        badges.chunked(4).forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                row.forEach { (symbol, label) ->
                    Surface(onClick = {
                        state.notify(if (symbol == "🔒") "Medalha bloqueada: alcance o marco $label. Conquistas ilustrativas." else "Conquista $label · demonstração")
                    }, modifier = Modifier.weight(1f), shape = CardShape, color = cardColor()) {
                        Column(Modifier.padding(vertical = 16.dp, horizontal = 4.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(symbol, fontSize = 25.sp); Text(label, fontSize = 11.sp)
                        }
                    }
                }
            }
        }
        ListTile("⭐", "Conteúdos salvos", "${state.savedContent.size} conteúdos", onClick = { state.savedOnly = true; state.navigate(Destination.Content) }) { Text("›") }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            OutlineButton("Progresso", { state.navigate(Destination.Progress) }, Modifier.weight(1f))
            OutlineButton("Metas", { state.navigate(Destination.Goals) }, Modifier.weight(1f))
        }
        Text("Estatísticas e medalhas iniciais ilustrativas · sessão local", style = MaterialTheme.typography.labelSmall)
    }
}

