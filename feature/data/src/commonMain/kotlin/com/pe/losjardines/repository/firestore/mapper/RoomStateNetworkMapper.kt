package com.pe.losjardines.repository.firestore.mapper

import com.pe.losjardines.firebase.firestore.model.RoomStateNetwork
import com.pe.losjardines.usecases.model.RoomDto

fun RoomStateNetwork.toData(): RoomDto = RoomDto(this.room, this.price, this.state)