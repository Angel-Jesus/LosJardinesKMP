package com.pe.losjardines.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.pe.losjardines.components.buttom.ButtonAJ
import com.pe.losjardines.components.dropdown.DropDownAJ
import com.pe.losjardines.components.picker.NativeDatePicker
import com.pe.losjardines.components.textInput.InputType
import com.pe.losjardines.components.textInput.TextInputAJ
import com.pe.losjardines.utils.toDateStringResult
import com.pe.losjardines.utils.toLocalDate
import com.pe.losjardines.values.AppTheme
import com.pe.losjardines.values.AppTypography
import com.pe.losjardines.values.BackgroundBrandInvertedColor
import com.pe.losjardines.values.BackgroundLightColor
import com.pe.losjardines.values.BrandIconColor
import com.pe.losjardines.values.LocalAppTypographyCore
import com.pe.losjardines.values.SoftTextColor
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Diálogo para actualizar un campo específico con soporte para distintos tipos de entrada.
 *
 * @param modifier      Modificador aplicado al contenedor externo del diálogo.
 * @param title         Texto principal del diálogo que indica el campo a actualizar (ej: "Actualizar nombre", "Cambiar correo").
 * @param descriptiion  Texto secundario que describe o da contexto sobre el campo a editar.
 * @param typeField     Define el tipo de entrada del campo. Controla si se muestra un texto, número,
 *                      selector u otro tipo de input. Por defecto [TypeField.TEXT].
 * @param value         Valor actual del campo que se muestra precargado en el input al abrir el diálogo.
 * @param listOption    Lista de opciones disponibles cuando [typeField] es de tipo selector.
 * @param aceptedEmpty  Variable que permite que se acepte valores vacio como salida. Por defecto [true].
 *                      Si no aplica, se pasa una lista vacía.
 * @param onDismiss     Lambda ejecutada cuando el usuario cancela o cierra el diálogo sin confirmar.
 * @param onConfirm     Lambda ejecutada cuando el usuario confirma el cambio, recibiendo el nuevo valor ingresado.
 * @param typography    Sistema tipográfico de la app. Se toma automáticamente del tema activo si no se especifica.
 */
@Composable
fun FieldUpdateDialog(
    modifier: Modifier = Modifier,
    title: String,
    descriptiion: String,
    typeField: TypeField = TypeField.TEXT,
    value: String,
    listOption: List<String> = emptyList(),
    aceptedEmpty: Boolean = true,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
    typography: AppTypography = LocalAppTypographyCore.current
){
    var valueState by rememberSaveable{ mutableStateOf(value) }
    var showDatePicker by rememberSaveable{ mutableStateOf(false) }

    if(showDatePicker){
        NativeDatePicker(
            initialDate = valueState.toLocalDate(),
            onDismiss = {
                showDatePicker = false
            },
            onDateSelected = {
                valueState = it.toDateStringResult()
            }
        )
    }

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(14.dp))
                .background(BackgroundLightColor)
                .padding(16.dp)
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = title,
                    style = typography.titleLarge
                )

                Text(
                    text = descriptiion,
                    style = typography.bodyLarge,
                    color = SoftTextColor
                )

                if(typeField != TypeField.DROP_DOWN){
                    TextInputAJ(
                        modifier = Modifier.padding(top = 8.dp),
                        value = valueState,
                        onValueChange = { newValue ->
                            valueState = newValue
                        },
                        onClick = {
                            showDatePicker = true
                        },
                        enableClickable = typeField == TypeField.DATE_PICKER,
                        readOnly = typeField == TypeField.DATE_PICKER,
                        trailingIcon = Icons.Default.CalendarMonth.takeIf { typeField == TypeField.DATE_PICKER },
                        inputType = typeField.getInputType(),
                    )
                } else {
                    DropDownAJ(
                        modifier = Modifier.padding(top = 8.dp),
                        options = listOption,
                        selectedOption = valueState,
                        onOptionSelected = {
                            valueState = it
                        }
                    )
                }

                Row(
                    modifier = Modifier.padding(top = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ButtonAJ(
                        modifier = Modifier.weight(1f),
                        enabled = (valueState.isNotEmpty() || aceptedEmpty) && valueState != value,
                        text = "Aceptar",
                        onClick = { onConfirm(valueState) }
                    )

                    ButtonAJ(
                        modifier = Modifier.weight(1f),
                        text = "Cancelar",
                        colors = ButtonDefaults.buttonColors(containerColor = BackgroundBrandInvertedColor, contentColor = BrandIconColor),
                        onClick = onDismiss
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun FieldUpdateDialogPreview(){
    AppTheme {
        FieldUpdateDialog(
            title = "Update Birthday",
            descriptiion = "Pick the date you'd like saved on your account.",
            value = "lunes",
            onDismiss = {},
            onConfirm = {},
            typeField = TypeField.DROP_DOWN
        )
    }
}

private fun TypeField.getInputType(): InputType {
    return when(this){
        TypeField.TEXT -> InputType.TEXT
        TypeField.TEXT_SPECIAL -> InputType.TEXT_SPECIAL
        TypeField.NUMBER -> InputType.NUMBER
        else -> InputType.TEXT
    }
}

enum class TypeField{
    TEXT,
    TEXT_SPECIAL,
    NUMBER,
    DATE_PICKER,
    DROP_DOWN
}