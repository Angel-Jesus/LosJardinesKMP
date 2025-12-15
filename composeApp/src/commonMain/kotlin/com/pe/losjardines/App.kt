package com.pe.losjardines

import androidx.compose.runtime.Composable
import com.pe.losjardines.navigation.NavManager
import com.pe.losjardines.values.AppTheme

@Composable
fun App() {
    AppTheme {
        NavManager()
    }
}