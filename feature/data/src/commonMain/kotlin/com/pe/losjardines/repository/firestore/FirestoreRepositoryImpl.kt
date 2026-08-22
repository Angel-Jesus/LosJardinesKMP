package com.pe.losjardines.repository.firestore

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.firebase.base.BaseClient
import com.pe.losjardines.firebase.firestore.FirestoreConstance
import com.pe.losjardines.firebase.firestore.FirestoreManager
import com.pe.losjardines.repository.FirestoreRepository
import com.pe.losjardines.repository.firestore.mapper.toData
import com.pe.losjardines.repository.firestore.mapper.toDomain
import com.pe.losjardines.repository.firestore.mapper.toFirestoreField
import com.pe.losjardines.repository.firestore.mapper.toTrashData
import com.pe.losjardines.usecases.model.FielTypeRegister
import com.pe.losjardines.usecases.model.RegistrationDto
import com.pe.losjardines.usecases.model.ReservationDto
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

    override suspend fun sendToTrash(registrationDto: RegistrationDto, dateDeleted: Long): Either<Failure, Unit> {
        return callFirestore {
            firebaseManager.sendTrash(registrationDto.toTrashData(dateDeleted))
        }
    }

    override suspend fun deleteClient(registrationDto: RegistrationDto): Either<Failure, Unit> {
        return callFirestore {
            firebaseManager.delete(registrationDto.collection, registrationDto.idFirebase)
        }
    }

    override suspend fun updateClientField(
        registrationDto: RegistrationDto,
        field: FielTypeRegister,
        value: Any
    ): Either<Failure, Unit> {
        return callFirestore {
            firebaseManager.update(
                registrationDto.collection,
                registrationDto.idFirebase,
                mapOf(field.toFirestoreField() to value)
            )
        }
    }

    override suspend fun updateRoomState(room: RoomDto): Either<Failure, Unit> {
        return callFirestore {
            firebaseManager.update(
                FirestoreConstance.ROOMS_COLLECTION,
                room.room,
                mapOf(FirestoreConstance.STATE_FIELD to room.state)
            )
        }
    }

    override suspend fun getRoomState(): Either<Failure, List<RoomDto>> {
        return callFirestore {
            firebaseManager.getRoomState().map { it.toData() }
        }
    }

    override suspend fun getRegistrationsByCollection(collection: String): Either<Failure, List<RegistrationDto>> {
        return callFirestore {
            firebaseManager.getRegistrations(collection).map { it.toDomain(collection) }
        }
    }

    override suspend fun getReservationsByCollection(): Either<Failure, List<ReservationDto>> {
        return callFirestore {
            firebaseManager.getReservations().map { it.toDomain() }
        }
    }

    override suspend fun sendReservation(reservationDto: ReservationDto): Either<Failure, Unit> {
        return callFirestore {
            firebaseManager.sendReservation(reservationDto.toData())
        }
    }

    override suspend fun updateReservationAttentionState(
        reservationDto: ReservationDto,
        attentionState: String
    ): Either<Failure, Unit> {
        return callFirestore {
            firebaseManager.update(
                FirestoreConstance.RESERVATION_COLLECTION,
                reservationDto.idFirebase,
                mapOf(FirestoreConstance.ATTENTION_STATE_FIELD to attentionState)
            )
        }
    }

    override suspend fun sendReservationToTrash(reservationDto: ReservationDto, dateDeleted: Long): Either<Failure, Unit> {
        return callFirestore {
            firebaseManager.sendTrash(reservationDto.toTrashData(dateDeleted))
        }
    }

    override suspend fun deleteReservation(reservationDto: ReservationDto): Either<Failure, Unit> {
        return callFirestore {
            firebaseManager.delete(FirestoreConstance.RESERVATION_COLLECTION, reservationDto.idFirebase)
        }
    }
}