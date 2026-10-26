package com.nutridia.core.components

import androidx.compose.runtime.Composable

// A versão Web usa os controles visíveis de voltar; não altera o histórico do navegador.
@Composable actual fun BackNavigation(enabled: Boolean, onBack: () -> Unit) = Unit
