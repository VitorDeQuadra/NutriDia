// ORIGEM: CODEX | Data de criacao: 2026-10-20
package com.nutridia.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nutridia.core.components.*
import com.nutridia.data.AppState

@Composable
fun BottomNavigation(state: AppState, selected: Destination) {
    Column(Modifier.background(MaterialTheme.colorScheme.surface)) {
        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = .3f))
        NavigationBar(containerColor = MaterialTheme.colorScheme.surface, tonalElevation = 0.dp) {
            val items = listOf(Triple(Destination.Diary, "📓", "Diário"), Triple(Destination.Community, "👥", "Comunidade"),
                Triple(Destination.AddMeal, "+", "Adicionar"), Triple(Destination.Content, "📚", "Conteúdo"), Triple(Destination.Profile, "🙂", "Perfil"))
            items.forEach { (route, icon, label) ->
                NavigationBarItem(selected = route == selected, onClick = {
                    if (route == Destination.AddMeal) state.beginMeal()
                    else { if (route == Destination.Content) state.savedOnly = false; state.root(route) }
                }, icon = { Text(icon, fontSize = if (route == Destination.AddMeal) 30.sp else 22.sp) },
                    label = { if (route != Destination.AddMeal) Text(label, fontSize = 10.sp, maxLines = 1,
                        fontWeight = if (route == selected) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(indicatorColor = MaterialTheme.colorScheme.primaryContainer))
            }
        }
    }
}
