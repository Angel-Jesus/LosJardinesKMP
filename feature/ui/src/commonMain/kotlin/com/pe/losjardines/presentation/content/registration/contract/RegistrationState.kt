package com.pe.losjardines.presentation.content.registration.contract

import com.pe.losjardines.base.ui.BaseUiState
import com.pe.losjardines.utils.companions.EMPTY

data class RegistrationState(
    val fullName: String = String.EMPTY,
    val sex: String = String.EMPTY,
    val country: String = String.EMPTY,
    val region: String = String.EMPTY,
    val travelReason: String = String.EMPTY,
    val documentType: String = String.EMPTY,
    val documentNumber: String = String.EMPTY,
    val checkInDate: String = String.EMPTY,
    val checkOutDate: String = String.EMPTY,
    val room: String = String.EMPTY,
    val rate: String = String.EMPTY,
    val observation: String = String.EMPTY
): BaseUiState
