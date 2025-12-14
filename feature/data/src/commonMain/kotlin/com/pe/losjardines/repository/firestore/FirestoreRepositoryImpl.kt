package com.pe.losjardines.repository.firestore

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.base.network.BaseClient
import com.pe.losjardines.firebase.firestore.FirestoreManager
import com.pe.losjardines.repository.FirestoreRepository
import com.pe.losjardines.repository.firestore.mapper.toData
import com.pe.losjardines.usecases.model.RegistrationDto

class FirestoreRepositoryImpl(
    private val firebaseManager: FirestoreManager
): BaseClient(), FirestoreRepository {
    override suspend fun send(registrationDto: RegistrationDto): Either<Failure, Unit> {
        return callFirestore {
            firebaseManager.send(registrationDto.toData())
        }
    }

    override suspend fun delete(collection: String, documentPath: String): Either<Failure, Unit> {
        return callFirestore {
            firebaseManager.delete(collection, documentPath)
        }
    }
}