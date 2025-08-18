package com.pe.losjardines.presentation.contract.registration.model

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import com.pe.losjardines.utils.companions.EMPTY

data class RegisterModel(
    val title: String,
    val value: String = String.EMPTY,
    val type: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
)