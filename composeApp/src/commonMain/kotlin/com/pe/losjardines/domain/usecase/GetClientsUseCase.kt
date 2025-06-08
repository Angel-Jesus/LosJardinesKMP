package com.pe.losjardines.domain.usecase

import com.pe.losjardines.core.base.either.Either
import com.pe.losjardines.core.base.error.Failure
import com.pe.losjardines.core.base.usecase.BaseUseCase
import com.pe.losjardines.data.mapper.toDomain
import com.pe.losjardines.domain.model.ClientFilter
import com.pe.losjardines.domain.model.ClientModel
import com.pe.losjardines.domain.repository.ClientRepository
import com.pe.losjardines.domain.utils.filterClients

class GetClientsUseCase(
    private val repository: ClientRepository
): BaseUseCase<GetClientsUseCase.Params, List<ClientModel>>() {
    data class Params(val filter: ClientFilter)

    override suspend fun run(params: Params): Either<Failure, List<ClientModel>> {
        return repository.getClients().mapSafely { list -> list.filterClients(params.filter).map { it.toDomain() } }
    }

}