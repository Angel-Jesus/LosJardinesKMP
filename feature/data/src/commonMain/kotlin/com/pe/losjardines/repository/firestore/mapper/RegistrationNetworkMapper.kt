package com.pe.losjardines.repository.firestore.mapper

import com.pe.losjardines.firebase.firestore.model.RegistrationNetwork
import com.pe.losjardines.usecases.model.RegistrationDto
import com.pe.losjardines.utils.dateToEpochMillis

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
    companions = this.companions
)