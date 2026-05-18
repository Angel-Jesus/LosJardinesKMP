package com.pe.losjardines.usecases.content

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.base.usecase.BaseSafeUseCase
import com.pe.losjardines.repository.FirestoreRepository
import com.pe.losjardines.usecases.model.RoomDto
import com.pe.losjardines.utils.FirestoreConstance

class UpdateStateRoomUseCase(
    val firestoreRepository: FirestoreRepository
): BaseSafeUseCase<UpdateStateRoomUseCase.Params, Unit>() {
    data class Params(val room: RoomDto)

    override suspend fun run(params: Params): Either<Failure, Unit> {
        val stateValueUpdate = mapOf(FirestoreConstance.STATE_FIELD to params.room.state)
        return firestoreRepository.update(FirestoreConstance.ROOMS_COLLECTION, params.room.room, stateValueUpdate)
    }
}