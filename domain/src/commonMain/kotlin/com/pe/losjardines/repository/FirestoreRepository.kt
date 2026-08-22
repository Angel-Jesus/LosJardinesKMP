package com.pe.losjardines.repository

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.usecases.model.FielTypeRegister
import com.pe.losjardines.usecases.model.RegistrationDto
import com.pe.losjardines.usecases.model.ReservationDto
import com.pe.losjardines.usecases.model.RoomDto

interface FirestoreRepository {
    suspend fun sendClient(registrationDto: RegistrationDto): Either<Failure, Unit>
    suspend fun sendToTrash(registrationDto: RegistrationDto, dateDeleted: Long): Either<Failure, Unit>
    suspend fun deleteClient(registrationDto: RegistrationDto): Either<Failure, Unit>

    suspend fun sendReservation(reservationDto: ReservationDto): Either<Failure, Unit>
    suspend fun updateReservationAttentionState(reservationDto: ReservationDto, attentionState: String): Either<Failure, Unit>
    suspend fun sendReservationToTrash(reservationDto: ReservationDto, dateDeleted: Long): Either<Failure, Unit>
    suspend fun deleteReservation(reservationDto: ReservationDto): Either<Failure, Unit>
    suspend fun updateClientField(registrationDto: RegistrationDto, field: FielTypeRegister, value: Any): Either<Failure, Unit>
    suspend fun updateRoomState(room: RoomDto): Either<Failure, Unit>
    suspend fun getRoomState(): Either<Failure, List<RoomDto>>
    suspend fun getRegistrationsByCollection(collection: String): Either<Failure, List<RegistrationDto>>
    suspend fun getReservationsByCollection(): Either<Failure, List<ReservationDto>>
}