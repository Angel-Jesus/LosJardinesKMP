package com.pe.losjardines.components.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.pe.losjardines.components.buttom.ButtonAJ
import com.pe.losjardines.values.AppTheme
import com.pe.losjardines.values.AppTypography
import com.pe.losjardines.values.BackgroundBrandInvertedColor
import com.pe.losjardines.values.BackgroundLightColor
import com.pe.losjardines.values.BlackTextColor
import com.pe.losjardines.values.BrandIconColor
import com.pe.losjardines.values.GrayTextColor
import com.pe.losjardines.values.LocalAppTypographyCore
import kotlinx.coroutines.delay
import losjardineskmp.core.core_ui.generated.resources.Res
import losjardineskmp.core.core_ui.generated.resources.ic_error
import losjardineskmp.core.core_ui.generated.resources.ic_success
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

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
            ResultDialogContent(
                modifier = modifier,
                title = title,
                description = description,
                isSuccess = isSuccess,
                typography = typography
            )
        }
    }

}

/**
 * Contenido visual de [ResultDialog] sin el envoltorio [Dialog]. Se extrae para poder
 * previsualizarlo con `@Preview`, ya que los composables `Dialog` no se renderizan en el preview.
 */
@Composable
private fun ResultDialogContent(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    isSuccess: Boolean = true,
    typography: AppTypography = LocalAppTypographyCore.current
){
    val imageState = Res.drawable.ic_success.takeIf{ isSuccess } ?: Res.drawable.ic_error

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

/**
 * Diálogo de resultado con acciones. Muestra el estado de una operación e incluye dos botones
 * (primario y secundario) cuyo texto es configurable, para que el usuario decida el siguiente paso.
 *
 * A diferencia de [ResultDialog], este diálogo no se cierra automáticamente: permanece visible
 * hasta que el usuario pulsa alguno de los botones.
 *
 * @param modifier              Modificador de Compose para controlar el layout externo del diálogo.
 * @param title                 Texto principal del diálogo que resume el resultado.
 * @param description           Texto secundario opcional con detalles adicionales. Si es null, no se muestra.
 * @param primaryButtonText     Texto del botón primario (acción principal, ej: "Aceptar", "Reintentar").
 * @param secondaryButtonText   Texto del botón secundario (acción alternativa, ej: "Cancelar", "Volver").
 * @param onPrimaryClick        Lambda ejecutada al pulsar el botón primario.
 * @param onSecondaryClick      Lambda ejecutada al pulsar el botón secundario.
 * @param onDismiss             Lambda ejecutada cuando el diálogo se cierra por gesto o botón de retroceso.
 * @param typography            Sistema tipográfico de la app. Se toma automáticamente del tema activo si no se especifica.
 */
@Composable
fun ResultActionDialog(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    primaryButtonText: String,
    secondaryButtonText: String,
    onPrimaryClick: () -> Unit,
    onSecondaryClick: () -> Unit = {},
    onDismiss: () -> Unit,
    typography: AppTypography = LocalAppTypographyCore.current
){
    Dialog(
        onDismissRequest = onDismiss
    ){
        ResultActionDialogContent(
            modifier = modifier,
            title = title,
            description = description,
            primaryButtonText = primaryButtonText,
            secondaryButtonText = secondaryButtonText,
            onPrimaryClick = onPrimaryClick,
            onSecondaryClick = onSecondaryClick,
            typography = typography
        )
    }
}

/**
 * Contenido visual de [ResultActionDialog] sin el envoltorio [Dialog]. Se extrae para poder
 * previsualizarlo con `@Preview`, ya que los composables `Dialog` no se renderizan en el preview.
 */
@Composable
private fun ResultActionDialogContent(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    primaryButtonText: String,
    secondaryButtonText: String,
    onPrimaryClick: () -> Unit,
    onSecondaryClick: () -> Unit = {},
    typography: AppTypography = LocalAppTypographyCore.current
){
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(BackgroundLightColor)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = title,
            style = typography.headerSmall,
            color = BlackTextColor
        )

        if(!description.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                style = typography.bodyLarge,
                color = GrayTextColor,
                textAlign = TextAlign.Center
            )
        }

        Row(
            modifier = Modifier.padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ButtonAJ(
                modifier = Modifier.weight(1f),
                text = primaryButtonText,
                onClick = onPrimaryClick
            )

            ButtonAJ(
                modifier = Modifier.weight(1f),
                text = secondaryButtonText,
                colors = ButtonDefaults.buttonColors(containerColor = BackgroundBrandInvertedColor, contentColor = BrandIconColor),
                onClick = onSecondaryClick
            )
        }
    }
}

@Preview
@Composable
private fun ResultDialogSuccessPreview() {
    AppTheme {
        Box(modifier = Modifier.background(Color.White).padding(16.dp)) {
            ResultDialogContent(
                title = "¡Operación exitosa!",
                description = "Información del cliente guardada correctamente",
                isSuccess = true
            )
        }
    }
}

@Preview
@Composable
private fun ResultDialogErrorPreview() {
    AppTheme {
        Box(modifier = Modifier.background(Color.White).padding(16.dp)) {
            ResultDialogContent(
                title = "Error al procesar",
                description = "No se pudo guardar la información. Inténtalo nuevamente.",
                isSuccess = false
            )
        }
    }
}

@Preview
@Composable
private fun ResultDialogWithoutDescriptionPreview() {
    AppTheme {
        Box(modifier = Modifier.background(Color.White).padding(16.dp)) {
            ResultDialogContent(
                title = "¡Operación exitosa!",
                description = null,
                isSuccess = true
            )
        }
    }
}

@Preview
@Composable
private fun ResultActionDialogPreview() {
    AppTheme {
        Box(modifier = Modifier.background(Color.White).padding(16.dp)) {
            ResultActionDialogContent(
                title = "¿Deseas continuar?",
                description = "Se registrará la información del cliente con los datos ingresados.",
                primaryButtonText = "Aceptar",
                secondaryButtonText = "Cancelar",
                onPrimaryClick = {},
                onSecondaryClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun ResultActionDialogWithoutDescriptionPreview() {
    AppTheme {
        Box(modifier = Modifier.background(Color.White).padding(16.dp)) {
            ResultActionDialogContent(
                title = "¿Deseas continuar?",
                description = null,
                primaryButtonText = "Reintentar",
                secondaryButtonText = "Volver",
                onPrimaryClick = {},
                onSecondaryClick = {}
            )
        }
    }
}
