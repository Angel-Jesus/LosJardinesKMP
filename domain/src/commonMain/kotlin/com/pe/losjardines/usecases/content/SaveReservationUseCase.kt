package com.pe.losjardines.usecases.content

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.base.error.toException
import com.pe.losjardines.repository.DatabaseRepository
import com.pe.losjardines.repository.FirestoreRepository
import com.pe.losjardines.usecases.model.ReservationDto
import com.pe.losjardines.utils.StateProcess
import com.pe.losjardines.utils.generateFirebaseDocumentId
import kotlin.coroutines.cancellation.CancellationException

class SaveReservationUseCase(
    private val databaseRepository: DatabaseRepository,
    private val firestoreRepository: FirestoreRepository
) {
    @Throws(Exception::class, CancellationException::class)
    suspend fun run(reservationDto: ReservationDto) {
        val dto = reservationDto.copy(idFirebase = generateFirebaseDocumentId())

        when(val sendResult = firestoreRepository.sendReservation(dto)){
            is Either.Success -> databaseRepository.saveReservation(dto, StateProcess.SYNC.description)
            is Either.Error-> when(sendResult.error){
                is Failure.InternetConnection -> databaseRepository.saveReservation(dto, StateProcess.PENDING_INSERT.description)
                else -> throw sendResult.error.toException()
            }
        }
    }
}
