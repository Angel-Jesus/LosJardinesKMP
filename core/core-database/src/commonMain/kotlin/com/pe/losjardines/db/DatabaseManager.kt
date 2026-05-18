package com.pe.losjardines.db

import com.pe.losjardines.cache.Database
import com.pe.losjardines.model.RegistrationDb

class DatabaseManager(
    private val database: Database
) {
    fun isSyncronized() = database.databaseQueries.getCountries().executeAsList().isNotEmpty()
    fun syncCountry() = database.databaseQueries.insertCountries()
    fun syncRegion() = database.databaseQueries.insertRegions()
    fun syncReasonTravels() = database.databaseQueries.insertReasonTravel()

    fun getCountries() = database.databaseQueries.getCountries().executeAsList()
    fun getRegionsByCountry(countryId: String) =
        database.databaseQueries.getRegionsByCountry(countryId).executeAsList()

    fun getReasonTravels() = database.databaseQueries.getReasonTravels().executeAsList()
    fun saveCustomerInformation(registrationDb: RegistrationDb) = database.databaseQueries.insertRegistration(
        collection = registrationDb.collection,
        idFirebase = registrationDb.idFirebase,
        country = registrationDb.country,
        dateEnter = registrationDb.dateEnter,
        dateExit = registrationDb.dateExit,
        fee = registrationDb.fee,
        name = registrationDb.name,
        sex = registrationDb.sex,
        typeDocument = registrationDb.typeDocument,
        numberDocument = registrationDb.numberDocument,
        observation = registrationDb.observation,
        reasonTravel = registrationDb.reasonTravel,
        region = registrationDb.region,
        room = registrationDb.room,
        companions = registrationDb.companions,
        state = registrationDb.state
    )

    fun getClientsRegister(dateInit: Long, dateLast: Long, dni: String) = database.databaseQueries.getRegistersByMonth(
        dateEnter = dateInit,
        dateEnter_ = dateLast,
        value_ = dni,
        numberDocument = "%$dni%"
    ).executeAsList()

    fun updateClientInformation(registrationDb: RegistrationDb) = database.databaseQueries.updateRegistration(
        collection = registrationDb.collection,
        idFirebase = registrationDb.idFirebase,
        country = registrationDb.country,
        dateEnter = registrationDb.dateEnter,
        dateExit = registrationDb.dateExit,
        fee = registrationDb.fee,
        name = registrationDb.name,
        sex = registrationDb.sex,
        typeDocument = registrationDb.typeDocument,
        numberDocument = registrationDb.numberDocument,
        observation = registrationDb.observation,
        reasonTravel = registrationDb.reasonTravel,
        region = registrationDb.region,
        room = registrationDb.room,
        companions = null,
        state = registrationDb.state,
        id = registrationDb.id ?: 0L
    )
}