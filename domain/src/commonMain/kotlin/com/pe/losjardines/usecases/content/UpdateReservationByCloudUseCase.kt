package com.pe.losjardines.usecases.content

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.toException
import com.pe.losjardines.repository.DatabaseRepository
import com.pe.losjardines.repository.FirestoreRepository
import com.pe.losjardines.utils.StateProcess
import com.pe.losjardines.utils.getCurrentYear
import kotlin.coroutines.cancellation.CancellationException

class UpdateReservationByCloudUseCase(
    private val databaseRepository: DatabaseRepository,
    private val firestoreRepository: FirestoreRepository
) {
    @Throws(Exception::class, CancellationException::class)
    suspend fun run() {
        val reservations = when (val result = firestoreRepository.getReservationsByCollection()) {
            is Either.Success -> result.data
            is Either.Error -> throw result.error.toException()
        }

        when (val deleteResult = databaseRepository.deleteAllReservations()) {
            is Either.Success -> Unit
            is Either.Error -> throw deleteResult.error.toException()
        }

        when (val insertResult = databaseRepository.insertReservations(reservations, StateProcess.SYNC.description)) {
            is Either.Success -> Unit
            is Either.Error -> throw insertResult.error.toException()
        }
    }
}
