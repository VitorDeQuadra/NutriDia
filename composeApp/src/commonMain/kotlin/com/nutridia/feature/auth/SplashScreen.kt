// ORIGEM: CODEX | Data de criacao: 2026-10-20
package com.nutridia.feature.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onReady: () -> Unit) {
    val ready by rememberUpdatedState(onReady)
    LaunchedEffect(Unit) { delay(900); ready() }
    Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).safeDrawingPadding().padding(24.dp)) {
        Column(Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Surface(shape = RoundedCornerShape(28.dp), color = MaterialTheme.colorScheme.primary, modifier = Modifier.size(96.dp)) {
                Box(contentAlignment = Alignment.Center) { Text("🥗", fontSize = 42.sp) }
            }
            Text("NutriDia", fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Text("Seu diário e sua comunidade", color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(8.dp))
            CircularProgressIndicator(Modifier.size(36.dp), strokeWidth = 4.dp)
        }
        Text("Preparando demonstração...", Modifier.align(Alignment.BottomCenter), fontSize = 14.sp)
    }
}
