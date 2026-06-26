package com.pe.losjardines.usecases.content

import com.pe.losjardines.repository.DatabaseRepository

class SyncronizationUseCase(
    private val databaseRepository: DatabaseRepository
) {
    suspend fun invoke(){
        if(databaseRepository.isSyncronized()) return
        databaseRepository.syncCountries()
        databaseRepository.syncRegions()
        databaseRepository.syncReasonTravels()
        databaseRepository.syncTypeRooms()
    }
}