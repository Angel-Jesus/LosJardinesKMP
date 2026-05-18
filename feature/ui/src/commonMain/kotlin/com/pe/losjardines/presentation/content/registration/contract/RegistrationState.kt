package com.pe.losjardines.presentation.content.registration.contract

import androidx.compose.ui.text.input.TextFieldValue
import com.pe.losjardines.base.ui.BaseUiState
import com.pe.losjardines.utils.companions.EMPTY

data class RegistrationState(
    val fullName: TextFieldValue = TextFieldValue(),
    val sex: String = String.EMPTY,
    val country: String = String.EMPTY,
    val region: String = String.EMPTY,
    val travelReason: String = String.EMPTY,
    val documentType: String = String.EMPTY,
    val documentNumber: TextFieldValue = TextFieldValue(),
    val checkInDate: String = String.EMPTY,
    val checkOutDate: String = String.EMPTY,
    val room: TextFieldValue = TextFieldValue(),
    val rate: TextFieldValue = TextFieldValue(),
    val observation: TextFieldValue = TextFieldValue()
): BaseUiState
