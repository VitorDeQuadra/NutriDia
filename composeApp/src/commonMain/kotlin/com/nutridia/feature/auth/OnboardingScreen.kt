// ORIGEM: CODEX | Data de criacao: 2026-10-20
package com.nutridia.feature.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nutridia.core.components.*

@Composable
fun OnboardingScreen(onContinue: () -> Unit) {
    BoxWithConstraints(Modifier.fillMaxSize().safeDrawingPadding()) {
    val viewportHeight = maxHeight
    Column(Modifier.fillMaxWidth().verticalScroll(rememberScrollState()).heightIn(min = viewportHeight).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(20.dp)) {
        TextButton(onContinue, Modifier.align(Alignment.End)) { Text("Pular", fontWeight = FontWeight.Bold) }
        Surface(Modifier.fillMaxWidth().heightIn(min = 180.dp, max = 300.dp).aspectRatio(1.45f),
            shape = CardShape, color = MaterialTheme.colorScheme.primaryContainer) {
            Box(contentAlignment = Alignment.Center) { Text("🍲", fontSize = 76.sp) }
        }
        Text("Registre sem complicação", fontSize = 25.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        Text("Anote o que comeu em menos de 30 segundos, pela busca, pelos favoritos ou por foto do prato.",
            textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(26.dp, 7.dp).background(MaterialTheme.colorScheme.primary, CircleShape))
            repeat(2) { Box(Modifier.size(7.dp).background(MaterialTheme.colorScheme.outline.copy(alpha = .35f), CircleShape)) }
        }
        Spacer(Modifier.weight(1f))
        PrimaryButton("Continuar", onContinue)
    }
    }
}
