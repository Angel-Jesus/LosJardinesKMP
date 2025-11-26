package com.pe.losjardines

import androidx.compose.runtime.Composable
import com.pe.losjardines.core.values.AppTheme
import com.pe.losjardines.presentation.LoginScreen

@Composable
fun App() {
    AppTheme {
        LoginScreen()
        /*
        Navigator(screen = HomeScreen()){ navigator ->
            SlideTransition(navigator)
        }
         */
    }
}