package com.pe.losjardines.repository.firestore.mapper

import com.pe.losjardines.firebase.firestore.FirestoreConstance
import com.pe.losjardines.usecases.model.FielTypeRegister

fun FielTypeRegister.toFirestoreField(): String = when (this) {
    FielTypeRegister.FULL_NAME -> FirestoreConstance.NAME_FIELD
    FielTypeRegister.SEX -> FirestoreConstance.SEX_FIELD
    FielTypeRegister.COUNTRY_OF_RESIDENCE -> FirestoreConstance.COUNTRY_FIELD
    FielTypeRegister.REGION_OF_RESIDENCE -> FirestoreConstance.REGION_FIELD
    FielTypeRegister.DOCUMENT_TYPE -> FirestoreConstance.TYPE_DOCUMENT_FIELD
    FielTypeRegister.DOCUMENT_NUMBER -> FirestoreConstance.NUMBER_DOCUMENT_FIELD
    FielTypeRegister.TRAVEL_REASON -> FirestoreConstance.REASON_TRAVEL_FIELD
    FielTypeRegister.CHECK_IN_DATE -> FirestoreConstance.DATE_ENTER_FIELD
    FielTypeRegister.CHECK_OUT_DATE -> FirestoreConstance.DATE_EXIT_FIELD
    FielTypeRegister.TYPE_ROOMS -> FirestoreConstance.TYPE_ROOMS_FIELD
    FielTypeRegister.ROOM -> FirestoreConstance.ROOMS_FIELD
    FielTypeRegister.RATE -> FirestoreConstance.FEE_FIELD
    FielTypeRegister.OBSERVATION -> FirestoreConstance.OBSERVATION_FIELD
}
