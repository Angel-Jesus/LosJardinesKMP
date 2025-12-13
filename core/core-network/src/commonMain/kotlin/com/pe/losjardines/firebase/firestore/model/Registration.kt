package com.pe.losjardines.firebase.firestore.model

import kotlinx.serialization.Serializable

@Serializable
data class Registration(
    val id: String,
    val birthday: String,
    val country: String,
    val dateEnter: String,
    val dateExit: String,
    val fee: String,
    val hour: String,
    val name: String,
    val typeDocument: String,
    val numberDocument: String,
    val observation: String = "",
    val reasonTravel: String,
    val region: String,
    val room: String,
    val companions: List<String> = emptyList()
)
