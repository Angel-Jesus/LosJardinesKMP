package com.pe.losjardines.presentation.content.utils

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pe.losjardines.usecases.model.FielTypeRegister

enum class FieldRegistration(val displayName: String, val dimensionWidth: Dp) {
    FULL_NAME("Nombres y Apellidos", 200.dp),
    SEX("Sexo", 48.dp),
    COUNTRY_OF_RESIDENCE("País", 64.dp),
    REGION_OF_RESIDENCE("Región", 64.dp),
    DOCUMENT_TYPE("T. Documento", 100.dp),
    DOCUMENT_NUMBER("N° Documento", 100.dp),
    TRAVEL_REASON("Motivo de Viaje", 200.dp),
    CHECK_IN_DATE("Fecha de ingreso", 120.dp),
    CHECK_OUT_DATE("Fecha de salida", 120.dp),
    TYPE_ROOM("Tipo de habitación", 120.dp),
    ROOM("N° Habitación", 100.dp),
    RATE("Tarifa", 80.dp),
    OBSERVATION("Observación", 200.dp);

    companion object{
        fun FieldRegistration.toFielTypeRegister(): FielTypeRegister {
            return when(this){
                FULL_NAME -> FielTypeRegister.FULL_NAME
                SEX -> FielTypeRegister.SEX
                COUNTRY_OF_RESIDENCE -> FielTypeRegister.COUNTRY_OF_RESIDENCE
                REGION_OF_RESIDENCE -> FielTypeRegister.REGION_OF_RESIDENCE
                DOCUMENT_TYPE -> FielTypeRegister.DOCUMENT_TYPE
                DOCUMENT_NUMBER -> FielTypeRegister.DOCUMENT_NUMBER
                TRAVEL_REASON -> FielTypeRegister.TRAVEL_REASON
                CHECK_IN_DATE -> FielTypeRegister.CHECK_IN_DATE
                CHECK_OUT_DATE -> FielTypeRegister.CHECK_OUT_DATE
                TYPE_ROOM -> FielTypeRegister.TYPE_ROOMS
                ROOM -> FielTypeRegister.ROOM
                RATE -> FielTypeRegister.RATE
                OBSERVATION -> FielTypeRegister.OBSERVATION
            }
        }
    }
}
