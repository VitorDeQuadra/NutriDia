// ORIGEM: CODEX | Data de criacao: 2026-10-20
package com.nutridia.feature.profile

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.nutridia.core.components.*
import com.nutridia.data.*
import com.nutridia.navigation.Destination

@Composable
fun NotificationsScreen(state: AppState) {
    Screen("Notificações", state::back, action = {
        TextButton({ state.notifications = state.notifications.map { it.copy(read = true) } }) { Text("Marcar lidas", style = MaterialTheme.typography.labelMedium) }
    }) {
        listOf(true, false).forEach { today ->
            SectionTitle(if (today) "Hoje" else "Ontem")
            state.notifications.filter { it.today == today }.forEach { notification ->
                ListTile(notification.symbol, notification.title, notification.subtitle, !notification.read, {
                    state.notifications = state.notifications.map { if (it.id == notification.id) it.copy(read = true) else it }
                    when (notification.target) {
                        "meal" -> state.beginMeal("Almoço")
                        "water" -> state.navigate(Destination.Diary)
                        "post" -> { state.selectedPostId = 3; state.navigate(Destination.PostDetail) }
                        "challenge" -> { state.selectedChallengeId = 1; state.navigate(Destination.ChallengeDetail) }
                        else -> state.navigate(Destination.Profile)
                    }
                })
            }
        }
        Text("Central demonstrativa. Notificações do sistema ainda não estão conectadas.", style = MaterialTheme.typography.labelSmall)
    }
}
