package com.pe.losjardines.db

import com.pe.losjardines.cache.Database
import com.pe.losjardines.model.RegistrationDb
import com.pe.losjardines.model.TrashDb

class DatabaseManager(
    private val database: Database
) {
    fun isSyncronized() = database.databaseQueries.getCountries().executeAsList().isNotEmpty()
    fun syncCountry() = database.databaseQueries.insertCountries()
    fun syncRegion() = database.databaseQueries.insertRegions()
    fun syncReasonTravels() = database.databaseQueries.insertReasonTravel()
    fun syncTypeRooms() = database.databaseQueries.insertTypeRooms()

    fun getCountries() = database.databaseQueries.getCountries().executeAsList()
    fun getRegionsByCountry(countryId: String) =
        database.databaseQueries.getRegionsByCountry(countryId).executeAsList()

    fun getReasonTravels() = database.databaseQueries.getReasonTravels().executeAsList()

    fun getTypeRoom() = database.databaseQueries.getTypeRooms().executeAsList()
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
        typeRoom = registrationDb.typeRoom,
        companions = registrationDb.companions,
        state = registrationDb.state
    )

    fun getRegistrationById(id: Long) = database.databaseQueries.getRegistrationById(id).executeAsOneOrNull()

    fun deleteAllRegistration() = database.databaseQueries.deleteAllRegistration()

    fun insertRegistrations(registrations: List<RegistrationDb>) = database.databaseQueries.transaction {
        registrations.forEach { registrationDb ->
            database.databaseQueries.insertRegistration(
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
                typeRoom = registrationDb.typeRoom,
                companions = registrationDb.companions,
                state = registrationDb.state
            )
        }
    }

    fun moveToTrash(trashDb: TrashDb, registrationId: Long) = database.databaseQueries.transaction {
        database.databaseQueries.insertTrash(
            collection = trashDb.collection,
            idFirebase = trashDb.idFirebase,
            country = trashDb.country,
            dateEnter = trashDb.dateEnter,
            dateExit = trashDb.dateExit,
            fee = trashDb.fee,
            name = trashDb.name,
            sex = trashDb.sex,
            typeDocument = trashDb.typeDocument,
            numberDocument = trashDb.numberDocument,
            observation = trashDb.observation,
            reasonTravel = trashDb.reasonTravel,
            region = trashDb.region,
            room = trashDb.room,
            typeRoom = trashDb.typeRoom,
            companions = trashDb.companions,
            dateDeleted = trashDb.dateDeleted,
            state = trashDb.state
        )
        database.databaseQueries.deleteById(registrationId)
    }

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
        typeRoom = registrationDb.typeRoom,
        room = registrationDb.room,
        companions = null,
        state = registrationDb.state,
        id = registrationDb.id ?: 0L
    )
}