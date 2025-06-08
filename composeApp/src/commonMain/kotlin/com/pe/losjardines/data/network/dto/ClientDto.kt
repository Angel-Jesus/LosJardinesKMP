package com.pe.losjardines.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class ClientDto (
    val id:Int = 0,
    val habitacion:String,
    val fecha:String,
    val hora:String,
    val apellidos:String,
    val dni:String,
    val precio:String,
    val procedencia:String,
    val observacion:String
)