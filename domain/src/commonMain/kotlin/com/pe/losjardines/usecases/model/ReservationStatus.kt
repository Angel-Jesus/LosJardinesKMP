package com.pe.losjardines.usecases.model

enum class ReservationStatus(val value: String, val description: String) {
    CHECK_IN("Check-in", "Reservado"),
    OCCUPIED("Occupied", "Ocupado"),
    CANCELED("Canceled", "Cancelado");

    companion object {
        val options = entries.map { it.value }
        fun getByDescription(description: String) = entries.find { it.description == description }
        fun getDescriptions(value: String) = entries.find { it.value == value }?.description.orEmpty()
    }
}
