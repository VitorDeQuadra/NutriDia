// ORIGEM: CODEX | Data de criacao: 2026-10-20
package com.nutridia.core.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val CardShape = RoundedCornerShape(18.dp)
val PillShape = RoundedCornerShape(50)

@Composable fun cardColor(): Color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = .38f)

@Composable
fun Screen(
    title: String,
    onBack: (() -> Unit)? = null,
    action: @Composable RowScope.() -> Unit = {},
    bottom: @Composable () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            Column {
                Row(Modifier.fillMaxWidth().heightIn(min = 60.dp).padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    if (onBack != null) IconAction("‹", "Voltar", onBack)
                    Text(title, Modifier.weight(1f), fontSize = 21.sp, fontWeight = FontWeight.Bold)
                    action()
                }
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = .35f))
            }
        },
        bottomBar = bottom,
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).imePadding().verticalScroll(rememberScrollState())
            .padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp), content = content)
    }
}

@Composable
fun PrimaryButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true) {
    Button(onClick, modifier.fillMaxWidth().heightIn(min = 50.dp), enabled = enabled,
        shape = PillShape, contentPadding = PaddingValues(horizontal = 18.dp, vertical = 12.dp)) {
        Text(text, fontSize = 17.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun OutlineButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier, danger: Boolean = false) {
    val color = if (danger) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
    OutlinedButton(onClick, modifier.heightIn(min = 48.dp), shape = PillShape,
        border = BorderStroke(1.dp, color), colors = ButtonDefaults.outlinedButtonColors(contentColor = color)) {
        Text(text, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun IconAction(glyph: String, label: String, onClick: () -> Unit) {
    IconButton(onClick, Modifier.semantics { contentDescription = label }) { Text(glyph, fontSize = 24.sp) }
}

@Composable
fun Glyph(symbol: String, modifier: Modifier = Modifier, highlight: Boolean = false) {
    Box(modifier.size(52.dp).clip(RoundedCornerShape(14.dp)).background(
        if (highlight) MaterialTheme.colorScheme.primary.copy(alpha = .12f) else cardColor()),
        contentAlignment = Alignment.Center) { Text(symbol, fontSize = 26.sp) }
}

@Composable
fun Avatar(name: String, modifier: Modifier = Modifier, large: Boolean = false) {
    Box(modifier.size(if (large) 80.dp else 44.dp).clip(CircleShape)
        .background(MaterialTheme.colorScheme.primaryContainer), contentAlignment = Alignment.Center) {
        Text(name.take(1).uppercase(), fontSize = if (large) 32.sp else 20.sp, fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer)
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(text.uppercase(), style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
}

@Composable
fun Tile(modifier: Modifier = Modifier, highlighted: Boolean = false, onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit) {
    val click = if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier
    Column(modifier.fillMaxWidth().clip(CardShape).background(
        if (highlighted) MaterialTheme.colorScheme.primaryContainer else cardColor()).then(click)
        .padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp), content = content)
}

@Composable
fun ListTile(symbol: String, title: String, subtitle: String = "", highlighted: Boolean = false,
    onClick: (() -> Unit)? = null, trailing: @Composable () -> Unit = {}) {
    Tile(highlighted = highlighted, onClick = onClick) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Glyph(symbol, highlight = highlighted)
            Column(Modifier.weight(1f)) {
                Text(title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                if (subtitle.isNotBlank()) Text(subtitle, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            trailing()
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Chips(options: List<String>, selected: Set<String>, onSelect: (String) -> Unit) {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
        options.forEach { label ->
            FilterChip(label in selected, { onSelect(label) }, label = {
                Text(label, fontSize = 15.sp, fontWeight = if (label in selected) FontWeight.Bold else FontWeight.Normal)
            }, shape = PillShape, colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer))
        }
    }
}

@Composable
fun EntryField(value: String, onChange: (String) -> Unit, label: String, modifier: Modifier = Modifier,
    numeric: Boolean = false, error: String? = null, enabled: Boolean = true) {
    OutlinedTextField(value, onChange, modifier.fillMaxWidth(), label = { Text(label) },
        singleLine = true, shape = RoundedCornerShape(14.dp), enabled = enabled, isError = error != null,
        supportingText = if (error != null) ({ Text(error) }) else null,
        keyboardOptions = KeyboardOptions(keyboardType = if (numeric) KeyboardType.Decimal else KeyboardType.Text))
}

@Composable
fun Notice(text: String) {
    Surface(shape = CardShape, border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = .4f)),
        color = Color.Transparent) { Text(text, Modifier.padding(16.dp), fontSize = 14.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant) }
}

@Composable
fun EmptyState(text: String) { Notice(text) }

@Composable
fun Metric(value: String, label: String, modifier: Modifier = Modifier) {
    Column(modifier.clip(CardShape).background(cardColor()).padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontSize = 23.sp, fontWeight = FontWeight.Bold)
        Text(label, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
fun ProgressLine(label: String, value: String, progress: Float) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Row { Text(label, Modifier.weight(1f)); Text(value, fontWeight = FontWeight.Bold) }
        LinearProgressIndicator(progress = { progress.coerceIn(0f, 1f) }, modifier = Modifier.fillMaxWidth().height(7.dp).clip(PillShape),
            color = MaterialTheme.colorScheme.primary, trackColor = MaterialTheme.colorScheme.outline.copy(alpha = .3f))
    }
}

@Composable
fun TextDialog(title: String, initial: String = "", label: String, numeric: Boolean = false,
    onDismiss: () -> Unit, validate: (String) -> String? = { null }, onConfirm: (String) -> Unit) {
    var value by remember { mutableStateOf(initial) }
    var error by remember { mutableStateOf<String?>(null) }
    AlertDialog(onDismissRequest = onDismiss, title = { Text(title) },
        text = { EntryField(value, { value = it; error = null }, label, numeric = numeric, error = error) },
        confirmButton = { TextButton({ error = validate(value); if (error == null) onConfirm(value) }, enabled = value.isNotBlank()) { Text("Confirmar") } },
        dismissButton = { TextButton(onDismiss) { Text("Cancelar") } })
}
