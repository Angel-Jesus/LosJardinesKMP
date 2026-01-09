package com.pe.losjardines.usecases.content

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.base.usecase.BaseSafeUseCase
import com.pe.losjardines.repository.DatabaseRepository
import com.pe.losjardines.usecases.model.TravelReasonDto

class GetReasonTravelsUseCase(
    private val databaseRepository: DatabaseRepository
): BaseSafeUseCase<Unit, List<TravelReasonDto>>() {
    override suspend fun run(params: Unit): Either<Failure, List<TravelReasonDto>> {
        return databaseRepository.getReasonTravels()
    }
}