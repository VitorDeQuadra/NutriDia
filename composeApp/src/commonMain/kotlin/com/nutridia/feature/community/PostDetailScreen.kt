package com.nutridia.feature.community

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nutridia.core.components.*
import com.nutridia.data.*
import com.nutridia.navigation.Destination

@Composable
fun PostDetailScreen(state: AppState) {
    var comment by remember { mutableStateOf("") }
    var report by remember { mutableStateOf(false) }
    var deleting by remember { mutableStateOf(false) }
    val post = state.selectedPost
    Screen("Publicação", state::back, bottom = {
        if (post != null) Row(Modifier.padding(16.dp).imePadding(), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(comment, { if (it.length <= 500) comment = it }, Modifier.weight(1f),
                placeholder = { Text("Escreva um comentário...") }, shape = CardShape,
                supportingText = { if (comment.isNotEmpty()) Text("${comment.length}/500") })
            IconButton({ state.comment(comment); comment = "" }, enabled = comment.isNotBlank()) { Text("➤") }
        }
    }) {
        if (post == null) EmptyState("Esta publicação não está mais disponível.")
        else {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Avatar(post.author)
                Column(Modifier.weight(1f)) { Text(post.author, fontWeight = FontWeight.Bold); Text("${post.group} · ${post.time}", style = MaterialTheme.typography.bodySmall) }
                if (post.author == state.name) IconAction("×", "Excluir minha publicação") { deleting = true }
            }
            Text(post.text)
            if (post.image) Tile(highlighted = true) { Text("🥗", style = MaterialTheme.typography.displayLarge) }
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextButton({ state.toggleLike(post.id) }) { Text("${if (post.liked) "♥" else "♡"} ${post.likes}") }
                Text("💬 ${post.comments.size}")
                Spacer(Modifier.weight(1f))
                if (post.author != state.name) TextButton({ report = true }) { Text("⚑ Denunciar") }
            }
            HorizontalDivider()
            if (post.comments.isEmpty()) EmptyState("Seja a primeira pessoa a comentar.")
            post.comments.forEach { reply ->
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Avatar(reply.author)
                    Tile(Modifier.weight(1f)) {
                        Row { Text(reply.author, Modifier.weight(1f), fontWeight = FontWeight.Bold); Text(reply.time, style = MaterialTheme.typography.labelSmall) }
                        Text(reply.text)
                    }
                }
            }
        }
    }
    if (report) TextDialog("Denunciar publicação", label = "Motivo obrigatório", onDismiss = { report = false }) {
        report = false; state.unsupported("Envio de denúncia para moderação")
    }
    if (deleting && post != null) AlertDialog(onDismissRequest = { deleting = false }, title = { Text("Excluir publicação?") },
        text = { Text("A publicação será removida apenas desta sessão demonstrativa.") },
        confirmButton = { TextButton({ state.posts = state.posts.filter { it.id != post.id }; state.root(Destination.Community) }) { Text("Excluir") } },
        dismissButton = { TextButton({ deleting = false }) { Text("Cancelar") } })
}
