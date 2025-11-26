package com.pe.losjardines.preview

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.pe.losjardines.components.textInput.TextInputAJ

@Preview(showBackground = true)
@Composable
fun TextInputAJPreview(){
    MaterialTheme {
        TextInputAJ(
            value = "",
            onValueChange = {},
            label = "Apellidos y Nombres",
            placeholder = "Nombre y Apellido"
        )
    }
}