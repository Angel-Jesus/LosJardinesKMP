package com.pe.losjardines.presentation.content.home.contract

import com.pe.losjardines.base.ui.BaseUiState
import com.pe.losjardines.usecases.model.ReservationDto
import com.pe.losjardines.utils.companions.EMPTY

data class HomeUiState(
    val isLoading: Boolean = false,
    val reservation: String = String.EMPTY,
    val available: String = String.EMPTY,
    val reservationNow: String = String.EMPTY,
    val reservations: List<ReservationDto> = emptyList()
): BaseUiState
