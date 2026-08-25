package com.pe.losjardines.preview.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.pe.losjardines.presentation.login.contract.LoginState
import com.pe.losjardines.presentation.login.screen.LoginContent
import com.pe.losjardines.values.AppTheme
import com.pe.losjardines.values.BackgroundBrandLightColor
import com.pe.losjardines.values.BrandTextColor
import com.pe.losjardines.values.DividerColor
import com.pe.losjardines.values.GrayTextColor

/**
 * Una entrada de la galería: un nombre y el composable que la renderiza.
 *
 * @param name texto que se muestra en la barra lateral.
 * @param content composable sin estado a renderizar (el `<Feature>Content`, nunca el que usa
 * `koinViewModel()`).
 */
data class PreviewEntry(
    val name: String,
    val content: @Composable () -> Unit
)

/**
 * Registro de todo lo que muestra la galería.
 *
 * Para añadir una pantalla: marca su `<Feature>Content` como `internal` (no `private`) y agrega
 * aquí una entrada por cada estado que quieras revisar en escritorio.
 */
private val previewEntries: List<PreviewEntry> = listOf(
    PreviewEntry("Login · vacío") {
        LoginContent(
            isMobile = false,
            uiState = LoginState(),
            dispatcherEvent = {}
        )
    }
)

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Los Jardines · Galería de previews (desktop)",
        state = rememberWindowState(width = 1280.dp, height = 800.dp)
    ) {
        AppTheme {
            GalleryContent(entries = previewEntries)
        }
    }
}

@Composable
private fun GalleryContent(entries: List<PreviewEntry>) {
    var selected by remember { mutableStateOf(entries.firstOrNull()) }

    Row(modifier = Modifier.fillMaxSize().background(Color.White)) {
        GallerySidebar(
            entries = entries,
            selected = selected,
            onSelect = { selected = it }
        )

        VerticalDivider(color = DividerColor)

        Box(modifier = Modifier.fillMaxSize()) {
            selected?.content?.invoke()
        }
    }
}

@Composable
private fun GallerySidebar(
    entries: List<PreviewEntry>,
    selected: PreviewEntry?,
    onSelect: (PreviewEntry) -> Unit
) {
    Column(modifier = Modifier.width(260.dp).fillMaxHeight()) {
        Text(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            text = "Previews",
            color = BrandTextColor
        )

        HorizontalDivider(color = DividerColor)

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            items(entries) { entry ->
                val isSelected = entry == selected
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelect(entry) }
                        .background(BackgroundBrandLightColor.takeIf { isSelected } ?: Color.White)
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    text = entry.name,
                    color = BrandTextColor.takeIf { isSelected } ?: GrayTextColor
                )
            }
        }
    }
}
