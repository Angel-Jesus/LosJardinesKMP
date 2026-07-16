package com.pe.losjardines.repository.firestore.mapper

import com.pe.losjardines.firebase.firestore.model.RegistrationNetwork
import com.pe.losjardines.firebase.firestore.model.TrashNetwork
import com.pe.losjardines.usecases.model.RegistrationDto
import com.pe.losjardines.utils.dateToEpochMillis
import com.pe.losjardines.utils.toLocalDate

fun RegistrationDto.toData(): RegistrationNetwork = RegistrationNetwork(
    id = this.idFirebase,
    country = this.country,
    dateEnter = this.dateEnter.dateToEpochMillis() ?: 0L,
    dateExit = this.dateExit.dateToEpochMillis() ?: 0L,
    fee = this.fee,
    name = this.name,
    sex = this.sex,
    typeDocument = this.typeDocument,
    numberDocument = this.numberDocument,
    observation = this.observation,
    reasonTravel = this.reasonTravel,
    region = this.region,
    room = this.room,
    typeRoom = this.typeRoom,
    companions = this.companions
)

fun RegistrationNetwork.toDomain(collection: String): RegistrationDto = RegistrationDto(
    collection = collection,
    idFirebase = this.id,
    country = this.country,
    dateEnter = this.dateEnter.toLocalDate(),
    dateExit = this.dateExit.toLocalDate(),
    fee = this.fee,
    name = this.name,
    sex = this.sex,
    typeDocument = this.typeDocument,
    numberDocument = this.numberDocument,
    observation = this.observation,
    reasonTravel = this.reasonTravel,
    region = this.region,
    typeRoom = this.typeRoom,
    room = this.room,
    companions = this.companions
)

fun RegistrationDto.toTrashData(dateDeleted: Long): TrashNetwork = TrashNetwork(
    id = this.idFirebase,
    country = this.country,
    dateEnter = this.dateEnter.dateToEpochMillis() ?: 0L,
    dateExit = this.dateExit.dateToEpochMillis() ?: 0L,
    fee = this.fee,
    name = this.name,
    sex = this.sex,
    typeDocument = this.typeDocument,
    numberDocument = this.numberDocument,
    observation = this.observation,
    reasonTravel = this.reasonTravel,
    region = this.region,
    room = this.room,
    companions = this.companions,
    dateDeleted = dateDeleted
)