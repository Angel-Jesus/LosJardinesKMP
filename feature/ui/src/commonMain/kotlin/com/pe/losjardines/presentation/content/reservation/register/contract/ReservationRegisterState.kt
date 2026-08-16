package com.pe.losjardines.presentation.content.reservation.register.contract

import androidx.compose.ui.text.input.TextFieldValue
import com.pe.losjardines.base.ui.BaseUiState
import com.pe.losjardines.utils.companions.EMPTY

data class ReservationRegisterState(
    val fullName: TextFieldValue = TextFieldValue(),
    val sex: String = String.EMPTY,
    val country: String = String.EMPTY,
    val region: String = String.EMPTY,
    val documentType: String = String.EMPTY,
    val documentNumber: TextFieldValue = TextFieldValue(),
    val checkInDate: String = String.EMPTY,
    val checkOutDate: String = String.EMPTY,
    val typeRoom: String = String.EMPTY,
    val room: TextFieldValue = TextFieldValue(),
    val rate: TextFieldValue = TextFieldValue(),
    val observation: TextFieldValue = TextFieldValue()
): BaseUiState
