package com.pe.losjardines.usecases.content

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.base.error.toException
import com.pe.losjardines.repository.DatabaseRepository
import com.pe.losjardines.repository.FirestoreRepository
import com.pe.losjardines.usecases.model.ReservationDto
import com.pe.losjardines.utils.StateProcess
import com.pe.losjardines.utils.getCurrentMillis
import kotlin.coroutines.cancellation.CancellationException

class DeleteReservationUseCase(
    private val databaseRepository: DatabaseRepository,
    private val firestoreRepository: FirestoreRepository
) {
    @Throws(Exception::class, CancellationException::class)
    suspend fun run(id: Long, idFirebase: String) {
        val dateDeleted = getCurrentMillis()

        val reservation = when (val result = databaseRepository.getReservationById(id)) {
            is Either.Success -> result.data.copy(id = id, idFirebase = idFirebase)
            is Either.Error -> throw result.error.toException()
        }

        when (val sendResult = firestoreRepository.sendReservationToTrash(reservation, dateDeleted)) {
            is Either.Success -> deleteFromFirebase(reservation, dateDeleted)
            is Either.Error -> when (sendResult.error) {
                is Failure.InternetConnection -> moveToTrashLocally(reservation, dateDeleted, StateProcess.PENDING_DELETE)
                else -> throw sendResult.error.toException()
            }
        }
    }

    private suspend fun deleteFromFirebase(reservation: ReservationDto, dateDeleted: Long) {
        when (val deleteResult = firestoreRepository.deleteReservation(reservation)) {
            is Either.Success -> moveToTrashLocally(reservation, dateDeleted, StateProcess.SYNC)
            is Either.Error -> when (deleteResult.error) {
                is Failure.InternetConnection -> moveToTrashLocally(reservation, dateDeleted, StateProcess.PENDING_DELETE)
                else -> throw deleteResult.error.toException()
            }
        }
    }

    private suspend fun moveToTrashLocally(
        reservation: ReservationDto,
        dateDeleted: Long,
        state: StateProcess
    ) {
        databaseRepository.moveReservationToTrash(reservation, dateDeleted, state.description)
    }
}
