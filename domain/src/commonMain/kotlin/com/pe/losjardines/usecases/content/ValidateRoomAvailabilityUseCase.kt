package com.pe.losjardines.usecases.content

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.toException
import com.pe.losjardines.repository.DatabaseRepository
import com.pe.losjardines.usecases.model.ReservationDto
import com.pe.losjardines.usecases.model.ReservationStatus
import com.pe.losjardines.usecases.model.RoomAvailability
import com.pe.losjardines.utils.dateToEpochMillis
import kotlin.coroutines.cancellation.CancellationException

/**
 * Valida, al registrar a un cliente, que la habitación solicitada no tenga ya una reserva activa
 * que choque con el rango de fechas indicado. Sirve para evitar la doble ocupación de una misma
 * habitación por dos clientes.
 *
 * Reglas de choque:
 *  - Solo se consideran reservas **activas** ([ReservationStatus.CHECK_IN] y
 *    [ReservationStatus.OCCUPIED]); las canceladas no bloquean.
 *  - Se compara la misma habitación (sin distinguir mayúsculas/espacios).
 *  - Dos rangos chocan si se solapan tratando el intervalo como semiabierto `[entrada, salida)`:
 *    el día de salida (checkout) libera la habitación, así que una entrada ese mismo día NO choca.
 *  - La reserva en curso (la que se está atendiendo en un check-in) puede excluirse por
 *    [Params.excludeReservationId] o [Params.excludeIdFirebase] para no auto-bloquearse.
 */
class ValidateRoomAvailabilityUseCase(
    private val databaseRepository: DatabaseRepository
) {
    data class Params(
        val room: String,
        val dateEnter: String,
        val dateExit: String,
        val excludeReservationId: Long? = null,
        val excludeIdFirebase: String? = null
    )

    @Throws(Exception::class, CancellationException::class)
    suspend fun run(params: Params): RoomAvailability {
        val room = params.room.trim()
        val newEnter = params.dateEnter.dateToEpochMillis()
        val newExit = params.dateExit.dateToEpochMillis()

        // Sin habitación o con fechas inválidas no es posible validar: no bloqueamos.
        if (room.isEmpty() || newEnter == null || newExit == null) return RoomAvailability.Available

        val conflict = loadActiveReservations().firstOrNull { reservation ->
            !reservation.isExcludedBy(params) &&
                reservation.room.trim().equals(room, ignoreCase = true) &&
                reservation.overlaps(newEnter, newExit)
        }

        return conflict?.let { RoomAvailability.Conflict(it) } ?: RoomAvailability.Available
    }

    private suspend fun loadActiveReservations(): List<ReservationDto> =
        BLOCKING_STATUSES.flatMap { status -> loadAllByStatus(status.value) }

    private suspend fun loadAllByStatus(state: String): List<ReservationDto> {
        val all = mutableListOf<ReservationDto>()
        var offset = 0L
        while (true) {
            val page = when (val result = databaseRepository.getReservations(state, PAGE_SIZE, offset)) {
                is Either.Success -> result.data
                is Either.Error -> throw result.error.toException()
            }
            all += page
            if (page.size < PAGE_SIZE) break
            offset += PAGE_SIZE
        }
        return all
    }

    private fun ReservationDto.isExcludedBy(params: Params): Boolean {
        val byId = params.excludeReservationId != null && id == params.excludeReservationId
        val byFirebase = !params.excludeIdFirebase.isNullOrBlank() && idFirebase == params.excludeIdFirebase
        return byId || byFirebase
    }

    /** Solapamiento de intervalos semiabiertos `[entrada, salida)`. */
    private fun ReservationDto.overlaps(newEnter: Long, newExit: Long): Boolean {
        val existingEnter = dateEnter.dateToEpochMillis() ?: return false
        val existingExit = dateExit.dateToEpochMillis() ?: return false
        return newEnter < existingExit && existingEnter < newExit
    }

    companion object {
        private const val PAGE_SIZE = 50L
        private val BLOCKING_STATUSES = listOf(ReservationStatus.CHECK_IN, ReservationStatus.OCCUPIED)
    }
}
