package com.pe.losjardines.presentation.content.reservation.contract

import com.pe.losjardines.base.ui.BaseUiState
import com.pe.losjardines.usecases.model.ReservationDto
import com.pe.losjardines.usecases.model.ReservationStatus

data class ReservationState(
    val loading: Boolean = false,
    val showClearFilter: Boolean = false,
    val reservationStatus: ReservationStatus = ReservationStatus.CHECK_IN,
    val reservations: List<ReservationDto> = emptyList(),
    val errorMessage: String? = null
): BaseUiState
