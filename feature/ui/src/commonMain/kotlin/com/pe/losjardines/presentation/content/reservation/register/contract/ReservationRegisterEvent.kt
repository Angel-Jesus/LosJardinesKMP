package com.pe.losjardines.presentation.content.reservation.register.contract

import com.pe.losjardines.base.ui.BaseEvent
import com.pe.losjardines.presentation.content.utils.FieldRegistration

sealed interface ReservationRegisterEvent: BaseEvent {
    data object GetCatalogInformation: ReservationRegisterEvent
    data class ValueChanged(val value: Any, val field: FieldRegistration): ReservationRegisterEvent
    data object SaveData: ReservationRegisterEvent
}
