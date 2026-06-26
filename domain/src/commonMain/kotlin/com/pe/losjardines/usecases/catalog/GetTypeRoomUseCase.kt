package com.pe.losjardines.usecases.catalog

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.repository.DatabaseRepository
import com.pe.losjardines.usecases.model.TypeRoomDto
import kotlin.coroutines.cancellation.CancellationException

class GetTypeRoomUseCase(
    private val databaseRepository: DatabaseRepository
) {
    @Throws(Exception::class, CancellationException::class, Failure::class)
    suspend fun run(): List<TypeRoomDto> {
        return when (val result = databaseRepository.getTypeRoom()) {
            is Either.Success -> result.data
            is Either.Error -> throw result.error
        }
    }
}
