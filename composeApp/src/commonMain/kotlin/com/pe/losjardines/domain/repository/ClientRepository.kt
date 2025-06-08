package com.pe.losjardines.domain.repository

import com.pe.losjardines.core.base.either.Either
import com.pe.losjardines.core.base.error.Failure
import com.pe.losjardines.data.network.dto.ClientDto

interface ClientRepository {
    suspend fun getClients(): Either<Failure, List<ClientDto>>
    suspend fun sendClient(dataClient: ClientDto): Either<Failure, Boolean>
    suspend fun updateClient(dataClient: ClientDto): Either<Failure, Boolean>
    suspend fun deleteClient(idClient: Int): Either<Failure, Boolean>
}