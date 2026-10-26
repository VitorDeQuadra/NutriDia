// ORIGEM: CODEX | Data de criacao: 2026-10-20
package com.nutridia.core.components

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable

@Composable actual fun BackNavigation(enabled: Boolean, onBack: () -> Unit) { BackHandler(enabled, onBack) }
