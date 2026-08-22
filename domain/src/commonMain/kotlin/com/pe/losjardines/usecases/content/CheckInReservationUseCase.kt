package com.pe.losjardines.usecases.content

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.base.error.toException
import com.pe.losjardines.repository.DatabaseRepository
import com.pe.losjardines.repository.FirestoreRepository
import com.pe.losjardines.usecases.model.ReservationDto
import com.pe.losjardines.usecases.model.ReservationStatus
import com.pe.losjardines.utils.StateProcess
import kotlin.coroutines.cancellation.CancellationException

/**
 * Realiza el check-in de una reserva: actualiza su estado de atención a [ReservationStatus.OCCUPIED].
 *
 * Primero intenta reflejar el cambio en Firebase. Si es satisfactorio la fila local se marca como
 * [StateProcess.SYNC]; si falla por conexión se marca como [StateProcess.PENDING_UPDATE] para
 * reintentar la sincronización más adelante. Cualquier otro error se propaga.
 */
class CheckInReservationUseCase(
    private val databaseRepository: DatabaseRepository,
    private val firestoreRepository: FirestoreRepository
) {
    @Throws(Exception::class, CancellationException::class)
    suspend fun run(reservation: ReservationDto) {
        val id = reservation.id ?: return
        val attentionState = ReservationStatus.OCCUPIED.value

        val syncState = when (val result = firestoreRepository.updateReservationAttentionState(reservation, attentionState)) {
            is Either.Success -> StateProcess.SYNC.description
            is Either.Error -> when (result.error) {
                is Failure.InternetConnection -> StateProcess.PENDING_UPDATE.description
                else -> throw result.error.toException()
            }
        }

        when (val result = databaseRepository.updateReservationAttentionState(id, attentionState, syncState)) {
            is Either.Success -> Unit
            is Either.Error -> throw result.error.toException()
        }
    }
}
