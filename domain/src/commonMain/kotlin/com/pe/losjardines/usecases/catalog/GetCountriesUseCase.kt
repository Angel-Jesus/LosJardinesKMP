package com.pe.losjardines.usecases.catalog

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.toException
import com.pe.losjardines.repository.DatabaseRepository
import com.pe.losjardines.usecases.model.CountryDto
import kotlin.coroutines.cancellation.CancellationException

class GetCountriesUseCase(
    private val databaseRepository: DatabaseRepository
){
    @Throws(Exception::class, CancellationException::class)
    suspend fun run(): List<CountryDto> {
        return when(val result = databaseRepository.getCountries()){
            is Either.Success -> result.data
            is Either.Error -> throw result.error.toException()
        }
    }
}