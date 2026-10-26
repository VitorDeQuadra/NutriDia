// ORIGEM: CODEX | Data de criacao: 2026-10-20
package com.nutridia.feature.community

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nutridia.core.components.*
import com.nutridia.data.AppState

@Composable
fun CreatePostScreen(state: AppState) {
    var text by remember { mutableStateOf("") }
    var group by remember { mutableStateOf(state.postingGroup ?: state.groups.firstOrNull { it.joined }?.name ?: "Público") }
    val options = listOf("Público") + state.groups.filter { it.joined }.map { it.name }
    Screen("Nova publicação", state::back, action = {
        Button({ state.publish(text, if (group == "Público") "" else group) }, enabled = text.isNotBlank() && text.length <= 1000) { Text("Publicar") }
    }) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Avatar(state.name); Text(state.name, style = MaterialTheme.typography.titleLarge)
        }
        Chips(options, setOf(group)) { group = it }
        OutlinedTextField(text, { if (it.length <= 1000) text = it }, Modifier.fillMaxWidth().heightIn(min = 160.dp),
            placeholder = { Text("Compartilhe uma dica, conquista ou dúvida...") }, shape = CardShape,
            supportingText = { Text("${text.length} / 1.000") })
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlineButton("📷  Foto", { state.unsupported("Câmera") }, Modifier.weight(1f))
            OutlineButton("🖼️  Galeria", { state.unsupported("Galeria") }, Modifier.weight(1f))
        }
        Notice("As publicações do grupo ficam visíveis para todos os membros. Conteúdo impróprio pode ser denunciado.")
        Text("Publicação local de demonstração, sem envio ao servidor.", style = MaterialTheme.typography.labelSmall)
    }
}
