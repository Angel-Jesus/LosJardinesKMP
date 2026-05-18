package com.pe.losjardines.repository

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.usecases.model.RegistrationDto
import com.pe.losjardines.usecases.model.RoomDto

interface FirestoreRepository {
    suspend fun sendClient(registrationDto: RegistrationDto): Either<Failure, Unit>
    suspend fun delete(collection: String, documentPath: String): Either<Failure, Unit>
    suspend fun update(collection: String, documentPath: String, data: Map<String, Any>): Either<Failure, Unit>
    suspend fun getRoomState(): Either<Failure, List<RoomDto>>
}