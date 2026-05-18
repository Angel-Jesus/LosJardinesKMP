package com.pe.losjardines.repository.db

import com.pe.losjardines.base.database.BaseDatabase
import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.db.DatabaseManager
import com.pe.losjardines.repository.DatabaseRepository
import com.pe.losjardines.repository.db.mapper.toData
import com.pe.losjardines.repository.db.mapper.toDomain
import com.pe.losjardines.usecases.model.CountryDto
import com.pe.losjardines.usecases.model.TravelReasonDto
import com.pe.losjardines.usecases.model.RegionDto
import com.pe.losjardines.usecases.model.RegistrationDto
import com.pe.losjardines.utils.getDateNow

class DatabaseRepositoryImpl(
    private val databaseManager: DatabaseManager
): BaseDatabase(), DatabaseRepository {
    override suspend fun isSyncronized(): Boolean = databaseManager.isSyncronized()

    override suspend fun syncRegions() = databaseManager.syncRegion()

    override suspend fun syncCountries() = databaseManager.syncCountry()

    override suspend fun syncReasonTravels() = databaseManager.syncReasonTravels()


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

    override suspend fun saveCustomerInformation(registrationDto: RegistrationDto, state: String): Either<Failure, Unit> {
        return callDatabase {
            databaseManager.saveCustomerInformation(registrationDto.toData(state))
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
}