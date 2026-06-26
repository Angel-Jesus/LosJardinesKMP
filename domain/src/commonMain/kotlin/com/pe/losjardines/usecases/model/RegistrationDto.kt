package com.pe.losjardines.usecases.model

data class RegistrationDto(
    val collection: String,
    val id: Long? = null,
    val idFirebase: String = "",
    val country: String,
    val dateEnter: String,
    val dateExit: String,
    val fee: String,
    val name: String,
    val sex: String,
    val typeDocument: String,
    val numberDocument: String,
    val observation: String = "",
    val reasonTravel: String,
    val region: String,
    val typeRoom: String,
    val room: String,
    val companions: List<String> = emptyList()
)
