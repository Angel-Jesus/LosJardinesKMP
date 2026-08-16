package com.pe.losjardines.presentation.content.reservation.contract

import com.pe.losjardines.base.ui.BaseEvent
import com.pe.losjardines.presentation.components.table.UpdateFieldParams
import com.pe.losjardines.presentation.content.utils.FieldFilter
import com.pe.losjardines.usecases.model.FilterValues
import com.pe.losjardines.usecases.model.ReservationStatus

sealed interface ReservationEvent: BaseEvent {
    data class GetReservations(val filter: FilterValues? = null): ReservationEvent
    data class UpdateReservationInformation(val newValue: String, val fieldParams: UpdateFieldParams?): ReservationEvent
    data class DeleteReservationInformation(val id: Long, val idFirebase: String): ReservationEvent
    data class FilterReservations(val reservationStatus: ReservationStatus?): ReservationEvent
}
