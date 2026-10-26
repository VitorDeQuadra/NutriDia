// ORIGEM: CODEX | Data de criacao: 2026-10-20
package com.nutridia.feature.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.nutridia.core.components.*
import com.nutridia.data.*

@Composable
fun EditProfileScreen(state: AppState) {
    var name by remember { mutableStateOf(state.name) }
    var restrictions by remember { mutableStateOf(state.restrictions) }
    var deleting by remember { mutableStateOf(false) }
    Screen("Editar perfil", state::back, action = {
        Button({ state.updateProfile(name, restrictions); state.back(); state.notify("Perfil atualizado nesta sessão.") }, enabled = name.isNotBlank()) { Text("Salvar") }
    }) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Avatar(name, large = true)
            TextButton({ state.unsupported("Alteração da foto de perfil") }) { Text("📷  Alterar foto") }
        }
        EntryField(name, { name = it }, "Nome", error = if (name.isBlank()) "O nome é obrigatório" else null)
        EntryField(state.email, {}, "E-mail", enabled = false)
        SectionTitle("Restrições alimentares")
        Chips(DemoData.restrictions, restrictions) { restrictions = if (it in restrictions) restrictions - it else restrictions + it }
        Text("Usadas para filtrar receitas e artigos.", style = MaterialTheme.typography.bodySmall)
        OutlineButton("Excluir minha conta", { deleting = true }, Modifier.fillMaxWidth(), danger = true)
    }
    if (deleting) AlertDialog(onDismissRequest = { deleting = false }, title = { Text("Exclusão da conta indisponível") },
        text = { Text("Não existe conta remota conectada neste protótipo. Você pode limpar os dados desta sessão demonstrativa. Isso não representa uma exclusão de conta no servidor.") },
        confirmButton = { TextButton({ state.clearSession(); state.notify("Sessão local reiniciada. Nenhuma conta remota foi excluída.") }) { Text("Reiniciar demonstração") } },
        dismissButton = { TextButton({ deleting = false }) { Text("Cancelar") } })
}
