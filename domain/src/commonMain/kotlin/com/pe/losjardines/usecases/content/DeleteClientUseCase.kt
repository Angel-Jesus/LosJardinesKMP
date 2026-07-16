package com.pe.losjardines.usecases.content

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.repository.DatabaseRepository
import com.pe.losjardines.repository.FirestoreRepository
import com.pe.losjardines.usecases.model.RegistrationDto
import com.pe.losjardines.utils.StateProcess
import com.pe.losjardines.utils.getCurrentMillis
import kotlin.coroutines.cancellation.CancellationException

class DeleteClientUseCase(
    private val databaseRepository: DatabaseRepository,
    private val firestoreRepository: FirestoreRepository
) {
    @Throws(Exception::class, CancellationException::class, Failure::class)
    suspend fun run(id: Long, idFirebase: String) {
        val dateDeleted = getCurrentMillis()

        val registration = when (val result = databaseRepository.getRegistrationById(id)) {
            is Either.Success -> result.data.copy(id = id, idFirebase = idFirebase)
            is Either.Error -> throw result.error
        }

        when (val sendResult = firestoreRepository.sendToTrash(registration, dateDeleted)) {
            is Either.Success -> deleteFromFirebase(registration, dateDeleted)
            is Either.Error -> when (sendResult.error) {
                is Failure.InternetConnection -> moveToTrashLocally(registration, dateDeleted, StateProcess.PENDING_DELETE)
                else -> throw sendResult.error
            }
        }
    }

    private suspend fun deleteFromFirebase(registration: RegistrationDto, dateDeleted: Long) {
        when (val deleteResult = firestoreRepository.deleteClient(registration)) {
            is Either.Success -> moveToTrashLocally(registration, dateDeleted, StateProcess.SYNC)
            is Either.Error -> when (deleteResult.error) {
                is Failure.InternetConnection -> moveToTrashLocally(registration, dateDeleted, StateProcess.PENDING_DELETE)
                else -> throw deleteResult.error
            }
        }
    }

    private suspend fun moveToTrashLocally(
        registration: RegistrationDto,
        dateDeleted: Long,
        state: StateProcess
    ) {
        databaseRepository.moveToTrash(registration, dateDeleted, state.description)
    }
}
