package com.pe.losjardines.firebase.firestore.model

import kotlinx.serialization.Serializable

@Serializable
data class RoomStateNetwork(
    val room: String = "",
    val price: Int = 0,
    val state: Boolean = false
)
