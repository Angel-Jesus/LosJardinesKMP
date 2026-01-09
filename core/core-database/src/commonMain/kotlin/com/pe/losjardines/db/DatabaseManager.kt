package com.pe.losjardines.db

import com.pe.losjardines.cache.Database

class DatabaseManager(
    private val database: Database
) {
    fun isSyncronized() = database.databaseQueries.getCountries().executeAsList().isNotEmpty()
    fun syncCountry() = database.databaseQueries.insertCountries()
    fun syncRegion() = database.databaseQueries.insertRegions()
    fun syncReasonTravels() = database.databaseQueries.insertReasonTravel()

    fun getCountries() = database.databaseQueries.getCountries().executeAsList()
    fun getRegionsByCountry(countryId: String) = database.databaseQueries.getRegionsByCountry(countryId).executeAsList()
    fun getReasonTravels() = database.databaseQueries.getReasonTravels().executeAsList()

}