package com.pe.losjardines.usecases.catalog

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.toException
import com.pe.losjardines.repository.DatabaseRepository
import com.pe.losjardines.usecases.model.RegionDto
import kotlin.coroutines.cancellation.CancellationException

class GetRegionsUseCase(
    private val databaseRepository: DatabaseRepository
) {
    @Throws(Exception::class, CancellationException::class)
    suspend fun run(countryId: String): List<RegionDto> {
        return when (val result = databaseRepository.getRegionsByCountry(countryId)) {
            is Either.Success -> result.data
            is Either.Error -> throw result.error.toException()
        }
    }
}
