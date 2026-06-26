package com.pe.losjardines.components.textInput

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.pe.losjardines.components.textInput.values.TextInputAJColors
import com.pe.losjardines.values.AppTypography
import com.pe.losjardines.values.LocalAppTypographyCore
import losjardineskmp.core.core_ui.generated.resources.Res
import losjardineskmp.core.core_ui.generated.resources.visibility_lock_icon
import losjardineskmp.core.core_ui.generated.resources.visibility_off_icon
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

/**
 * Campo de texto personalizado con soporte para validación, íconos y modo contraseña.
 *
 * @param modifier          Modificador aplicado al contenedor externo [Column].
 * @param value             Estado actual del valor del texto
 * @param onValueChange     Callback invocado cada vez que el usuario modifica el contenido.
 *                          El texto ya viene filtrado según el [inputType] definido.
 * @param readOnly          Si es `true`, el campo muestra el valor pero no permite editarlo
 *                          ni abre el teclado.
 * @param enableClickable   Si es `true`, habilita la captura de clics sobre el campo mediante
 *                          una capa transparente superpuesta. Útil para campos que abren un
 *                          selector (fecha, hora, opciones).
 * @param onClick           Acción ejecutada al hacer clic cuando [enableClickable] es `true`.
 * @param label             Texto mostrado encima del campo como etiqueta descriptiva.
 *                          Si es `null`, no se renderiza.
 * @param placeholder       Texto de ayuda visible cuando el campo está vacío.
 *                          Si es `null`, no se renderiza.
 * @param enabled           Si es `false`, el campo queda deshabilitado visualmente y no
 *                          acepta interacción del usuario.
 * @param inputType         Define el tipo de entrada permitido. Controla tanto el teclado
 *                          mostrado como el filtro aplicado al texto ingresado.
 *                          Ver [InputType].
 * @param leadingIcon       Ícono decorativo al inicio del campo. Si es `null`, no se muestra.
 * @param trailingIcon      Ícono al final del campo. En modo contraseña es reemplazado
 *                          automáticamente por el ícono de visibilidad. Si es `null` y no
 *                          es contraseña, no se muestra.
 * @param colors            Colores personalizados del campo. Por defecto usa [TextInputAJColors].
 * @param isTypePassword    Si es `true`, el texto se oculta con una máscara y se agrega
 *                          un ícono para alternar la visibilidad.
 * @param typography        Tipografía utilizada en etiqueta, placeholder y texto ingresado.
 *                          Por defecto toma el valor del [LocalAppTypographyCore].
 */
