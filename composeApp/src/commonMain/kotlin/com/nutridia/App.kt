package com.nutridia

import androidx.compose.runtime.Composable
import com.nutridia.core.theme.NutriDiaTheme
import com.nutridia.feature.auth.LoginScreen

/**
 * Ponto de entrada compartilhado (Android + Web).
 * Por enquanto abre direto no Login; o grafo de navegação entra depois.
 */
@Composable
fun App() {
    NutriDiaTheme {
        LoginScreen()
    }
}
