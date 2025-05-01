package com.pe.losjardines

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.pe.losjardines.core.values.AppTheme
import com.pe.losjardines.presentation.screen.HomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    AppTheme {
        Navigator(screen = HomeScreen()){ navigator ->
            SlideTransition(navigator)
        }
    }
}