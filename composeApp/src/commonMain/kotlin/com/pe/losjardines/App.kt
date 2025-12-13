package com.pe.losjardines

import androidx.compose.runtime.Composable
import com.pe.losjardines.values.AppTheme
import com.pe.losjardines.presentation.screen.LoginScreenModule

@Composable
fun App() {
    AppTheme {
        LoginScreenModule()
    }
}