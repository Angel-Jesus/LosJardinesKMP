package com.pe.losjardines.repository.db.mapper

import com.pe.losjardines.usecases.model.CountryDto
import com.pe.losjardines.usecases.model.RegionDto
import com.pe.losjardines.usecases.model.TravelReasonDto
import com.pe.losjardines.usecases.model.TypeRoomDto
import compelosjardinescache.Countries
import compelosjardinescache.Reason_travel
import compelosjardinescache.Regions
import compelosjardinescache.Type_room

fun Regions.toDomain(): RegionDto = RegionDto(id = this.id, name = this.name)

fun Countries.toDomain(): CountryDto = CountryDto(id = this.id, name = this.name)

fun Reason_travel.toDomain(): TravelReasonDto = TravelReasonDto(id = this.id, code = this.code, description = this.description)

fun Type_room.toDomain(): TypeRoomDto = TypeRoomDto(code = this.code, description = this.description.orEmpty())