package com.pe.losjardines.repository

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.usecases.model.RegistrationDto

interface FirestoreRepository {
    suspend fun send(registrationDto: RegistrationDto): Either<Failure, Unit>
    suspend fun delete(collection: String, documentPath: String): Either<Failure, Unit>
}