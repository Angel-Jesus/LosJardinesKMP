package com.pe.losjardines.usecases.model

import com.pe.losjardines.utils.FirestoreConstance
import com.pe.losjardines.utils.dateToEpochMillis

data class UpdateParams(
    val registrationDto: RegistrationDto?,
    val newValue: String,
    val fieldTypeRegister: FielTypeRegister?
)

enum class FielTypeRegister(val networkField: String){
    FULL_NAME(FirestoreConstance.NAME_FIELD),
    SEX(FirestoreConstance.SEX_FIELD),
    COUNTRY_OF_RESIDENCE(FirestoreConstance.COUNTRY_FIELD),
    REGION_OF_RESIDENCE(FirestoreConstance.REGION_FIELD),
    DOCUMENT_TYPE(FirestoreConstance.TYPE_DOCUMENT_FIELD),
    DOCUMENT_NUMBER(FirestoreConstance.NUMBER_DOCUMENT_FIELD),
    TRAVEL_REASON(FirestoreConstance.REASON_TRAVEL_FIELD),
    CHECK_IN_DATE(FirestoreConstance.DATE_ENTER_FIELD),
    CHECK_OUT_DATE(FirestoreConstance.DATE_EXIT_FIELD),
    TYPE_ROOMS(FirestoreConstance.TYPE_ROOMS_FIELD),
    ROOM(FirestoreConstance.ROOMS_FIELD),
    RATE(FirestoreConstance.FEE_FIELD),
    OBSERVATION(FirestoreConstance.OBSERVATION_FIELD);

    companion object {
        fun FielTypeRegister.getValueByField(newValue: String): Any {
            return when (this) {
                CHECK_IN_DATE -> newValue.dateToEpochMillis() ?: 0L
                CHECK_OUT_DATE -> newValue.dateToEpochMillis() ?: 0L
                else -> newValue
            }
        }

        fun FielTypeRegister.getRegisterUpdate(newValue: String, registrationDto: RegistrationDto): RegistrationDto {
            return when (this) {
                FULL_NAME -> registrationDto.copy(name = newValue)
                SEX -> registrationDto.copy(sex = newValue)
                COUNTRY_OF_RESIDENCE -> registrationDto.copy(country = newValue)
                REGION_OF_RESIDENCE -> registrationDto.copy(region = newValue)
                DOCUMENT_TYPE -> registrationDto.copy(typeDocument = newValue)
                DOCUMENT_NUMBER -> registrationDto.copy(numberDocument = newValue)
                TRAVEL_REASON -> registrationDto.copy(reasonTravel = newValue)
                CHECK_IN_DATE -> registrationDto.copy(dateEnter = newValue)
                CHECK_OUT_DATE -> registrationDto.copy(dateExit = newValue)
                TYPE_ROOMS -> registrationDto.copy(typeRoom = newValue)
                ROOM -> registrationDto.copy(room = newValue)
                RATE -> registrationDto.copy(fee = newValue)
                OBSERVATION -> registrationDto.copy(observation = newValue)
            }
        }
    }
}
