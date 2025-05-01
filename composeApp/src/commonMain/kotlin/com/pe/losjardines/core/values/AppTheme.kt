package com.pe.losjardines.core.values

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

val LocalAppTypography = staticCompositionLocalOf<AppTypography> {
    error("No Typography provided")
}

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    val typography = AppTypography()

    CompositionLocalProvider(
        LocalAppTypography provides typography
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