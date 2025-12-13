package com.pe.losjardines

import androidx.compose.ui.window.ComposeUIViewController
import com.pe.losjardines.di.initKoinModularization

fun MainViewController() = ComposeUIViewController(
    configure = { initKoinModularization() }
) { App() }