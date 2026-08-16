package com.pe.losjardines.usecases.content

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.repository.DatabaseRepository
import com.pe.losjardines.usecases.model.ReservationDto
import kotlin.coroutines.cancellation.CancellationException

class GetReservationByIdUseCase(
    private val databaseRepository: DatabaseRepository
) {
    @Throws(Exception::class, CancellationException::class, Failure::class)
    suspend fun run(id: Long): ReservationDto {
        return when (val result = databaseRepository.getReservationById(id)) {
            is Either.Success -> result.data
            is Either.Error -> throw result.error
        }
    }
}
