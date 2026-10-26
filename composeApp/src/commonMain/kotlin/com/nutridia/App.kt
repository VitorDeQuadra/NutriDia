package com.nutridia

import androidx.compose.runtime.Composable
import com.nutridia.core.theme.NutriDiaTheme
// INICIO ALTERACAO CODEX | Data: 2026-10-20
import androidx.compose.runtime.remember
import androidx.compose.foundation.isSystemInDarkTheme
import com.nutridia.data.AppState
import com.nutridia.navigation.NutriNavHost
// FIM ALTERACAO CODEX

/**
 * Ponto de entrada compartilhado (Android + Web).
 * Por enquanto abre direto no Login; o grafo de navegação entra depois.
 */
@Composable
fun App() {
    // INICIO ALTERACAO CODEX | Data: 2026-10-20
    val state = remember { AppState() }
    val dark = when (state.theme) { "Claro" -> false; "Escuro" -> true; else -> isSystemInDarkTheme() }
    NutriDiaTheme(darkTheme = dark) {
        NutriNavHost(state)
    // FIM ALTERACAO CODEX
    }
}
