package com.pe.losjardines

import androidx.compose.ui.window.ComposeUIViewController
import com.pe.losjardines.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) { App() }