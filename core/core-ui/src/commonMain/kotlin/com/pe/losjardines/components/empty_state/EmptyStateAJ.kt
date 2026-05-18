package com.pe.losjardines.components.empty_state

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pe.losjardines.values.AppTypography
import com.pe.losjardines.values.LocalAppTypographyCore
import com.pe.losjardines.values.SoftTextColor
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

/**
 * Componente que muestra un estado vacío cuando no hay contenido disponible para presentar al usuario.
 *
 * @param modifier     Modificador de Compose para controlar el layout externo del componente.
 * @param image        Recurso de imagen opcional del tipo DrawableResource que ilustra visualmente el estado vacío. Si es null, no se muestra imagen.
 * @param imageSize    Tamaño de la imagen del estado vacío.
 * @param title        Texto principal que indica al usuario que no hay contenido disponible (ej: "Sin resultados", "Lista vacía").
 * @param description  Texto secundario que explica el motivo o sugiere una acción al usuario (ej: "Intenta con otros filtros").
 * @param typography   Sistema tipográfico de la app. Se toma automáticamente del tema activo si no se especifica.
 */
@Composable
fun EmptyStateAJ(
    modifier: Modifier = Modifier,
    image: DrawableResource? = null,
    imageSize: Dp = 48.dp,
    title: String,
    description: String,
    typography: AppTypography = LocalAppTypographyCore.current
){
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        image?.let {
            Image(
                modifier = Modifier.size(imageSize),
                painter = painterResource(it),
                contentDescription = null
            )
        }

        Text(
            text = title,
            style = typography.titleMedium,
            textAlign = TextAlign.Center
        )

        Text(
            text = description,
            style = typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = SoftTextColor
        )
    }
}

/**
 * Componente que muestra un estado vacío cuando no hay contenido disponible para presentar al usuario.
 *
 * @param modifier     Modificador de Compose para controlar el layout externo del componente.
 * @param image        Recurso de imagen opcional del tipo ImageVector que ilustra visualmente el estado vacío. Si es null, no se muestra imagen.
 * @param imageSize    Tamaño de la imagen del estado vacío.
 * @param title        Texto principal que indica al usuario que no hay contenido disponible (ej: "Sin resultados", "Lista vacía").
 * @param description  Texto secundario que explica el motivo o sugiere una acción al usuario (ej: "Intenta con otros filtros").
 * @param typography   Sistema tipográfico de la app. Se toma automáticamente del tema activo si no se especifica.
 */
@Composable
fun EmptyStateAJ(
    modifier: Modifier = Modifier,
    image: ImageVector? = null,
    imageSize: Dp = 64.dp,
    title: String,
    description: String,
    typography: AppTypography = LocalAppTypographyCore.current
){
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        image?.let {
            Icon(
                modifier = Modifier.size(imageSize),
                imageVector = it,
                contentDescription = null
            )
        }

        Text(
            text = title,
            style = typography.titleMedium,
            textAlign = TextAlign.Center
        )

        Text(
            text = description,
            style = typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = SoftTextColor
        )
    }
}