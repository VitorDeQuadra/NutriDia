package com.nutridia.core.components

import androidx.compose.runtime.Composable

@Composable expect fun BackNavigation(enabled: Boolean, onBack: () -> Unit)
