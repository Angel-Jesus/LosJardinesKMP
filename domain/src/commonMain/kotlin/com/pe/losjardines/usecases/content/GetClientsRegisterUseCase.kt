package com.pe.losjardines.usecases.content

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.base.usecase.BaseSafeUseCase
import com.pe.losjardines.repository.DatabaseRepository
import com.pe.losjardines.repository.FirestoreRepository
import com.pe.losjardines.usecases.model.FilterValues
import com.pe.losjardines.usecases.model.RegistrationDto
import com.pe.losjardines.utils.constance.MonthFilter
import com.pe.losjardines.utils.getDateInitToEpochMillis
import com.pe.losjardines.utils.getDateLastToEpochMillis
import com.pe.losjardines.utils.getDateToEpochMillis
import com.pe.losjardines.utils.getLastDayOfMonth

class GetClientsRegisterUseCase(
    private val firestoreRepository: FirestoreRepository,
    private val databaseRepository: DatabaseRepository
): BaseSafeUseCase<GetClientsRegisterUseCase.Params, List<RegistrationDto>>()  {
    data class Params(val filter: FilterValues? = null)

    override suspend fun run(params: Params): Either<Failure, List<RegistrationDto>> {
        if(params.filter == null){
            val dateInit = getDateInitToEpochMillis()
            val dateLast = getDateLastToEpochMillis()
            return databaseRepository.getClientsRegister(dateInit, dateLast, null)
        }

        val month = MonthFilter.fromDisplayName(params.filter.month)
        val year = params.filter.year.toIntOrNull() ?: 0
        val dateInit = getDateToEpochMillis(1, month?.number ?: 0, year)
        val dateLast = getDateToEpochMillis(getLastDayOfMonth(year, month?.number ?: 0), month?.number ?: 0, year)

        if(dateInit == null || dateLast == null) return Either.Error(Failure.UnknownFailure("Error getting month or year"))

        return databaseRepository.getClientsRegister(dateInit, dateLast, params.filter.searchDni.takeIf { it?.isNotEmpty() == true })
    }
}