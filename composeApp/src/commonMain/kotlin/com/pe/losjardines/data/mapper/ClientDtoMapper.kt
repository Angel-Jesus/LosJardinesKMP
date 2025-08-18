package com.pe.losjardines.data.mapper

import com.pe.losjardines.data.remote.dto.ClientDto
import com.pe.losjardines.domain.model.ClientModel

fun ClientDto.toDomain(): ClientModel = ClientModel(
    id = this.id,
    room = this.habitacion,
    date = this.fecha,
    time = this.hora,
    name = this.apellidos,
    dni = this.dni,
    price = this.precio,
    origin = this.procedencia,
    observation = this.observacion,
)

fun ClientModel.toDto(): ClientDto = ClientDto(
    id = this.id,
    habitacion = this.room,
    fecha = this.date,
    hora = this.time,
    apellidos = this.name,
    dni = this.dni,
    precio = this.price,
    procedencia = this.origin,
    observacion = this.observation,
)