@Composable
fun TextInputAJ(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit = {},
    readOnly: Boolean = false,
    enableClickable: Boolean = false,
    onClick: () -> Unit = {},
    label: String? = null,
    placeholder: String? = null,
    enabled: Boolean = true,
    inputType: InputType = InputType.TEXT,
    leadingIcon: Any? = null,
    trailingIcon: Any? = null,
    colors: TextInputAJColors = TextInputAJColors(),
    isTypePassword: Boolean = false,
    typography: AppTypography = LocalAppTypographyCore.current
) {
    val passwordVisible = remember { mutableStateOf(!isTypePassword) }

    Column(modifier = modifier) {
        label?.let {
            Text(
                text = it,
                style = typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Box {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = value,
                onValueChange = { raw ->
                    if(inputType.validateRaw(raw) || raw.isEmpty()) onValueChange(raw)
                },
                textStyle = typography.bodyMedium,
                visualTransformation = if (passwordVisible.value) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                placeholder = if (placeholder != null) {
                    {
                        Text(
                            text = placeholder,
                            color = colors.placeholderColor,
                            style = typography.titleSmall
                        )
                    }
                } else null,
                leadingIcon = if (leadingIcon != null) {
                    {
                        if(leadingIcon is DrawableResource){
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(resource = leadingIcon),
                                contentDescription = null,
                                tint = colors.leadingIconColor
                            )
                        } else if(leadingIcon is ImageVector) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = leadingIcon,
                                contentDescription = null,
                                tint = colors.leadingIconColor
                            )
                        }
                    }
                } else null,
                trailingIcon = isTypePassword.isPasswordCase(colors, trailingIcon, passwordVisible),
                enabled = enabled,
                readOnly = readOnly,
                keyboardOptions = KeyboardOptions(keyboardType = inputType.toKeyboardType()),
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colors.focusedContainerColor,
                    unfocusedContainerColor = colors.unfocusedContainerColor,
                    focusedPlaceholderColor = colors.focusedIndicatorColor,
                    focusedIndicatorColor = colors.focusedIndicatorColor
                )
            )

            if (enableClickable) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = onClick
                        )
                )
            }
        }
    }
}
/**
 * Campo de texto personalizado con soporte para validación, íconos y modo contraseña.
 *
 * @param modifier          Modificador aplicado al contenedor externo [Column].
 * @param value             Estado actual del campo como [TextFieldValue], incluye texto,
 *                          selección y posición del cursor.
 * @param onValueChange     Callback invocado cada vez que el usuario modifica el contenido.
 *                          El texto ya viene filtrado según el [inputType] definido.
 * @param readOnly          Si es `true`, el campo muestra el valor pero no permite editarlo
 *                          ni abre el teclado.
 * @param enableClickable   Si es `true`, habilita la captura de clics sobre el campo mediante
 *                          una capa transparente superpuesta. Útil para campos que abren un
 *                          selector (fecha, hora, opciones).
 * @param onClick           Acción ejecutada al hacer clic cuando [enableClickable] es `true`.
 * @param label             Texto mostrado encima del campo como etiqueta descriptiva.
 *                          Si es `null`, no se renderiza.
 * @param placeholder       Texto de ayuda visible cuando el campo está vacío.
 *                          Si es `null`, no se renderiza.
 * @param enabled           Si es `false`, el campo queda deshabilitado visualmente y no
 *                          acepta interacción del usuario.
 * @param inputType         Define el tipo de entrada permitido. Controla tanto el teclado
 *                          mostrado como el filtro aplicado al texto ingresado.
 *                          Ver [InputType].
 * @param leadingIcon       Ícono decorativo al inicio del campo. Si es `null`, no se muestra.
 * @param trailingIcon      Ícono al final del campo. En modo contraseña es reemplazado
 *                          automáticamente por el ícono de visibilidad. Si es `null` y no
 *                          es contraseña, no se muestra.
 * @param colors            Colores personalizados del campo. Por defecto usa [TextInputAJColors].
 * @param isTypePassword    Si es `true`, el texto se oculta con una máscara y se agrega
 *                          un ícono para alternar la visibilidad.
 * @param typography        Tipografía utilizada en etiqueta, placeholder y texto ingresado.
 *                          Por defecto toma el valor del [LocalAppTypographyCore].
 */
@Composable
fun TextInputAJ(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit = {},
    readOnly: Boolean = false,
    enableClickable: Boolean = false,
    onClick: () -> Unit = {},
    label: String? = null,
    placeholder: String? = null,
    enabled: Boolean = true,
    inputType: InputType = InputType.TEXT,
    leadingIcon: Any? = null,
    trailingIcon: Any? = null,
    colors: TextInputAJColors = TextInputAJColors(),
    isTypePassword: Boolean = false,
    typography: AppTypography = LocalAppTypographyCore.current
) {
    val passwordVisible = remember { mutableStateOf(!isTypePassword) }

    Column(modifier = modifier) {
        label?.let {
            Text(
                text = it,
                style = typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Box {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = value,
                onValueChange = { raw ->
                    if(inputType.validateRaw(raw.text) || raw.text.isEmpty()) onValueChange(raw)
                },
                textStyle = typography.bodyLarge,
                visualTransformation = if (passwordVisible.value) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                placeholder = if (placeholder != null) {
                    {
                        Text(
                            text = placeholder,
                            color = colors.placeholderColor,
                            style = typography.titleSmall
                        )
                    }
                } else null,
                leadingIcon = if (leadingIcon != null) {
                    {
                        if(leadingIcon is DrawableResource){
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(resource = leadingIcon),
                                contentDescription = null,
                                tint = colors.leadingIconColor
                            )
                        } else if(leadingIcon is ImageVector) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = leadingIcon,
                                contentDescription = null,
                                tint = colors.leadingIconColor
                            )
                        }
                    }
                } else null,
                trailingIcon = isTypePassword.isPasswordCase(colors, trailingIcon, passwordVisible),
                enabled = enabled,
                readOnly = readOnly,
                keyboardOptions = KeyboardOptions(keyboardType = inputType.toKeyboardType()),
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colors.focusedContainerColor,
                    unfocusedContainerColor = colors.unfocusedContainerColor,
                    focusedPlaceholderColor = colors.focusedIndicatorColor,
                    focusedIndicatorColor = colors.focusedIndicatorColor
                )
            )

            if (enableClickable) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = onClick
                        )
                )
            }
        }
    }
}

