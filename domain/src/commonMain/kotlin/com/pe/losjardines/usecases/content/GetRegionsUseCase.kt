package com.pe.losjardines.usecases.content

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.base.usecase.BaseSafeUseCase
import com.pe.losjardines.repository.DatabaseRepository
import com.pe.losjardines.usecases.model.RegionDto

class GetRegionsUseCase(
    private val databaseRepository: DatabaseRepository
): BaseSafeUseCase<GetRegionsUseCase.Params, List<RegionDto>>() {
    data class Params(
        val countryId: String
    )

    override suspend fun run(params: Params): Either<Failure, List<RegionDto>> {
        return databaseRepository.getRegionsByCountry(params.countryId)
    }
}