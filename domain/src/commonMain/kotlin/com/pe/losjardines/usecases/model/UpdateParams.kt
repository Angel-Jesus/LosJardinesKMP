package com.pe.losjardines.usecases.model

import com.pe.losjardines.utils.dateToEpochMillis

data class UpdateParams(
    val registrationDto: RegistrationDto?,
    val newValue: String,
    val fieldTypeRegister: FielTypeRegister?
)

enum class FielTypeRegister {
    FULL_NAME,
    SEX,
    COUNTRY_OF_RESIDENCE,
    REGION_OF_RESIDENCE,
    DOCUMENT_TYPE,
    DOCUMENT_NUMBER,
    TRAVEL_REASON,
    CHECK_IN_DATE,
    CHECK_OUT_DATE,
    TYPE_ROOMS,
    ROOM,
    RATE,
    OBSERVATION;

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
