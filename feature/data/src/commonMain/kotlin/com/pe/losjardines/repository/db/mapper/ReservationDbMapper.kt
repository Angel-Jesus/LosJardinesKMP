package com.pe.losjardines.repository.db.mapper

import com.pe.losjardines.model.ReservationDb
import com.pe.losjardines.model.TrashDb
import com.pe.losjardines.usecases.model.ReservationDto
import com.pe.losjardines.usecases.model.ReservationStatus
import com.pe.losjardines.utils.dateToEpochMillis
import com.pe.losjardines.utils.toLocalDate
import com.pe.losjardines.utils.toSmartString
import compelosjardinescache.Reservation

fun ReservationDto.toData(state: String): ReservationDb = ReservationDb(
    id = this.id,
    collection = this.collection,
    idFirebase = this.idFirebase,
    country = this.country,
    dateEnter = this.dateEnter.dateToEpochMillis(),
    dateExit = this.dateExit.dateToEpochMillis(),
    fee = this.fee.toDoubleOrNull(),
    name = this.userName,
    sex = this.sex,
    typeDocument = this.typeDocument,
    numberDocument = this.numberDocument,
    observation = this.observation,
    region = this.region,
    typeRoom = this.typeRoom,
    room = this.room,
    companions = "",
    state = state,
    attentionState = this.attentionState
)

fun ReservationDto.toTrashData(dateDeleted: Long, state: String): TrashDb = TrashDb(
    id = this.id,
    collection = this.collection,
    idFirebase = this.idFirebase,
    country = this.country,
    dateEnter = this.dateEnter.dateToEpochMillis(),
    dateExit = this.dateExit.dateToEpochMillis(),
    fee = this.fee.toDoubleOrNull(),
    name = this.userName,
    sex = this.sex,
    typeDocument = this.typeDocument,
    numberDocument = this.numberDocument,
    observation = this.observation,
    reasonTravel = null,
    region = this.region,
    typeRoom = this.typeRoom,
    room = this.room,
    companions = "",
    dateDeleted = dateDeleted,
    state = state
)

fun Reservation.toDomain(): ReservationDto = ReservationDto(
    id = this.id,
    collection = this.collection,
    idFirebase = this.idFirebase,
    country = this.country.orEmpty(),
    dateEnter = this.dateEnter.toLocalDate(),
    dateExit = this.dateExit.toLocalDate(),
    fee = this.fee.toSmartString(),
    userName = this.name.orEmpty(),
    sex = this.sex.orEmpty(),
    typeDocument = this.typeDocument.orEmpty(),
    numberDocument = this.numberDocument.orEmpty(),
    observation = this.observation.orEmpty(),
    region = this.region.orEmpty(),
    typeRoom = this.typeRoom.orEmpty(),
    room = this.room.orEmpty(),
    state = this.state.orEmpty(),
    attentionState = this.attentionState ?: ReservationStatus.CHECK_IN.value,
    companions = emptyList()
)
