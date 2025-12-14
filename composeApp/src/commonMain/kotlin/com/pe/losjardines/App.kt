package com.pe.losjardines

import androidx.compose.runtime.Composable
import com.pe.losjardines.navigation.NavManager
import com.pe.losjardines.values.AppTheme
import com.pe.losjardines.presentation.login.screen.LoginScreen

@Composable
fun App() {
    AppTheme {
        NavManager()
    }
}