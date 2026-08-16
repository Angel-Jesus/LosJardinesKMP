package com.pe.losjardines.presentation.content.reservation.register.contract

import com.pe.losjardines.base.ui.BaseEffect

sealed interface ReservationRegisterEffect: BaseEffect {
    data class SuccessSave(val message: String? = null): ReservationRegisterEffect
    data class ErrorSave(val message: String): ReservationRegisterEffect
}
