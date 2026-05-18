package com.pe.losjardines

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.pe.losjardines.presentation.content.registration.screen.RegistrationMobileScreen
import com.pe.losjardines.values.AppTheme

@Preview(showBackground = true)
@Composable
fun RegistrationMobileScreenPreview(){
    AppTheme {
        RegistrationMobileScreen(title = "Registro")
    }
}