private fun Boolean.isPasswordCase(
    colors: TextInputAJColors,
    icon: Any?,
    passwordVisible: MutableState<Boolean>
): @Composable (() -> Unit)? {
    if(!this){
        if(icon == null) return null

        return {
            if(icon is DrawableResource){
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(resource = icon),
                    contentDescription = null,
                    tint = colors.trailingIconColor
                )
            } else if(icon is ImageVector){
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = icon,
                    contentDescription = null,
                    tint = colors.trailingIconColor
                )
            }
        }
    }
    return {
        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable(
                    enabled = true,
                    onClick = {
                        passwordVisible.value = !passwordVisible.value
                    }
                ),
            painter = painterResource(resource = Res.drawable.visibility_lock_icon.takeIf{ passwordVisible.value } ?: Res.drawable.visibility_off_icon),
            contentDescription = null,
            tint = colors.trailingIconColor
        )
    }

}

/**
 * Define el tipo de entrada permitido en el campo de texto.
 *
 * @property TEXT          Acepta cualquier carácter: letras, números y símbolos.
 * @property TEXT_SPECIAL  Solo letras (incluyendo tildes y ñ) y espacios.
 * @property DOCUMENT      Solo texto y dígitos numéricos (0-9).
 * @property NUMBER        Solo dígitos numéricos (0-9).
 * @property EMAIL         Solo caracteres válidos para direcciones de correo electrónico.
 * @property DECIMAL       Solo dígitos numéricos (0-9) y un punto decimal.
 * @property YEAR          Solo dígitos numéricos (0-9) para el año y como máximo 4 caracteres.
 */
enum class InputType {
    EMAIL,
    TEXT,
    TEXT_SPECIAL,
    DOCUMENT,
    NUMBER,
    DECIMAL,
    YEAR
}

/** Retorna el [KeyboardType] correspondiente al [InputType]. */
private fun InputType.toKeyboardType(): KeyboardType = when (this) {
    InputType.EMAIL         -> KeyboardType.Email
    InputType.TEXT         -> KeyboardType.Text
    InputType.TEXT_SPECIAL -> KeyboardType.Text
    InputType.NUMBER       -> KeyboardType.Number
    InputType.DECIMAL       -> KeyboardType.Decimal
    InputType.DOCUMENT -> KeyboardType.Text
    InputType.YEAR         -> KeyboardType.Number
}

/** Filtra el texto ingresado según el [InputType]. */
private fun InputType.validateRaw(input: String): Boolean = when (this) {
    InputType.TEXT         -> true
    InputType.TEXT_SPECIAL -> input.all { it.isLetter() || it == ' ' }
    InputType.DOCUMENT -> input.all { it.isLetter() || it.isDigit() }
    InputType.NUMBER       -> input.all { it.isDigit() }
    InputType.EMAIL -> true
    InputType.DECIMAL -> input.matches(Regex("^\\d+\\.?\\d{0,2}$"))
    InputType.YEAR -> input.all { it.isDigit() } && input.length < 5
}