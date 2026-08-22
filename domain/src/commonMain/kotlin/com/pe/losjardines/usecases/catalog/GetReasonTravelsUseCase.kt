package com.pe.losjardines.usecases.catalog

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.toException
import com.pe.losjardines.repository.DatabaseRepository
import com.pe.losjardines.usecases.model.TravelReasonDto
import kotlin.coroutines.cancellation.CancellationException

class GetReasonTravelsUseCase(
    private val databaseRepository: DatabaseRepository
){
    @Throws(Exception::class, CancellationException::class)
    suspend fun run(): List<TravelReasonDto> {
        return when(val result = databaseRepository.getReasonTravels()){
            is Either.Success -> result.data
            is Either.Error -> throw result.error.toException()
        }
    }
}