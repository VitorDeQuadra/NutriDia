// ORIGEM: CODEX | Data de criacao: 2026-10-20
package com.nutridia.feature.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.nutridia.core.components.*
import com.nutridia.data.AppState

@Composable
fun SettingsScreen(state: AppState) {
    var dialog by remember { mutableStateOf("") }
    Screen("Configurações", state::back) {
        SectionTitle("Aparência")
        ListTile("🎨", "Tema", onClick = { dialog = "Tema" }) { Text("${state.theme} ›", style = MaterialTheme.typography.bodySmall) }
        SectionTitle("Lembretes")
        listOf(Triple("Refeições", "🍽️", state.mealReminderTimes), Triple("Hidratação", "💧", state.waterReminderTimes),
            Triple("Comunidade", "👥", ""), Triple("Conquistas", "🏅", "")).forEach { (label, symbol, detail) ->
            ListTile(symbol, label, detail, onClick = { if (label == "Refeições" || label == "Hidratação") dialog = label }) {
                Switch(label in state.reminders, { checked -> state.reminders = if (checked) state.reminders + label else state.reminders - label })
            }
        }
        SectionTitle("Privacidade")
        ListTile("📊", "Mostrar meu progresso", "Aparecer nas classificações dos desafios") {
            Switch(state.showRanking, { state.showRanking = it })
        }
        OutlineButton("Política de privacidade", { state.unsupported("Política de privacidade definitiva") }, Modifier.fillMaxWidth())
        SectionTitle("Conta")
        ListTile("ℹ️", "Sobre o NutriDia", onClick = { dialog = "Sobre" }) { Text("v0.1.0 ›") }
        ListTile("🚪", "Sair da conta", onClick = { dialog = "Sair" })
        Notice("As preferências valem nesta sessão. Os lembretes não agendam notificações no aparelho enquanto a integração não estiver disponível.")
    }
    when (dialog) {
        "Tema" -> AlertDialog(onDismissRequest = { dialog = "" }, title = { Text("Aparência") }, text = {
            Chips(listOf("Claro", "Escuro", "Do sistema"), setOf(state.theme)) { state.theme = it }
        }, confirmButton = { TextButton({ dialog = "" }) { Text("Concluir") } })
        "Refeições", "Hidratação" -> TextDialog("Preferência de lembrete", if (dialog == "Refeições") state.mealReminderTimes else state.waterReminderTimes,
            "Horários ou intervalo", onDismiss = { dialog = "" }) {
            if (dialog == "Refeições") state.mealReminderTimes = it else state.waterReminderTimes = it
            dialog = ""; state.notify("Preferência atualizada localmente. Nenhum lembrete foi agendado no aparelho.")
        }
        "Sobre" -> AlertDialog(onDismissRequest = { dialog = "" }, title = { Text("NutriDia") },
            text = { Text("Diário de nutrição e comunidade de apoio à reeducação alimentar. Projeto acadêmico. Versão 0.1.0 · interface demonstrativa com 25 telas.") },
            confirmButton = { TextButton({ dialog = "" }) { Text("Fechar") } })
        "Sair" -> AlertDialog(onDismissRequest = { dialog = "" }, title = { Text("Sair da demonstração?") },
            text = { Text("As alterações desta sessão serão descartadas. Não há conta remota autenticada.") },
            confirmButton = { TextButton({ state.clearSession() }) { Text("Sair") } },
            dismissButton = { TextButton({ dialog = "" }) { Text("Cancelar") } })
    }
}
