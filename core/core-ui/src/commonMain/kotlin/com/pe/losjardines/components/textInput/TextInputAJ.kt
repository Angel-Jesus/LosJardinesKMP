package com.pe.losjardines.components.textInput

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
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

@Composable
fun TextInputAJ(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String? = null,
    placeholder: String? = null,
    enabled: Boolean = true,
    leadingIcon: DrawableResource? = null,
    trailingIcon: DrawableResource? = null,
    colors: TextInputAJColors = TextInputAJColors(),
    isTypePassword: Boolean = false,
    typography: AppTypography = LocalAppTypographyCore.current
){
    val passwordVisible = remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        label?.let {
            Text(
                text = label,
                style = typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        OutlinedTextField(
            modifier = modifier,
            value = value,
            onValueChange = onValueChange,
            visualTransformation = if(passwordVisible.value){
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            placeholder = if(placeholder != null) {
                @Composable {
                    Text(
                        text = placeholder,
                        color = colors.placeholderColor,
                        style = typography.bodyLarge
                    )
                }
            } else { null },
            leadingIcon = if(leadingIcon != null) {
                @Composable {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(resource = leadingIcon),
                        contentDescription = null,
                        tint = colors.leadingIconColor
                    )
                }
            } else { null },
            trailingIcon = isTypePassword.isPasswordCase(colors, trailingIcon, passwordVisible),
            enabled = enabled,
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                focusedPlaceholderColor = colors.focusedIndicatorColor,
                focusedIndicatorColor = colors.focusedIndicatorColor
            )
        )
    }
}

private fun Boolean.isPasswordCase(
    colors: TextInputAJColors,
    icon: DrawableResource?,
    passwordVisible: MutableState<Boolean>
): @Composable (() -> Unit)? {
    if(!this){
        if(icon == null) return null

        return {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(resource = icon),
                contentDescription = null,
                tint = colors.trailingIconColor
            )
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