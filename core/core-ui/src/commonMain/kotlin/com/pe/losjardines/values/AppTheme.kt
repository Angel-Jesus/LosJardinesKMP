package com.pe.losjardines.values

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalAppTypographyCore provides AppTypographyCore()
    ) {
        content()
    }
}