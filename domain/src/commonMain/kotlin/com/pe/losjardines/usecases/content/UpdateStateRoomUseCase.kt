package com.pe.losjardines.usecases.content

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.repository.FirestoreRepository
import com.pe.losjardines.usecases.model.RoomDto
import com.pe.losjardines.utils.FirestoreConstance
import kotlin.coroutines.cancellation.CancellationException

class UpdateStateRoomUseCase(
    val firestoreRepository: FirestoreRepository
) {
    @Throws(Exception::class, CancellationException::class, Failure::class)
    suspend fun run(room: RoomDto) {
        val stateValueUpdate = mapOf(FirestoreConstance.STATE_FIELD to room.state)
        val result = firestoreRepository.update(FirestoreConstance.ROOMS_COLLECTION, room.room, stateValueUpdate)
        if (result is Either.Error) throw result.error
    }
}
