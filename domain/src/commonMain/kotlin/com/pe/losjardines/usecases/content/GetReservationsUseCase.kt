package com.pe.losjardines.usecases.content

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.repository.DatabaseRepository
import com.pe.losjardines.usecases.model.ReservationDto
import com.pe.losjardines.usecases.model.ReservationStatus
import kotlin.coroutines.cancellation.CancellationException

class GetReservationsUseCase(
    private val databaseRepository: DatabaseRepository
) {
    @Throws(Exception::class, CancellationException::class, Failure::class)
    suspend fun run(state: ReservationStatus, page: Int): List<ReservationDto> {
        val limit = PAGE_SIZE.toLong()
        val offset = ((page - 1).coerceAtLeast(0) * PAGE_SIZE).toLong()

        return when (val result = databaseRepository.getReservations(state.value, limit, offset)) {
            is Either.Success -> result.data
            is Either.Error -> throw result.error
        }
    }

    companion object {
        const val PAGE_SIZE = 50
    }
}
