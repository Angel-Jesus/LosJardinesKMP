package com.pe.losjardines.usecases.model

import com.pe.losjardines.utils.calculateNights

data class ReservationDto(
    val collection: String,
    val id: Long? = null,
    val idFirebase: String,
    val userName: String,
    val dateEnter: String,
    val dateExit: String,
    val fee: String,
    val sex: String,
    val typeDocument: String,
    val numberDocument: String,
    val country: String,
    val region: String,
    val typeRoom: String,
    val room: String,
    val observation: String,
    val state: String = "",
    val attentionState: String = ReservationStatus.CHECK_IN.value,
    val companions: List<String> = emptyList()
)

/**
 * Cantidad de días que ocupa la reserva/habitación, calculada entre [ReservationDto.dateEnter]
 * y [ReservationDto.dateExit] (formato `dd/MM/yyyy`). Devuelve como mínimo 1 y 0 si las fechas
 * no son válidas.
 */
fun ReservationDto.occupiedDays(): Int = calculateNights(dateEnter, dateExit)
