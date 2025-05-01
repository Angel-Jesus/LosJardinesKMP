package com.pe.losjardines.presentation.contract.registration.model

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType

data class RegisterModel(
    val title: String,
    val value: String = "",
    val type: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
)