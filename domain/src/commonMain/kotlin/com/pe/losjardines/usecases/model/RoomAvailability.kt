package com.pe.losjardines.usecases.model

/**
 * Resultado de validar si una habitación está disponible para un rango de fechas al registrar
 * a un cliente. Ver [com.pe.losjardines.usecases.content.ValidateRoomAvailabilityUseCase].
 */
sealed interface RoomAvailability {
    /** La habitación está libre en el rango solicitado. */
    data object Available : RoomAvailability

    /**
     * Existe una reserva activa que choca con el rango solicitado en la misma habitación.
     * [reservation] es la reserva en conflicto detectada.
     */
    data class Conflict(val reservation: ReservationDto) : RoomAvailability
}
