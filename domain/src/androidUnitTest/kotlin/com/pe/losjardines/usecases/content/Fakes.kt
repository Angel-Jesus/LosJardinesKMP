package com.pe.losjardines.usecases.content

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.repository.DatabaseRepository
import com.pe.losjardines.repository.FirestoreRepository
import com.pe.losjardines.usecases.model.CountryDto
import com.pe.losjardines.usecases.model.FielTypeRegister
import com.pe.losjardines.usecases.model.RegionDto
import com.pe.losjardines.usecases.model.RegistrationDto
import com.pe.losjardines.usecases.model.ReservationDto
import com.pe.losjardines.usecases.model.RoomDto
import com.pe.losjardines.usecases.model.TravelReasonDto
import com.pe.losjardines.usecases.model.TypeRoomDto
import com.pe.losjardines.utils.files.ExcelCellUpdate
import com.pe.losjardines.utils.files.ExcelGenerator
import com.pe.losjardines.utils.files.PlatformFile
import com.pe.losjardines.utils.files.PlatformInputStream

/**
 * Fake del repositorio local. Devuelve los registros provistos y captura
 * el rango de fechas con el que fue consultado.
 */
class FakeDatabaseRepository(
    private val registrations: List<RegistrationDto>
) : DatabaseRepository {

    var lastDateInit: Long? = null
        private set
    var lastDateLast: Long? = null
        private set
    var lastSearchDni: String? = null
        private set

    override suspend fun getClientsRegister(
        dateInit: Long,
        dateLast: Long,
        searchDni: String?
    ): Either<Failure, List<RegistrationDto>> {
        lastDateInit = dateInit
        lastDateLast = dateLast
        lastSearchDni = searchDni
        return Either.Success(registrations)
    }

    // --- métodos no usados por este flujo ---
    override suspend fun isSyncronized(): Boolean = true
    override suspend fun syncRegions() = Unit
    override suspend fun syncCountries() = Unit
    override suspend fun syncReasonTravels() = Unit
    override suspend fun syncTypeRooms() = Unit
    override suspend fun getCountries(): Either<Failure, List<CountryDto>> = Either.Success(emptyList())
    override suspend fun getRegionsByCountry(countryId: String): Either<Failure, List<RegionDto>> = Either.Success(emptyList())
    override suspend fun getReasonTravels(): Either<Failure, List<TravelReasonDto>> = Either.Success(emptyList())
    override suspend fun getTypeRoom(): Either<Failure, List<TypeRoomDto>> = Either.Success(emptyList())
    override suspend fun saveCustomerInformation(registrationDto: RegistrationDto, state: String): Either<Failure, Unit> = Either.Success(Unit)
    override suspend fun updateClientInformation(state: String, registrationDto: RegistrationDto): Either<Failure, Unit> = Either.Success(Unit)
    override suspend fun getRegistrationById(id: Long): Either<Failure, RegistrationDto> = Either.Error(Failure.UnknownFailure("fake"))
    override suspend fun moveToTrash(registrationDto: RegistrationDto, dateDeleted: Long, state: String): Either<Failure, Unit> = Either.Success(Unit)
    override suspend fun deleteAllRegisters(): Either<Failure, Unit> = Either.Success(Unit)
    override suspend fun insertRegistrations(registrations: List<RegistrationDto>, state: String): Either<Failure, Unit> = Either.Success(Unit)
    override suspend fun saveReservation(reservationDto: ReservationDto, state: String): Either<Failure, Unit> = Either.Success(Unit)
    override suspend fun getReservationById(id: Long): Either<Failure, ReservationDto> = Either.Error(Failure.UnknownFailure("fake"))
    override suspend fun moveReservationToTrash(reservationDto: ReservationDto, dateDeleted: Long, state: String): Either<Failure, Unit> = Either.Success(Unit)
    override suspend fun getReservations(state: String, limit: Long, offset: Long): Either<Failure, List<ReservationDto>> = Either.Success(emptyList())
    override suspend fun updateReservationAttentionState(id: Long, attentionState: String, state: String): Either<Failure, Unit> = Either.Success(Unit)
    override suspend fun deleteAllReservations(): Either<Failure, Unit> = Either.Success(Unit)
    override suspend fun insertReservations(reservations: List<ReservationDto>, state: String): Either<Failure, Unit> = Either.Success(Unit)
}

/** Fake del repositorio remoto. No participa en la generación del reporte. */
class FakeFirestoreRepository : FirestoreRepository {
    override suspend fun sendClient(registrationDto: RegistrationDto): Either<Failure, Unit> = Either.Success(Unit)
    override suspend fun deleteClient(registrationDto: RegistrationDto): Either<Failure, Unit> = Either.Success(Unit)
    override suspend fun updateClientField(registrationDto: RegistrationDto, field: FielTypeRegister, value: Any): Either<Failure, Unit> = Either.Success(Unit)
    override suspend fun updateRoomState(room: RoomDto): Either<Failure, Unit> = Either.Success(Unit)
    override suspend fun getRoomState(): Either<Failure, List<RoomDto>> = Either.Success(emptyList())
    override suspend fun sendToTrash(registrationDto: RegistrationDto, dateDeleted: Long): Either<Failure, Unit> = Either.Success(Unit)
    override suspend fun sendReservation(reservationDto: ReservationDto): Either<Failure, Unit> = Either.Success(Unit)
    override suspend fun updateReservationAttentionState(reservationDto: ReservationDto, attentionState: String): Either<Failure, Unit> = Either.Success(Unit)
    override suspend fun sendReservationToTrash(reservationDto: ReservationDto, dateDeleted: Long): Either<Failure, Unit> = Either.Success(Unit)
    override suspend fun deleteReservation(reservationDto: ReservationDto): Either<Failure, Unit> = Either.Success(Unit)
    override suspend fun getRegistrationsByCollection(collection: String): Either<Failure, List<RegistrationDto>> = Either.Success(emptyList())
    override suspend fun getReservationsByCollection(): Either<Failure, List<ReservationDto>> = Either.Success(emptyList())
}

/**
 * Fake del generador de Excel: en vez de escribir el archivo, captura las
 * celdas calculadas para poder verificar las agregaciones del reporte.
 */
class FakeExcelGenerator : ExcelGenerator {
    var capturedUpdates: List<ExcelCellUpdate> = emptyList()
        private set

    override fun generarDesdeTemplate(
        templateStream: PlatformInputStream,
        outputFile: PlatformFile,
        updates: List<ExcelCellUpdate>,
        sheetName: String
    ): Either<Failure, PlatformFile> {
        capturedUpdates = updates
        return Either.Success(outputFile)
    }
}
