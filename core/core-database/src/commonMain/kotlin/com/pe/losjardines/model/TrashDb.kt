package com.pe.losjardines.model

data class TrashDb(
    val id: Long? = null,
    val collection: String,
    val idFirebase: String,
    val country: String?,
    val dateEnter: Long?,
    val dateExit: Long?,
    val fee: Double?,
    val name: String?,
    val sex: String?,
    val typeDocument: String?,
    val numberDocument: String?,
    val observation: String?,
    val reasonTravel: String?,
    val region: String?,
    val typeRoom: String?,
    val room: String?,
    val companions: String?,
    val dateDeleted: Long?,
    val state: String?
)
