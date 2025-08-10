package com.pe.losjardines.domain.usecase

import com.pe.losjardines.core.base.either.Either
import com.pe.losjardines.core.base.error.ErrorMessage
import com.pe.losjardines.core.base.error.Failure
import com.pe.losjardines.core.base.usecase.BaseUseCase
import com.pe.losjardines.data.mapper.toDto
import com.pe.losjardines.domain.repository.ClientRepository
import com.pe.losjardines.presentation.enums.TitleClient
import com.pe.losjardines.presentation.model.DataUpdate

class UpdateClientUseCase(
    private val repository: ClientRepository
): BaseUseCase<UpdateClientUseCase.Params, Boolean>() {
    data class Params(val dataUpdate: DataUpdate)
    override suspend fun run(params: Params): Either<Failure, Boolean> {
        if (params.dataUpdate.userModel == null) return Either.Error(Failure.RequestNull(ErrorMessage.REQUEST_NULL))
        val request = when(params.dataUpdate.type){
            TitleClient.ROOM -> params.dataUpdate.userModel.copy(room = params.dataUpdate.value)
            TitleClient.NAME -> params.dataUpdate.userModel.copy(name = params.dataUpdate.value)
            TitleClient.DNI -> params.dataUpdate.userModel.copy(dni = params.dataUpdate.value)
            TitleClient.PRICE -> params.dataUpdate.userModel.copy(price = params.dataUpdate.value)
            TitleClient.DATE -> params.dataUpdate.userModel.copy(date = params.dataUpdate.value)
            TitleClient.TIME -> params.dataUpdate.userModel.copy(time = params.dataUpdate.value)
            TitleClient.ORIGIN -> params.dataUpdate.userModel.copy(origin = params.dataUpdate.value)
            TitleClient.OBSERVATION -> params.dataUpdate.userModel.copy(observation = params.dataUpdate.value)
        }
        return repository.updateClient(request.toDto())
    }
}