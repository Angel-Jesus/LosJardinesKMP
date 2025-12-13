package com.pe.losjardines.repository.firestore.mapper

import com.pe.losjardines.firebase.firestore.model.Registration
import com.pe.losjardines.usecases.model.RegistrationDto

fun RegistrationDto.toData(): Registration = Registration(
    id = this.id,
    birthday = this.birthday,
    country = this.country,
    dateEnter = this.dateEnter,
    dateExit = this.dateExit,
    fee = this.fee,
    hour = this.hour,
    name = this.name,
    typeDocument = this.typeDocument,
    numberDocument = this.numberDocument,
    observation = this.observation,
    reasonTravel = this.reasonTravel,
    region = this.region,
    room = this.room,
    companions = this.companions
)