package com.pe.losjardines.domain.usecase

import com.pe.losjardines.core.base.either.Either
import com.pe.losjardines.core.base.error.Failure
import com.pe.losjardines.core.base.usecase.BaseUseCase
import com.pe.losjardines.data.mapper.toDto
import com.pe.losjardines.domain.model.ClientModel
import com.pe.losjardines.domain.repository.ClientNetworkRepository

class SendClientUseCase(
    private val repository: ClientNetworkRepository
): BaseUseCase<SendClientUseCase.Params, Boolean>()  {
    data class Params(val dataClient: ClientModel)

    override suspend fun run(params: Params): Either<Failure, Boolean> {
        return repository.sendClient(params.dataClient.toDto())
    }
}