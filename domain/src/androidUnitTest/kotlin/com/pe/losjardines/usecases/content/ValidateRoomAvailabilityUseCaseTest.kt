package com.pe.losjardines.usecases.content

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.repository.DatabaseRepository
import com.pe.losjardines.usecases.model.CountryDto
import com.pe.losjardines.usecases.model.RegionDto
import com.pe.losjardines.usecases.model.RegistrationDto
import com.pe.losjardines.usecases.model.ReservationDto
import com.pe.losjardines.usecases.model.ReservationStatus
import com.pe.losjardines.usecases.model.RoomAvailability
import com.pe.losjardines.usecases.model.TravelReasonDto
import com.pe.losjardines.usecases.model.TypeRoomDto
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Valida la lógica anti-duplicidad al registrar un cliente: que no se pueda ocupar una habitación
 * que ya tiene una reserva activa cuyo rango de fechas choca con el solicitado.
 */
class ValidateRoomAvailabilityUseCaseTest {

    // Reserva activa existente en la habitación 101, del 10 al 15 de julio de 2026 (Check-in).
    private val reservation101 = reservation(
        id = 1L,
        idFirebase = "fb-101",
        room = "101",
        dateEnter = "10/07/2026",
        dateExit = "15/07/2026",
        attentionState = ReservationStatus.CHECK_IN.value
    )

    // Reserva ya ocupada en la habitación 205, del 12 al 14 de julio de 2026 (Occupied).
    private val reservation205 = reservation(
        id = 2L,
        idFirebase = "fb-205",
        room = "205",
        dateEnter = "12/07/2026",
        dateExit = "14/07/2026",
        attentionState = ReservationStatus.OCCUPIED.value
    )

    private fun useCaseWith(vararg reservations: ReservationDto): ValidateRoomAvailabilityUseCase {
        val byState = reservations.groupBy { it.attentionState }
        return ValidateRoomAvailabilityUseCase(FakeReservationDatabaseRepository(byState))
    }

    @Test
    fun `bloquea cuando el rango choca con una reserva activa en la misma habitacion`() = runBlocking {
        val useCase = useCaseWith(reservation101, reservation205)

        val result = useCase.run(
            ValidateRoomAvailabilityUseCase.Params(
                room = "101",
                dateEnter = "12/07/2026",
                dateExit = "13/07/2026"
            )
        )

        assertTrue("Se esperaba conflicto por choque en la habitación 101", result is RoomAvailability.Conflict)
        assertEquals("fb-101", (result as RoomAvailability.Conflict).reservation.idFirebase)
    }

    @Test
    fun `disponible cuando la habitacion es distinta`() = runBlocking {
        val useCase = useCaseWith(reservation101, reservation205)

        val result = useCase.run(
            ValidateRoomAvailabilityUseCase.Params(
                room = "102",
                dateEnter = "12/07/2026",
                dateExit = "13/07/2026"
            )
        )

        assertEquals(RoomAvailability.Available, result)
    }

    @Test
    fun `disponible cuando la nueva entrada es el dia de salida de otra reserva (checkout libera)`() = runBlocking {
        val useCase = useCaseWith(reservation101)

        val result = useCase.run(
            ValidateRoomAvailabilityUseCase.Params(
                room = "101",
                dateEnter = "15/07/2026", // mismo día en que la 101 hace checkout
                dateExit = "18/07/2026"
            )
        )

        assertEquals(RoomAvailability.Available, result)
    }

    @Test
    fun `disponible cuando la nueva salida es el dia de entrada de otra reserva`() = runBlocking {
        val useCase = useCaseWith(reservation101)

        val result = useCase.run(
            ValidateRoomAvailabilityUseCase.Params(
                room = "101",
                dateEnter = "05/07/2026",
                dateExit = "10/07/2026" // termina el día en que la 101 hace check-in
            )
        )

        assertEquals(RoomAvailability.Available, result)
    }

    @Test
    fun `excluye la reserva en curso por id (caso check-in) y no la reporta como choque`() = runBlocking {
        val useCase = useCaseWith(reservation101)

        val result = useCase.run(
            ValidateRoomAvailabilityUseCase.Params(
                room = "101",
                dateEnter = "10/07/2026",
                dateExit = "15/07/2026", // mismas fechas de la reserva que se está atendiendo
                excludeReservationId = 1L
            )
        )

        assertEquals(RoomAvailability.Available, result)
    }

    @Test
    fun `excluye la reserva en curso por idFirebase`() = runBlocking {
        val useCase = useCaseWith(reservation101)

        val result = useCase.run(
            ValidateRoomAvailabilityUseCase.Params(
                room = "101",
                dateEnter = "10/07/2026",
                dateExit = "15/07/2026",
                excludeIdFirebase = "fb-101"
            )
        )

        assertEquals(RoomAvailability.Available, result)
    }

