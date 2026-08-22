package com.pe.losjardines.db

import com.pe.losjardines.cache.Database
import com.pe.losjardines.model.RegistrationDb
import com.pe.losjardines.model.ReservationDb
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

    fun saveReservation(reservationDb: ReservationDb) = database.databaseQueries.insertReservation(
        collection = reservationDb.collection,
        idFirebase = reservationDb.idFirebase,
        country = reservationDb.country,
        dateEnter = reservationDb.dateEnter,
        dateExit = reservationDb.dateExit,
        fee = reservationDb.fee,
        name = reservationDb.name,
        sex = reservationDb.sex,
        typeDocument = reservationDb.typeDocument,
        numberDocument = reservationDb.numberDocument,
        observation = reservationDb.observation,
        region = reservationDb.region,
        room = reservationDb.room,
        typeRoom = reservationDb.typeRoom,
        companions = reservationDb.companions,
        state = reservationDb.state,
        attentionState = reservationDb.attentionState
    )

    fun getReservationById(id: Long) = database.databaseQueries.getReservationById(id).executeAsOneOrNull()

    fun updateReservationAttentionState(id: Long, attentionState: String, state: String) =
        database.databaseQueries.updateReservationAttentionState(attentionState = attentionState, state = state, id = id)

    fun getReservations(state: String, limit: Long, offset: Long) = database.databaseQueries.getReservationsByState(
        attentionState = state,
        limit = limit,
        offset = offset
    ).executeAsList()

    fun deleteAllReservations() = database.databaseQueries.deleteAllReservations()

    fun insertReservations(reservations: List<ReservationDb>) = database.databaseQueries.transaction {
        reservations.forEach { reservationDb ->
            database.databaseQueries.insertReservation(
                collection = reservationDb.collection,
                idFirebase = reservationDb.idFirebase,
                country = reservationDb.country,
                dateEnter = reservationDb.dateEnter,
                dateExit = reservationDb.dateExit,
                fee = reservationDb.fee,
                name = reservationDb.name,
                sex = reservationDb.sex,
                typeDocument = reservationDb.typeDocument,
                numberDocument = reservationDb.numberDocument,
                observation = reservationDb.observation,
                region = reservationDb.region,
                room = reservationDb.room,
                typeRoom = reservationDb.typeRoom,
                companions = reservationDb.companions,
                state = reservationDb.state,
                attentionState = reservationDb.attentionState
            )
        }
    }

    fun moveReservationToTrash(trashDb: TrashDb, reservationId: Long) = database.databaseQueries.transaction {
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
        database.databaseQueries.deleteReservationById(reservationId)
    }

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