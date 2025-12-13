package com.pe.losjardines.repository.firestore

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.base.network.BaseClient
import com.pe.losjardines.firebase.firestore.FirestoreService
import com.pe.losjardines.repository.FirestoreRepository
import com.pe.losjardines.repository.firestore.mapper.toData
import com.pe.losjardines.usecases.model.RegistrationDto

class FirestoreRepositoryImpl(
    private val firestoreService: FirestoreService
): BaseClient(), FirestoreRepository {
    override suspend fun send(registrationDto: RegistrationDto): Either<Failure, Unit> {
        return callFirestore {
            firestoreService.send(registrationDto.toData())
        }
    }

    override suspend fun delete(collection: String, documentPath: String): Either<Failure, Unit> {
        return callFirestore {
            firestoreService.delete(collection, documentPath)
        }
    }
}