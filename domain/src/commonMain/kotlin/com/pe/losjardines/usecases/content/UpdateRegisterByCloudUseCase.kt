package com.pe.losjardines.usecases.content

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.repository.DatabaseRepository
import com.pe.losjardines.repository.FirestoreRepository
import com.pe.losjardines.utils.StateProcess
import com.pe.losjardines.utils.getCurrentYear
import kotlin.coroutines.cancellation.CancellationException

class UpdateRegisterByCloudUseCase(
    private val databaseRepository: DatabaseRepository,
    private val firestoreRepository: FirestoreRepository
) {
    @Throws(Exception::class, CancellationException::class, Failure::class)
    suspend fun run() {
        val collection = getCurrentYear()

        val registrations = when (val result = firestoreRepository.getRegistrationsByCollection(collection)) {
            is Either.Success -> result.data
            is Either.Error -> throw result.error
        }

        when (val deleteResult = databaseRepository.deleteAllRegisters()) {
            is Either.Success -> Unit
            is Either.Error -> throw deleteResult.error
        }

        when (val insertResult = databaseRepository.insertRegistrations(registrations, StateProcess.SYNC.description)) {
            is Either.Success -> Unit
            is Either.Error -> throw insertResult.error
        }
    }
}
