package com.pe.losjardines.data.repository

import com.pe.losjardines.core.base.either.Either
import com.pe.losjardines.core.base.error.Failure
import com.pe.losjardines.core.base.network.BaseClient
import com.pe.losjardines.core.utils.ConnectionUtils
import com.pe.losjardines.data.network.ApiService
import com.pe.losjardines.data.network.dto.ClientDto
import com.pe.losjardines.data.network.requets.ClientRequest
import com.pe.losjardines.domain.repository.ClientRepository

class ClientRepositoryImpl(
    private val apiService: ApiService,
    connectionUtils: ConnectionUtils,
): ClientRepository, BaseClient(connectionUtils) {
    override suspend fun getClients(): Either<Failure, List<ClientDto>> {
        return callService {
            apiService.getClientList()
        }
    }

    override suspend fun sendClient(dataClient: ClientDto): Either<Failure, Boolean> {
        return callService {
            apiService.sendClient(
                ClientRequest(
                    id = dataClient.id.toString(),
                    dni = dataClient.dni,
                    hora = dataClient.hora,
                    precio = dataClient.precio,
                    fecha = dataClient.fecha,
                    habitacion = dataClient.habitacion,
                    apellido = dataClient.apellidos,
                    observacion = dataClient.observacion,
                    procedencia = dataClient.procedencia,
                    accion = "POST"
                )
            )
        }
    }

    override suspend fun updateClient(dataClient: ClientDto): Either<Failure, Boolean> {
        return callService {
            apiService.sendClient(
                ClientRequest(
                    id = dataClient.id.toString(),
                    dni = dataClient.dni,
                    hora = dataClient.hora,
                    precio = dataClient.precio,
                    fecha = dataClient.fecha,
                    habitacion = dataClient.habitacion,
                    apellido = dataClient.apellidos,
                    observacion = dataClient.observacion,
                    procedencia = dataClient.procedencia,
                    accion = "UPDATE"
                )
            )
        }
    }

    override suspend fun deleteClient(idClient: Int): Either<Failure, Boolean> {
        return callService {
            apiService.deleteClient(
                ClientRequest(
                    id = idClient.toString(),
                    accion = "DELETE"
                )
            )
        }
    }
}