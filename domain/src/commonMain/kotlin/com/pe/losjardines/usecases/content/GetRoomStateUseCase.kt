package com.pe.losjardines.usecases.content

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.base.usecase.BaseSafeUseCase
import com.pe.losjardines.repository.FirestoreRepository
import com.pe.losjardines.usecases.model.RoomDto

class GetRoomStateUseCase(
    private val firestoreRepository: FirestoreRepository
): BaseSafeUseCase<Unit, List<RoomDto>>() {
    override suspend fun run(params: Unit): Either<Failure, List<RoomDto>> = firestoreRepository.getRoomState()
}