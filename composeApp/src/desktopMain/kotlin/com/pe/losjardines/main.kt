package com.pe.losjardines

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.pe.losjardines.di.initKoin


fun main() = application {
    initKoin()

    Window(
        onCloseRequest = ::exitApplication,
        alwaysOnTop = true,
        title = "LosJardinesKMP",
    ) {
        App()
    }
}