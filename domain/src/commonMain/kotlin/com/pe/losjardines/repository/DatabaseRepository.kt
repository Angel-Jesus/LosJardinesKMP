package com.pe.losjardines.repository

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.usecases.model.CountryDto
import com.pe.losjardines.usecases.model.TravelReasonDto
import com.pe.losjardines.usecases.model.RegionDto
import com.pe.losjardines.usecases.model.RegistrationDto
import com.pe.losjardines.usecases.model.ReservationDto
import com.pe.losjardines.usecases.model.TypeRoomDto

interface DatabaseRepository {
    suspend fun isSyncronized(): Boolean
    suspend fun syncRegions()
    suspend fun syncCountries()
    suspend fun syncReasonTravels()
    suspend fun syncTypeRooms()

    suspend fun getCountries(): Either<Failure, List<CountryDto>>
    suspend fun getRegionsByCountry(countryId: String): Either<Failure, List<RegionDto>>

    suspend fun getReasonTravels(): Either<Failure, List<TravelReasonDto>>
    suspend fun getTypeRoom(): Either<Failure, List<TypeRoomDto>>

    suspend fun saveCustomerInformation(registrationDto: RegistrationDto, state: String): Either<Failure, Unit>
    suspend fun getRegistrationById(id: Long): Either<Failure, RegistrationDto>
    suspend fun moveToTrash(registrationDto: RegistrationDto, dateDeleted: Long, state: String): Either<Failure, Unit>
    suspend fun getClientsRegister(
        dateInit: Long,
        dateLast: Long,
        searchDni: String?
    ): Either<Failure, List<RegistrationDto>>
    suspend fun updateClientInformation(state: String, registrationDto: RegistrationDto): Either<Failure, Unit>
    suspend fun deleteAllRegisters(): Either<Failure, Unit>
    suspend fun insertRegistrations(registrations: List<RegistrationDto>, state: String): Either<Failure, Unit>

    suspend fun saveReservation(reservationDto: ReservationDto, state: String): Either<Failure, Unit>
    suspend fun getReservationById(id: Long): Either<Failure, ReservationDto>
    suspend fun moveReservationToTrash(reservationDto: ReservationDto, dateDeleted: Long, state: String): Either<Failure, Unit>
    suspend fun getReservations(
        state: String,
        limit: Long,
        offset: Long
    ): Either<Failure, List<ReservationDto>>
    suspend fun updateReservationAttentionState(id: Long, attentionState: String, state: String): Either<Failure, Unit>
}