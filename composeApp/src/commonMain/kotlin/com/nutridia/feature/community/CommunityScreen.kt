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
import com.nutridia.data.*
import com.nutridia.navigation.*

@Composable
fun PostCard(post: Post, state: AppState) {
    Tile(onClick = { state.selectedPostId = post.id; state.navigate(Destination.PostDetail) }) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Avatar(post.author)
            Column(Modifier.weight(1f)) {
                Text(post.author, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                if (post.professional) Text("Nutricionista", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelSmall)
                Text("${if (post.group.isNotBlank()) "Grupo ${post.group} · " else ""}${post.time}", style = MaterialTheme.typography.bodySmall)
            }
        }
        Text(post.text, lineHeight = 23.sp)
        if (post.image) Surface(Modifier.fillMaxWidth().height(138.dp), shape = CardShape, color = MaterialTheme.colorScheme.primaryContainer) {
            Box(contentAlignment = Alignment.Center) { Text("🥗", fontSize = 48.sp) }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextButton({ state.toggleLike(post.id) }) { Text("${if (post.liked) "♥" else "♡"} ${post.likes}") }
            TextButton({ state.selectedPostId = post.id; state.navigate(Destination.PostDetail) }) { Text("💬 ${post.comments.size}") }
            Spacer(Modifier.weight(1f))
            IconAction("⋯", "Ações da publicação") { state.selectedPostId = post.id; state.navigate(Destination.PostDetail) }
        }
    }
}

@Composable
fun CommunityScreen(state: AppState) {
    var filter by remember { mutableStateOf("Todos") }
    Screen("Comunidade", action = { IconAction("🔔", "Notificações") { state.navigate(Destination.Notifications) } },
        bottom = { BottomNavigation(state, Destination.Community) }) {
        Chips(listOf("Todos", "Iniciantes", "Sem lactose"), setOf(filter)) { filter = it }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlineButton("Grupos", { state.navigate(Destination.Groups) }, Modifier.weight(1f))
            OutlineButton("Desafios", { state.navigate(Destination.Challenges) }, Modifier.weight(1f))
        }
        PrimaryButton("+  Nova publicação", { state.postingGroup = null; state.navigate(Destination.CreatePost) })
        val posts = state.posts.filter { filter == "Todos" || it.group == filter }
        if (posts.isEmpty()) EmptyState("Ainda não há publicações neste grupo. Compartilhe uma dica ou uma conquista.")
        posts.forEach { PostCard(it, state) }
    }
}