    @Test
    fun `una reserva cancelada no bloquea la habitacion`() = runBlocking {
        val canceled = reservation(
            id = 9L,
            idFirebase = "fb-cancel",
            room = "101",
            dateEnter = "10/07/2026",
            dateExit = "15/07/2026",
            attentionState = ReservationStatus.CANCELED.value
        )
        val useCase = useCaseWith(canceled)

        val result = useCase.run(
            ValidateRoomAvailabilityUseCase.Params(
                room = "101",
                dateEnter = "11/07/2026",
                dateExit = "13/07/2026"
            )
        )

        assertEquals(RoomAvailability.Available, result)
    }

    @Test
    fun `detecta choque contra una reserva en estado ocupado`() = runBlocking {
        val useCase = useCaseWith(reservation101, reservation205)

        val result = useCase.run(
            ValidateRoomAvailabilityUseCase.Params(
                room = "205",
                dateEnter = "13/07/2026",
                dateExit = "16/07/2026"
            )
        )

        assertTrue("Se esperaba conflicto por choque en la habitación 205", result is RoomAvailability.Conflict)
        assertEquals("fb-205", (result as RoomAvailability.Conflict).reservation.idFirebase)
    }

    @Test
    fun `disponible cuando no hay reservas`() = runBlocking {
        val useCase = useCaseWith()

        val result = useCase.run(
            ValidateRoomAvailabilityUseCase.Params(
                room = "101",
                dateEnter = "10/07/2026",
                dateExit = "12/07/2026"
            )
        )

        assertEquals(RoomAvailability.Available, result)
    }

    @Test
    fun `disponible cuando la habitacion viene vacia (no se puede validar)`() = runBlocking {
        val useCase = useCaseWith(reservation101)

        val result = useCase.run(
            ValidateRoomAvailabilityUseCase.Params(
                room = "   ",
                dateEnter = "10/07/2026",
                dateExit = "15/07/2026"
            )
        )

        assertEquals(RoomAvailability.Available, result)
    }

    private fun reservation(
        id: Long,
        idFirebase: String,
        room: String,
        dateEnter: String,
        dateExit: String,
        attentionState: String
    ) = ReservationDto(
        collection = "2026",
        id = id,
        idFirebase = idFirebase,
        userName = "Cliente $room",
        dateEnter = dateEnter,
        dateExit = dateExit,
        fee = "100",
        sex = "M",
        typeDocument = "DNI",
        numberDocument = "1000000$id",
        country = "Perú",
        region = "Lima",
        typeRoom = "Simple",
        room = room,
        observation = "",
        attentionState = attentionState
    )
}

/**
 * Fake del repositorio local para reservas: responde `getReservations` filtrando por estado de
 * atención y respetando la paginación (limit/offset), como lo haría la consulta real.
 */
private class FakeReservationDatabaseRepository(
    private val reservationsByState: Map<String, List<ReservationDto>>
) : DatabaseRepository {

    override suspend fun getReservations(
        state: String,
        limit: Long,
        offset: Long
    ): Either<Failure, List<ReservationDto>> {
        val page = reservationsByState[state].orEmpty()
            .drop(offset.toInt())
            .take(limit.toInt())
        return Either.Success(page)
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
    override suspend fun getRegistrationById(id: Long): Either<Failure, RegistrationDto> = Either.Error(Failure.UnknownFailure("fake"))
    override suspend fun moveToTrash(registrationDto: RegistrationDto, dateDeleted: Long, state: String): Either<Failure, Unit> = Either.Success(Unit)
    override suspend fun getClientsRegister(dateInit: Long, dateLast: Long, searchDni: String?): Either<Failure, List<RegistrationDto>> = Either.Success(emptyList())
    override suspend fun updateClientInformation(state: String, registrationDto: RegistrationDto): Either<Failure, Unit> = Either.Success(Unit)
    override suspend fun deleteAllRegisters(): Either<Failure, Unit> = Either.Success(Unit)
    override suspend fun insertRegistrations(registrations: List<RegistrationDto>, state: String): Either<Failure, Unit> = Either.Success(Unit)
    override suspend fun saveReservation(reservationDto: ReservationDto, state: String): Either<Failure, Unit> = Either.Success(Unit)
    override suspend fun getReservationById(id: Long): Either<Failure, ReservationDto> = Either.Error(Failure.UnknownFailure("fake"))
    override suspend fun moveReservationToTrash(reservationDto: ReservationDto, dateDeleted: Long, state: String): Either<Failure, Unit> = Either.Success(Unit)
    override suspend fun updateReservationAttentionState(id: Long, attentionState: String, state: String): Either<Failure, Unit> = Either.Success(Unit)
    override suspend fun deleteAllReservations(): Either<Failure, Unit> = Either.Success(Unit)
    override suspend fun insertReservations(reservations: List<ReservationDto>, state: String): Either<Failure, Unit> = Either.Success(Unit)
}
