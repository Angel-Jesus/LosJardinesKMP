package com.pe.losjardines.repository.firestore.mapper

import com.pe.losjardines.firebase.firestore.model.ReservationNetwork
import com.pe.losjardines.firebase.firestore.model.TrashNetwork
import com.pe.losjardines.usecases.model.ReservationDto
import com.pe.losjardines.utils.dateToEpochMillis
import com.pe.losjardines.utils.toLocalDate

fun ReservationDto.toData(): ReservationNetwork = ReservationNetwork(
    id = this.idFirebase,
    country = this.country,
    dateEnter = this.dateEnter.dateToEpochMillis() ?: 0L,
    dateExit = this.dateExit.dateToEpochMillis() ?: 0L,
    fee = this.fee,
    name = this.userName,
    sex = this.sex,
    typeDocument = this.typeDocument,
    numberDocument = this.numberDocument,
    observation = this.observation,
    region = this.region,
    room = this.room,
    typeRoom = this.typeRoom,
    attentionState = this.attentionState,
    companions = this.companions
)

fun ReservationNetwork.toDomain(collection: String): ReservationDto = ReservationDto(
    collection = collection,
    idFirebase = this.id,
    country = this.country,
    dateEnter = this.dateEnter.toLocalDate(),
    dateExit = this.dateExit.toLocalDate(),
    fee = this.fee,
    userName = this.name,
    sex = this.sex,
    typeDocument = this.typeDocument,
    numberDocument = this.numberDocument,
    observation = this.observation,
    region = this.region,
    typeRoom = this.typeRoom,
    room = this.room,
    attentionState = this.attentionState,
    companions = this.companions
)

fun ReservationDto.toTrashData(dateDeleted: Long): TrashNetwork = TrashNetwork(
    id = this.idFirebase,
    country = this.country,
    dateEnter = this.dateEnter.dateToEpochMillis() ?: 0L,
    dateExit = this.dateExit.dateToEpochMillis() ?: 0L,
    fee = this.fee,
    name = this.userName,
    sex = this.sex,
    typeDocument = this.typeDocument,
    numberDocument = this.numberDocument,
    observation = this.observation,
    reasonTravel = "",
    region = this.region,
    room = this.room,
    companions = this.companions,
    dateDeleted = dateDeleted
)
