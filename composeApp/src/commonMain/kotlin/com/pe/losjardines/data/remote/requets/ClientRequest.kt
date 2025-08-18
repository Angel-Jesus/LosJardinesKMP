package com.pe.losjardines.data.remote.requets

data class ClientRequest(
    val id: String = "",
    val habitacion: String = "",
    val fecha: String = "",
    val hora: String = "",
    val apellido: String = "",
    val dni: String = "",
    val precio: String = "",
    val procedencia: String = "",
    val observacion: String = "",
    var accion: String = "POST"
)
