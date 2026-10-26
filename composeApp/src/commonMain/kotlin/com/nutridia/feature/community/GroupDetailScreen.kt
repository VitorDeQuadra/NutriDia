// ORIGEM: CODEX | Data de criacao: 2026-10-20
package com.nutridia.feature.community

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.nutridia.core.components.*
import com.nutridia.data.AppState
import com.nutridia.navigation.Destination

@Composable
fun GroupDetailScreen(state: AppState) {
    val group = state.selectedGroup
    Screen(group.name, state::back) {
        Tile(highlighted = true) {
            ListTile(group.symbol, group.name, "${group.members} membros · moderado por Dra. Beatriz", true)
            Text(group.description)
            Button({ state.toggleGroup(group.id) }) { Text(if (group.joined) "Participando ✓ · Sair" else "Entrar no grupo") }
        }
        SectionTitle("Mural do grupo")
        if (group.joined) PrimaryButton("+  Publicar no grupo", {
            state.postingGroup = group.name
            state.navigate(Destination.CreatePost)
        }) else Notice("Entre no grupo para publicar. As publicações podem ser lidas sem participar.")
        val posts = state.posts.filter { it.group == group.name }
        if (posts.isEmpty()) EmptyState("Ainda não há publicações neste grupo.")
        posts.forEach { PostCard(it, state) }
    }
}
