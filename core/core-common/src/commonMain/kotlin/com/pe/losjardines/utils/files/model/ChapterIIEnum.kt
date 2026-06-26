package com.pe.losjardines.utils.files.model

enum class AccommodationColumn {
    // CAPACIDAD OFERTADA
    ROOM_WITH_BATH,
    BEDS,
    // ALOJAMIENTO UTILIZADO
    ARRIVALS,
    OCCUPIED_ROOMS,
    OVERNIGHTS,
    // TARIFAS
    RATE_WITH_BATH,
    RATE_WITHOUT_BATH
}
enum class RoomType {
    SINGLE,
    DOUBLE,
    TRIPLE;

    companion object{
        fun getRoomType(type: String): RoomType {
            return when(type.trim().lowercase()){
                "simple" -> SINGLE
                "matrimonial" -> DOUBLE
                "triple" -> TRIPLE
                else -> DOUBLE
            }
        }
    }
}