package com.pe.losjardines.usecases.content

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.repository.DatabaseRepository
import com.pe.losjardines.repository.FirestoreRepository
import com.pe.losjardines.usecases.model.RegistrationDto
import com.pe.losjardines.utils.StateProcess
import com.pe.losjardines.utils.generateFirebaseDocumentId
import kotlin.coroutines.cancellation.CancellationException

class SaveCustomerRegistrationUseCase(
    private val databaseRepository: DatabaseRepository,
    private val firestoreRepository: FirestoreRepository
) {
    @Throws(Exception::class, CancellationException::class, Failure::class)
    suspend fun run(registrationDto: RegistrationDto) {
        val dto = registrationDto.copy(idFirebase = generateFirebaseDocumentId())
        val sendResult = firestoreRepository.sendClient(dto)

        val saveResult = if (sendResult is Either.Success) {
            databaseRepository.saveCustomerInformation(dto, StateProcess.SYNC.description)
        } else {
            databaseRepository.saveCustomerInformation(dto, StateProcess.PENDING_INSERT.description)
        }

        if (saveResult is Either.Error) throw saveResult.error
    }
}
