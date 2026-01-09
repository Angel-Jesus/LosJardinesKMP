package com.pe.losjardines.usecases.content

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.base.usecase.BaseSafeUseCase
import com.pe.losjardines.repository.DatabaseRepository
import com.pe.losjardines.usecases.model.CountryDto

class GetCountriesUseCase(
    private val databaseRepository: DatabaseRepository
): BaseSafeUseCase<Unit, List<CountryDto>>() {
    override suspend fun run(params: Unit): Either<Failure, List<CountryDto>> {
        return databaseRepository.getCountries()
    }
}