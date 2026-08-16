package com.pe.losjardines.firebase.firestore.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReservationNetwork(
    val id: String,
    val country: String,
    val dateEnter: Long,
    val dateExit: Long,
    val fee: String,
    val name: String,
    val sex: String,
    val typeDocument: String,
    val numberDocument: String,
    val observation: String = "",
    val region: String,
    val room: String,
    @SerialName("typeRooms")
    val typeRoom: String = "",
    val attentionState: String = "",
    val companions: List<String> = emptyList()
)
