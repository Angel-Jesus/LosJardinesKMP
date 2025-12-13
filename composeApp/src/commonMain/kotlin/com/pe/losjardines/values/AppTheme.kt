package com.pe.losjardines.values

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.pe.losjardines.values.AppTypographyCore
import com.pe.losjardines.values.LocalAppTypographyCore

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    val typography = AppTypographyCore()

    CompositionLocalProvider(
        LocalAppTypographyCore provides typography
    ){
        MaterialTheme(
            colorScheme = MaterialTheme.colorScheme, // Puedes personalizar colores también si quieres
            typography = Typography(
                titleLarge = typography.titleLarge,
                bodyLarge = typography.bodyLarge
            ),
            content = content
        )
    }

}