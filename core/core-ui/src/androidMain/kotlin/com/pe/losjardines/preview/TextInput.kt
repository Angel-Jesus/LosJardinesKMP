package com.pe.losjardines.preview

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.pe.losjardines.components.textInput.TextInputAJ
import com.pe.losjardines.values.AppTheme

@Preview(showBackground = true)
@Composable
fun TextInputAJPreview(){
    AppTheme {
        TextInputAJ(
            value = "",
            onValueChange = {},
            label = "Apellidos y Nombres",
            placeholder = "Nombre y Apellido"
        )
    }
}