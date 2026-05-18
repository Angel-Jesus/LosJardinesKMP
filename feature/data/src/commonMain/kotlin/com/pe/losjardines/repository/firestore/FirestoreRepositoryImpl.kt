package com.pe.losjardines.repository.firestore

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.base.network.BaseClient
import com.pe.losjardines.firebase.firestore.FirestoreManager
import com.pe.losjardines.repository.FirestoreRepository
import com.pe.losjardines.repository.firestore.mapper.toData
import com.pe.losjardines.usecases.model.RegistrationDto
import com.pe.losjardines.usecases.model.RoomDto
import com.pe.losjardines.utils.NetworkChecker

class FirestoreRepositoryImpl(
    private val firebaseManager: FirestoreManager,
    networkChecker: NetworkChecker
): BaseClient(networkChecker), FirestoreRepository {
    override suspend fun sendClient(registrationDto: RegistrationDto): Either<Failure, Unit> {
        return callFirestore {
            firebaseManager.send(registrationDto.collection, registrationDto.toData())
        }
    }

    override suspend fun delete(collection: String, documentPath: String): Either<Failure, Unit> {
        return callFirestore {
            firebaseManager.delete(collection, documentPath)
        }
    }

    override suspend fun update(
        collection: String,
        documentPath: String,
        data: Map<String, Any>
    ): Either<Failure, Unit> {
        return callFirestore {
            firebaseManager.update(collection, documentPath, data)
        }
    }

    override suspend fun getRoomState(): Either<Failure, List<RoomDto>> {
        return callFirestore {
            firebaseManager.getRoomState().map { it.toData() }
        }
    }
}