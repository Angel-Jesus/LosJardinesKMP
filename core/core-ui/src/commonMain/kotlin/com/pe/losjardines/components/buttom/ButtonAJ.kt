package com.pe.losjardines.components.buttom

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.pe.losjardines.values.AppTypography
import com.pe.losjardines.values.BackgroundBrandColor
import com.pe.losjardines.values.BackgroundBrandInvertedColor
import com.pe.losjardines.values.BrandIconColor
import com.pe.losjardines.values.LocalAppTypographyCore
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

/**
 * Botón personalizado que sigue los lineamientos visuales de la marca AJ.
 *
 * @param modifier   Modificador de Compose para controlar el layout externo del botón (tamaño, padding, alineación).
 * @param enabled    Indica si el botón es interactuable. Si es false, se muestra deshabilitado y no responde a toques.
 * @param text       Texto visible dentro del botón que describe la acción a realizar (ej: "Confirmar", "Enviar").
 * @param onClick    Lambda que se ejecuta al pulsar el botón. Contiene la lógica de la acción correspondiente.
 * @param colors     Colores del botón en sus distintos estados. Por defecto usa BackgroundBrandColor como fondo.
 * @param typography Sistema tipográfico de la app. Se toma automáticamente del tema activo si no se especifica.
 */
@Composable
fun ButtonAJ(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    onClick: () -> Unit,
    colors: ButtonColors = ButtonDefaults.buttonColors(containerColor = BackgroundBrandColor),
    typography: AppTypography = LocalAppTypographyCore.current
){
    Button(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
        colors = colors,
        shape = RoundedCornerShape(8.dp)
    ){
        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            text = text,
            style = typography.titleMedium
        )
    }
}

/**
 * Botón personalizado que sigue los lineamientos visuales de la marca AJ.
 *
 * @param modifier   Modificador de Compose para controlar el layout externo del botón (tamaño, padding, alineación).
 * @param enabled    Indica si el botón es interactuable. Si es false, se muestra deshabilitado y no responde a toques.
 * @param icon       Icono del tipo DrawableResource visible dentro del botón que describe la acción a realizar.
 * @param onClick    Lambda que se ejecuta al pulsar el botón. Contiene la lógica de la acción correspondiente.
 * @param colors     Colores del botón en sus distintos estados. Por defecto usa BackgroundLightColor como fondo.
 */
@Composable
fun IconButtonAJ(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: DrawableResource,
    onClick: () -> Unit,
    colors: ButtonColors = ButtonDefaults.buttonColors(containerColor = BackgroundBrandInvertedColor)
){
    Button(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
        colors = colors,
        shape = RoundedCornerShape(8.dp),
        elevation = ButtonDefaults.buttonElevation(2.dp)
    ){
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(resource = icon),
            contentDescription = null
        )
    }
}

/**
 * Botón personalizado que sigue los lineamientos visuales de la marca AJ.
 *
 * @param modifier   Modificador de Compose para controlar el layout externo del botón (tamaño, padding, alineación).
 * @param enabled    Indica si el botón es interactuable. Si es false, se muestra deshabilitado y no responde a toques.
 * @param icon       Icono del tipo ImageVector visible dentro del botón que describe la acción a realizar.
 * @param onClick    Lambda que se ejecuta al pulsar el botón. Contiene la lógica de la acción correspondiente.
 * @param colors     Colores del botón en sus distintos estados. Por defecto usa BackgroundLightColor como fondo.
 */
@Composable
fun IconButtonAJ(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector,
    onClick: () -> Unit,
    colors: ButtonColors = ButtonDefaults.buttonColors(containerColor = BackgroundBrandInvertedColor, contentColor = BrandIconColor)
){
    Button(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
        colors = colors,
        shape = RoundedCornerShape(8.dp),
        elevation = ButtonDefaults.buttonElevation(2.dp)
    ){
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = icon,
            contentDescription = null
        )
    }
}