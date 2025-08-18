package com.pe.losjardines.data.remote

import com.pe.losjardines.BuildKonfig
import com.pe.losjardines.data.remote.requets.ClientRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

class ApiService(private val httpClient: HttpClient) {
    suspend fun getClientList(): HttpResponse {
        return httpClient.get(BuildKonfig.ROUTE_AJ)
    }

    suspend fun sendClient(request: ClientRequest): HttpResponse{
        return httpClient.get(BuildKonfig.ROUTE_AJ){
            url {
                parameters.append("id", request.id)
                parameters.append("dni", request.dni)
                parameters.append("hora", request.hora)
                parameters.append("precio", request.precio)
                parameters.append("fecha", request.fecha)
                parameters.append("habitacion", request.habitacion)
                parameters.append("apellido", request.apellido)
                parameters.append("observacion", request.observacion)
                parameters.append("procedencia", request.procedencia)
                parameters.append("accion", request.accion)
            }
        }
    }

    suspend fun deleteClient(request: ClientRequest): HttpResponse{
        return httpClient.get(BuildKonfig.ROUTE_AJ){
            url {
                parameters.append("id", request.id)
                parameters.append("accion", request.accion)
            }
        }
    }
}