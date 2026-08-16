package com.pe.losjardines.repository.db

import com.pe.losjardines.base.database.BaseDatabase
import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.db.DatabaseManager
import com.pe.losjardines.repository.DatabaseRepository
import com.pe.losjardines.repository.db.mapper.toData
import com.pe.losjardines.repository.db.mapper.toDomain
import com.pe.losjardines.repository.db.mapper.toTrashData
import com.pe.losjardines.usecases.model.CountryDto
import com.pe.losjardines.usecases.model.RegionDto
import com.pe.losjardines.usecases.model.RegistrationDto
import com.pe.losjardines.usecases.model.ReservationDto
import com.pe.losjardines.usecases.model.TravelReasonDto
import com.pe.losjardines.usecases.model.TypeRoomDto

class DatabaseRepositoryImpl(
    private val databaseManager: DatabaseManager
): BaseDatabase(), DatabaseRepository {
    override suspend fun isSyncronized(): Boolean = databaseManager.isSyncronized()

    override suspend fun syncRegions() = databaseManager.syncRegion()

    override suspend fun syncCountries() = databaseManager.syncCountry()

    override suspend fun syncReasonTravels() = databaseManager.syncReasonTravels()
    override suspend fun syncTypeRooms() = databaseManager.syncTypeRooms()


    override suspend fun getCountries(): Either<Failure, List<CountryDto>> {
        return callDatabase {
            databaseManager.getCountries().map { it.toDomain() }
        }
    }

    override suspend fun getRegionsByCountry(countryId: String): Either<Failure, List<RegionDto>> {
        return callDatabase {
            databaseManager.getRegionsByCountry(countryId).map { it.toDomain() }
        }
    }

    override suspend fun getReasonTravels(): Either<Failure, List<TravelReasonDto>> {
        return callDatabase {
            databaseManager.getReasonTravels().map { it.toDomain() }
        }
    }

    override suspend fun getTypeRoom(): Either<Failure, List<TypeRoomDto>> {
        return callDatabase {
            databaseManager.getTypeRoom().map { it.toDomain() }
        }
    }

    override suspend fun saveCustomerInformation(registrationDto: RegistrationDto, state: String): Either<Failure, Unit> {
        return callDatabase {
            databaseManager.saveCustomerInformation(registrationDto.toData(state))
        }
    }

    override suspend fun getRegistrationById(id: Long): Either<Failure, RegistrationDto> {
        return callDatabase {
            databaseManager.getRegistrationById(id)?.toDomain()
        }
    }

    override suspend fun moveToTrash(
        registrationDto: RegistrationDto,
        dateDeleted: Long,
        state: String
    ): Either<Failure, Unit> {
        return callDatabase {
            databaseManager.moveToTrash(
                registrationDto.toTrashData(dateDeleted, state),
                registrationDto.id ?: 0L
            )
        }
    }

    override suspend fun getClientsRegister(
        dateInit: Long,
        dateLast: Long,
        searchDni: String?
    ): Either<Failure, List<RegistrationDto>> {
        return callDatabase {
            databaseManager.getClientsRegister(dateInit, dateLast, searchDni.orEmpty()).map { it.toDomain() }
        }
    }

    override suspend fun updateClientInformation(state: String, registrationDto: RegistrationDto): Either<Failure, Unit> {
        return callDatabase {
            databaseManager.updateClientInformation(registrationDto.toData(state))
        }
    }

    override suspend fun deleteAllRegisters(): Either<Failure, Unit> {
        return callDatabase {
            databaseManager.deleteAllRegistration()
        }
    }

    override suspend fun insertRegistrations(registrations: List<RegistrationDto>, state: String): Either<Failure, Unit> {
        return callDatabase {
            databaseManager.insertRegistrations(registrations.map { it.toData(state) })
        }
    }

    override suspend fun saveReservation(reservationDto: ReservationDto, state: String): Either<Failure, Unit> {
        return callDatabase {
            databaseManager.saveReservation(reservationDto.toData(state))
        }
    }

    override suspend fun getReservationById(id: Long): Either<Failure, ReservationDto> {
        return callDatabase {
            databaseManager.getReservationById(id)?.toDomain()
        }
    }

    override suspend fun moveReservationToTrash(
        reservationDto: ReservationDto,
        dateDeleted: Long,
        state: String
    ): Either<Failure, Unit> {
        return callDatabase {
            databaseManager.moveReservationToTrash(
                reservationDto.toTrashData(dateDeleted, state),
                reservationDto.id ?: 0L
            )
        }
    }

    override suspend fun getReservations(
        state: String,
        limit: Long,
        offset: Long
    ): Either<Failure, List<ReservationDto>> {
        return callDatabase {
            databaseManager.getReservations(state, limit, offset).map { it.toDomain() }
        }
    }

    override suspend fun updateReservationAttentionState(id: Long, attentionState: String, state: String): Either<Failure, Unit> {
        return callDatabase {
            databaseManager.updateReservationAttentionState(id, attentionState, state)
        }
    }
}