package com.pe.losjardines.domain.usecase

import com.pe.losjardines.core.base.either.Either
import com.pe.losjardines.core.base.error.Failure
import com.pe.losjardines.core.base.usecase.BaseUseCase
import com.pe.losjardines.domain.repository.ClientNetworkRepository

class DeleteClientUseCase(
    private val repository: ClientNetworkRepository
): BaseUseCase<DeleteClientUseCase.Params, Boolean>() {
    data class Params(val id: Int)
    override suspend fun run(params: Params): Either<Failure, Boolean> {
        return repository.deleteClient(params.id)
    }

}