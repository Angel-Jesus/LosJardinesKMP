package com.pe.losjardines.usecases.content

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.base.usecase.BaseSafeUseCase
import com.pe.losjardines.repository.DatabaseRepository
import com.pe.losjardines.repository.FirestoreRepository
import com.pe.losjardines.usecases.model.RegistrationDto
import com.pe.losjardines.utils.StateProcess
import com.pe.losjardines.utils.generateFirebaseDocumentId
import com.pe.losjardines.utils.getDateNow

class SaveCustomerRegistrationUseCase(
    private val databaseRepository: DatabaseRepository,
    private val firestoreRepository: FirestoreRepository
): BaseSafeUseCase<SaveCustomerRegistrationUseCase.Params, Unit>() {
    data class Params(val registrationDto: RegistrationDto)

    override suspend fun run(params: Params): Either<Failure, Unit> {
        val registrationDto = params.registrationDto.copy(idFirebase = generateFirebaseDocumentId())
        val sendCustomerInformation = firestoreRepository.sendClient(registrationDto)

        return if(sendCustomerInformation is Either.Success){
            databaseRepository.saveCustomerInformation(registrationDto, StateProcess.SYNC.description)
        } else {
            databaseRepository.saveCustomerInformation(registrationDto, StateProcess.PENDING_INSERT.description)
        }
    }
}