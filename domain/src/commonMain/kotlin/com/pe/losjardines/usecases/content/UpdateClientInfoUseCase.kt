package com.pe.losjardines.usecases.content

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.repository.DatabaseRepository
import com.pe.losjardines.repository.FirestoreRepository
import com.pe.losjardines.usecases.model.FielTypeRegister.Companion.getRegisterUpdate
import com.pe.losjardines.usecases.model.FielTypeRegister.Companion.getValueByField
import com.pe.losjardines.usecases.model.UpdateParams
import com.pe.losjardines.utils.StateProcess
import kotlin.coroutines.cancellation.CancellationException

class UpdateClientInfoUseCase(
    val databaseRepository: DatabaseRepository,
    val firestoreRepository: FirestoreRepository
) {
    @Throws(Exception::class, CancellationException::class, Failure::class)
    suspend fun run(fieldParams: UpdateParams) {
        if (fieldParams.registrationDto == null) throw Failure.UnknownFailure("Not found client")
        if (fieldParams.fieldTypeRegister == null) throw Failure.UnknownFailure("Not found field type")

        val result = firestoreRepository.update(
            fieldParams.registrationDto.collection,
            fieldParams.registrationDto.idFirebase,
            mapOf(fieldParams.fieldTypeRegister.networkField to fieldParams.fieldTypeRegister.getValueByField(fieldParams.newValue))
        )

        val saveResult = when (result) {
            is Either.Error -> databaseRepository.updateClientInformation(
                StateProcess.PENDING_UPDATE.description,
                fieldParams.fieldTypeRegister.getRegisterUpdate(fieldParams.newValue, fieldParams.registrationDto)
            )
            is Either.Success -> databaseRepository.updateClientInformation(
                StateProcess.SYNC.description,
                fieldParams.fieldTypeRegister.getRegisterUpdate(fieldParams.newValue, fieldParams.registrationDto)
            )
        }

        if (saveResult is Either.Error) throw saveResult.error
    }
}
