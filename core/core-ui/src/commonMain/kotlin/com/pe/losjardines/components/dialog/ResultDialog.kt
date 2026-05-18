package com.pe.losjardines.components.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.pe.losjardines.values.AppTypography
import com.pe.losjardines.values.BackgroundLightColor
import com.pe.losjardines.values.BlackTextColor
import com.pe.losjardines.values.GrayTextColor
import com.pe.losjardines.values.LocalAppTypographyCore
import kotlinx.coroutines.delay
import losjardineskmp.core.core_ui.generated.resources.Res
import losjardineskmp.core.core_ui.generated.resources.ic_error
import losjardineskmp.core.core_ui.generated.resources.ic_success
import org.jetbrains.compose.resources.painterResource

/**
 * Diálogo de resultado que muestra el estado de una operación al usuario.
 *
 * @param modifier        Modificador de Compose para controlar el layout externo del diálogo.
 * @param title           Texto principal del diálogo que resume el resultado (ej: "¡Operación exitosa!", "Error al procesar").
 * @param description     Texto secundario opcional con detalles adicionales sobre el resultado. Si es null, no se muestra.
 * @param isSuccess       Indica el tipo de resultado. Si es true muestra estilo de éxito, si es false muestra estilo de error.
 * @param visibility      Controla si el diálogo es visible o no en pantalla.
 * @param timeVisibility  Duración en milisegundos que el diálogo permanece visible antes de cerrarse automáticamente.
 * @param onDismiss       Lambda que se ejecuta cuando el diálogo se cierra, ya sea automáticamente o por acción del usuario.
 * @param typography      Sistema tipográfico de la app. Se toma automáticamente del tema activo si no se especifica.
 */
@Composable
fun ResultDialog(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    isSuccess: Boolean = true,
    visibility: Boolean,
    timeVisibility: Long = 1000,
    onDismiss: () -> Unit,
    typography: AppTypography = LocalAppTypographyCore.current
){
    val imageState = Res.drawable.ic_success.takeIf{ isSuccess } ?: Res.drawable.ic_error

    LaunchedEffect(visibility){
        if(visibility){
            delay(timeVisibility)
            onDismiss()
        }
    }

    if(visibility){
        Dialog(
            onDismissRequest = onDismiss
        ){
            Column(
                modifier = modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(BackgroundLightColor)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Image(
                    modifier = Modifier.size(48.dp),
                    painter = painterResource(imageState),
                    contentDescription = null
                )

                Text(
                    text = title,
                    style = typography.headerSmall,
                    color = BlackTextColor
                )

                if(!description.isNullOrEmpty()) {
                    Text(
                        text = description,
                        style = typography.bodyLarge,
                        color = GrayTextColor,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

}