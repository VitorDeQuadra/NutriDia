package com.nutridia

import androidx.compose.runtime.Composable
import com.nutridia.core.theme.NutriDiaTheme
import androidx.compose.runtime.remember
import androidx.compose.foundation.isSystemInDarkTheme
import com.nutridia.data.AppState
import com.nutridia.navigation.NutriNavHost

/**
 * Ponto de entrada compartilhado (Android + Web).
 * Por enquanto abre direto no Login; o grafo de navegação entra depois.
 */
@Composable
fun App() {
    val state = remember { AppState() }
    val dark = when (state.theme) { "Claro" -> false; "Escuro" -> true; else -> isSystemInDarkTheme() }
    NutriDiaTheme(darkTheme = dark) {
        NutriNavHost(state)
    }
}
