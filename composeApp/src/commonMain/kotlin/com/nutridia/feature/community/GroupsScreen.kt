package com.nutridia.feature.community

import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.nutridia.core.components.*
import com.nutridia.data.*
import com.nutridia.navigation.*

@Composable
private fun GroupCard(group: Group, state: AppState) {
    ListTile(group.symbol, group.name, "${group.members} membros", onClick = {
        state.selectedGroupId = group.id; state.navigate(Destination.GroupDetail)
    }) {
        if (group.joined) Text("Participando", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelMedium)
        else Button({ state.toggleGroup(group.id) }) { Text("Entrar") }
    }
}

@Composable
fun GroupsScreen(state: AppState) {
    var search by remember { mutableStateOf("") }
    val groups = state.groups.filter { it.name.contains(search, true) }
    Screen("Grupos", state::back, bottom = { BottomNavigation(state, Destination.Community) }) {
        EntryField(search, { search = it }, "🔍 Buscar grupo")
        if (search.isBlank()) {
            SectionTitle("Sugeridos para você")
            state.groups.take(2).forEach { GroupCard(it, state) }
        }
        SectionTitle("Todos os grupos")
        if (groups.isEmpty()) EmptyState("Nenhum grupo encontrado. Tente outro nome.")
        groups.filter { search.isNotBlank() || it.id > 2 }.forEach { GroupCard(it, state) }
    }
}
