package com.pe.losjardines.presentation.content.utils

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pe.losjardines.usecases.model.FielTypeRegister
import com.pe.losjardines.utils.FirestoreConstance

enum class FieldRegistration(val displayName: String, val dimensionWidth: Dp, val networkField: String) {
    FULL_NAME("Nombres y Apellidos", 200.dp, FirestoreConstance.NAME_FIELD),
    SEX("Sexo", 48.dp, FirestoreConstance.SEX_FIELD),
    COUNTRY_OF_RESIDENCE("País", 64.dp, FirestoreConstance.COUNTRY_FIELD),
    REGION_OF_RESIDENCE("Región", 64.dp, FirestoreConstance.REGION_FIELD),
    DOCUMENT_TYPE("T. Documento", 100.dp, FirestoreConstance.TYPE_DOCUMENT_FIELD),
    DOCUMENT_NUMBER("N° Documento", 100.dp, FirestoreConstance.NUMBER_DOCUMENT_FIELD),
    TRAVEL_REASON("Motivo de Viaje", 200.dp, FirestoreConstance.REASON_TRAVEL_FIELD),
    CHECK_IN_DATE("Fecha de ingreso", 120.dp, FirestoreConstance.DATE_ENTER_FIELD),
    CHECK_OUT_DATE("Fecha de salida", 120.dp, FirestoreConstance.DATE_EXIT_FIELD),
    ROOM("N° Habitación", 100.dp, FirestoreConstance.ROOMS_COLLECTION),
    RATE("Tarifa", 80.dp, FirestoreConstance.FEE_FIELD),
    OBSERVATION("Observación", 200.dp, FirestoreConstance.OBSERVATION_FIELD);

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
                ROOM -> FielTypeRegister.ROOM
                RATE -> FielTypeRegister.RATE
                OBSERVATION -> FielTypeRegister.OBSERVATION
            }
        }
    }
}
