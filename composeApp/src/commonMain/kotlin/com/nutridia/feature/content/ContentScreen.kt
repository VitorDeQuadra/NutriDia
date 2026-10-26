// ORIGEM: CODEX | Data de criacao: 2026-10-20
package com.nutridia.feature.content

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
private fun ContentCard(item: ContentItem, state: AppState, modifier: Modifier = Modifier, featured: Boolean = false) {
    Surface(modifier, shape = CardShape, color = cardColor(), onClick = { state.selectedContent = item; state.navigate(Destination.RecipeDetail) }) {
        Column {
            Surface(Modifier.fillMaxWidth().height(if (featured) 110.dp else 90.dp), color = MaterialTheme.colorScheme.primaryContainer) {
                Box(contentAlignment = Alignment.Center) { Text(item.symbol, fontSize = if (featured) 42.sp else 32.sp) }
            }
            Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(item.title, fontSize = if (featured) 19.sp else 16.sp, fontWeight = FontWeight.Bold)
                Text("${if (item.recipe) "Receita" else "Artigo"} · ${item.minutes} min${if (featured && item.recipe) " · ${item.kcal} kcal" else ""}", style = MaterialTheme.typography.bodySmall)
                if (featured) Text(item.tags.joinToString(" · "), style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
fun ContentScreen(state: AppState) {
    var category by remember { mutableStateOf("") }
    var filters by remember { mutableStateOf(state.restrictions) }
    var query by remember { mutableStateOf("") }
    var searching by remember { mutableStateOf(false) }
    val contents = DemoData.contents.filter { item ->
        (!state.savedOnly || item.id in state.savedContent) && item.title.contains(query, true) &&
            (category.isEmpty() || category == "Receitas" && item.recipe || category == "Artigos" && !item.recipe) && filters.all { it in item.tags }
    }
    Screen(if (state.savedOnly) "Conteúdos salvos" else "Conteúdo", if (state.savedOnly) state::back else null,
        action = { IconAction("🔍", "Pesquisar conteúdo") { searching = !searching } }, bottom = { BottomNavigation(state, Destination.Content) }) {
        if (searching) EntryField(query, { query = it }, "Pesquisar conteúdo")
        Chips(listOf("Sem lactose", "Receitas", "Artigos", "Sem glúten") + state.restrictions.filterNot { it in setOf("Sem lactose", "Sem glúten") }, filters + category) {
            if (it == "Receitas" || it == "Artigos") category = if (category == it) "" else it
            else filters = if (it in filters) filters - it else filters + it
        }
        Text("Filtros aplicados a partir das suas restrições", style = MaterialTheme.typography.bodySmall)
        if (contents.isEmpty()) EmptyState("Nenhum conteúdo com esses filtros. Ajuste as categorias ou as restrições.")
        contents.firstOrNull()?.let { ContentCard(it, state, Modifier.fillMaxWidth(), featured = true) }
        contents.drop(1).chunked(2).forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                row.forEach { ContentCard(it, state, Modifier.weight(1f)) }
                if (row.size == 1) Spacer(Modifier.weight(1f))
            }
        }
    }
}
