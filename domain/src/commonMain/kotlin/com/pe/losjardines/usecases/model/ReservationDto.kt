package com.pe.losjardines.usecases.model

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
