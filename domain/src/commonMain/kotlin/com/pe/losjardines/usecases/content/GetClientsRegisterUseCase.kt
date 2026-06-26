package com.pe.losjardines.usecases.content

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.repository.DatabaseRepository
import com.pe.losjardines.repository.FirestoreRepository
import com.pe.losjardines.usecases.model.FilterValues
import com.pe.losjardines.usecases.model.RegistrationDto
import com.pe.losjardines.utils.constance.MonthFilter
import com.pe.losjardines.utils.getDateInitToEpochMillis
import com.pe.losjardines.utils.getDateLastToEpochMillis
import com.pe.losjardines.utils.getDateToEpochMillis
import com.pe.losjardines.utils.getLastDayOfMonth
import kotlin.coroutines.cancellation.CancellationException

class GetClientsRegisterUseCase(
    private val firestoreRepository: FirestoreRepository,
    private val databaseRepository: DatabaseRepository
) {
    @Throws(Exception::class, CancellationException::class, Failure::class)
    suspend fun run(filter: FilterValues? = null): List<RegistrationDto> {
        val result = if (filter == null) {
            databaseRepository.getClientsRegister(getDateInitToEpochMillis(), getDateLastToEpochMillis(), null)
        } else {
            val month = MonthFilter.fromDisplayName(filter.month)
            val year = filter.year.toIntOrNull() ?: 0
            val dateInit = getDateToEpochMillis(1, month?.number ?: 0, year)
                ?: throw Failure.UnknownFailure("Error getting month or year")
            val dateLast = getDateToEpochMillis(getLastDayOfMonth(year, month?.number ?: 0), month?.number ?: 0, year)
                ?: throw Failure.UnknownFailure("Error getting month or year")
            databaseRepository.getClientsRegister(dateInit, dateLast, filter.searchDni?.takeIf { it.isNotEmpty() })
        }

        return when (result) {
            is Either.Success -> result.data
            is Either.Error -> throw result.error
        }
    }
}
