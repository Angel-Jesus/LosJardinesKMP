package com.pe.losjardines.usecases.content

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.base.usecase.BaseSafeUseCase
import com.pe.losjardines.repository.DatabaseRepository
import com.pe.losjardines.repository.FirestoreRepository
import com.pe.losjardines.usecases.model.FielTypeRegister.Companion.getRegisterUpdate
import com.pe.losjardines.usecases.model.FielTypeRegister.Companion.getValueByField
import com.pe.losjardines.usecases.model.UpdateParams
import com.pe.losjardines.utils.StateProcess

class UpdateClientInfoUseCase (
    val databaseRepository: DatabaseRepository,
    val firestoreRepository: FirestoreRepository
): BaseSafeUseCase<UpdateClientInfoUseCase.Params, Unit>() {
    data class Params(val fieldParams: UpdateParams)

    override suspend fun run(params: Params): Either<Failure, Unit> {
        if(params.fieldParams.registrationDto == null) return Either.Error(Failure.UnknownFailure("Not found client"))
        if(params.fieldParams.fieldTypeRegister == null) return Either.Error(Failure.UnknownFailure("Not found field type"))

        val result = firestoreRepository.update(
            params.fieldParams.registrationDto.collection,
            params.fieldParams.registrationDto.idFirebase,
            mapOf(params.fieldParams.fieldTypeRegister.networkField to params.fieldParams.fieldTypeRegister.getValueByField(params.fieldParams.newValue))
        )

        return when(result){
            is Either.Error -> {
                databaseRepository.updateClientInformation(StateProcess.PENDING_UPDATE.description, params.fieldParams.fieldTypeRegister.getRegisterUpdate(params.fieldParams.newValue, params.fieldParams.registrationDto))
            }
            is Either.Success -> {
                databaseRepository.updateClientInformation(StateProcess.SYNC.description, params.fieldParams.fieldTypeRegister.getRegisterUpdate(params.fieldParams.newValue, params.fieldParams.registrationDto))
            }
        }
    }
}