package com.pe.losjardines.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.pe.losjardines.components.dropdown.DropDownAJ
import com.pe.losjardines.values.AppTheme

@Preview(showBackground = true)
@Composable
fun DropDownAJPreview(){
    AppTheme {
        DropDownAJ(
            label = "Sexo",
            options = listOf("Masculino", "Femenino"),
            selectedOption = "Masculino",
            onOptionSelected = {}
        )
    }
}