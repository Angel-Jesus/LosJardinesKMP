package com.pe.losjardines.repository.db.mapper

import com.pe.losjardines.model.RegistrationDb
import com.pe.losjardines.usecases.model.RegistrationDto
import com.pe.losjardines.utils.dateToEpochMillis
import com.pe.losjardines.utils.toLocalDate
import com.pe.losjardines.utils.toSmartString
import compelosjardinescache.Registration

fun RegistrationDto.toData(state: String): RegistrationDb = RegistrationDb(
    id = this.id,
    collection = this.collection,
    idFirebase = this.idFirebase,
    country = this.country,
    dateEnter = this.dateEnter.dateToEpochMillis(),
    dateExit = this.dateExit.dateToEpochMillis(),
    fee = this.fee.toDoubleOrNull(),
    name = this.name,
    sex = this.sex,
    typeDocument = this.typeDocument,
    numberDocument = this.numberDocument,
    observation = this.observation,
    reasonTravel = this.reasonTravel,
    region = this.region,
    room = this.room,
    companions = "",
    state = state
)

fun Registration.toDomain(): RegistrationDto = RegistrationDto(
    id = this.id,
    collection = this.collection,
    idFirebase = this.idFirebase,
    country = this.country.orEmpty(),
    dateEnter = this.dateEnter.toLocalDate(),
    dateExit = this.dateExit.toLocalDate(),
    fee = this.fee.toSmartString(),
    name = this.name.orEmpty(),
    sex = this.sex.orEmpty(),
    typeDocument = this.typeDocument.orEmpty(),
    numberDocument = this.numberDocument.orEmpty(),
    observation = this.observation.orEmpty(),
    reasonTravel = this.reasonTravel.orEmpty(),
    region = this.region.orEmpty(),
    room = this.room.orEmpty(),
    companions = emptyList() //this.companions
)