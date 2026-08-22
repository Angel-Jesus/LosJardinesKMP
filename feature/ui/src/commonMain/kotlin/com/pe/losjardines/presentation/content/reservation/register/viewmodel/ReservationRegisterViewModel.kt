package com.pe.losjardines.presentation.content.reservation.register.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.pe.losjardines.base.error.getMessage
import com.pe.losjardines.base.ui.BaseViewModel
import com.pe.losjardines.presentation.content.reservation.register.contract.ReservationRegisterEffect
import com.pe.losjardines.presentation.content.reservation.register.contract.ReservationRegisterEvent
import com.pe.losjardines.presentation.content.reservation.register.contract.ReservationRegisterState
import com.pe.losjardines.presentation.content.utils.FieldRegistration
import com.pe.losjardines.presentation.content.utils.mapToState
import com.pe.losjardines.usecases.catalog.GetCountriesUseCase
import com.pe.losjardines.usecases.catalog.GetRegionsUseCase
import com.pe.losjardines.usecases.catalog.GetTypeRoomUseCase
import com.pe.losjardines.usecases.content.SaveReservationUseCase
import com.pe.losjardines.usecases.content.ValidateRoomAvailabilityUseCase
import com.pe.losjardines.usecases.model.CountryDto
import com.pe.losjardines.usecases.model.RegionDto
import com.pe.losjardines.usecases.model.ReservationDto
import com.pe.losjardines.usecases.model.RoomAvailability
import com.pe.losjardines.usecases.model.TypeRoomDto
import com.pe.losjardines.utils.companions.EMPTY
import com.pe.losjardines.utils.getDateNow
import com.pe.losjardines.utils.getDayNow
import com.pe.losjardines.utils.toDateStringResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class ReservationRegisterViewModel(
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getRegionsUseCase: GetRegionsUseCase,
    private val getTypeRoomUseCase: GetTypeRoomUseCase,
    private val saveReservationUseCase: SaveReservationUseCase,
    private val validateRoomAvailabilityUseCase: ValidateRoomAvailabilityUseCase
): BaseViewModel<ReservationRegisterState, ReservationRegisterEvent, ReservationRegisterEffect>(ReservationRegisterState()) {

    private val _catalogState = MutableStateFlow(CatalogState())
    val countryCatalog = _catalogState.mapToState(viewModelScope, emptyList(), mapper = { it.countries })
    val regionCatalog = _catalogState.mapToState(viewModelScope, emptyList(), mapper = { it.regions })
    val typeRoomsCatalog = _catalogState.mapToState(viewModelScope, emptyList(), mapper = { it.typeRooms })

    val fullName = uiState.mapToState(viewModelScope, TextFieldValue(), mapper = { it.fullName })
    val sex = uiState.mapToState(viewModelScope, String.EMPTY, mapper = { it.sex })
    val country = uiState.mapToState(viewModelScope, String.EMPTY, mapper = { it.country })
    val typeRoom = uiState.mapToState(viewModelScope, String.EMPTY, mapper = { it.typeRoom })
    val region = uiState.mapToState(viewModelScope, String.EMPTY, mapper = { it.region })
    val documentType = uiState.mapToState(viewModelScope, String.EMPTY, mapper = { it.documentType })
    val documentNumber = uiState.mapToState(viewModelScope, TextFieldValue(), mapper = { it.documentNumber })
    val checkInDate = uiState.mapToState(viewModelScope, getDayNow().toDateStringResult(), mapper = { it.checkInDate })
    val checkOutDate = uiState.mapToState(viewModelScope, String.EMPTY, mapper = { it.checkOutDate })
    val room = uiState.mapToState(viewModelScope, TextFieldValue(), mapper = { it.room })
    val fee = uiState.mapToState(viewModelScope, TextFieldValue(), mapper = { it.rate })
    val observation = uiState.mapToState(viewModelScope, TextFieldValue(), mapper = { it.observation })

    override fun onEvent(event: ReservationRegisterEvent) {
        when(event){
            is ReservationRegisterEvent.GetCatalogInformation -> getCatalogInformation()
            is ReservationRegisterEvent.ValueChanged -> valueChanged(event.value, event.field)
            is ReservationRegisterEvent.SaveData -> saveData()
        }
    }

    private fun saveData() {
        val collection = getDateNow().year.toString()

        val reservation = ReservationDto(
            collection = collection,
            idFirebase = String.EMPTY,
            userName = fullName.value.text,
            dateEnter = checkInDate.value,
            dateExit = checkOutDate.value,
            fee = fee.value.text,
            sex = sex.value,
            typeDocument = documentType.value,
            numberDocument = documentNumber.value.text,
            country = country.value,
            region = region.value,
            typeRoom = typeRoom.value,
            room = room.value.text,
            observation = observation.value.text
        )

        executeTask(
            task = {
                // Se valida que la habitación no tenga otra reserva activa que choque con las
                // fechas solicitadas, para evitar reservar la misma habitación a dos clientes.
                val availability = validateRoomAvailabilityUseCase.run(
                    ValidateRoomAvailabilityUseCase.Params(
                        room = reservation.room,
                        dateEnter = reservation.dateEnter,
                        dateExit = reservation.dateExit
                    )
                )

                when (availability) {
                    is RoomAvailability.Conflict -> availability
                    is RoomAvailability.Available -> {
                        saveReservationUseCase.run(reservation)
                        availability
                    }
                }
            },
            onSuccess = { availability ->
                when (availability) {
                    is RoomAvailability.Conflict -> {
                        sendEffect(ReservationRegisterEffect.ErrorSave(roomConflictMessage(availability.reservation)))
                    }
                    is RoomAvailability.Available -> {
                        resetData()
                        sendEffect(ReservationRegisterEffect.SuccessSave("Reserva registrada correctamente"))
                    }
                }
            },
            onError = {
                sendEffect(ReservationRegisterEffect.ErrorSave(it.getMessage().orEmpty()))
            }
        )
    }

    private fun roomConflictMessage(reservation: ReservationDto): String =
        "La habitación ${reservation.room} ya tiene una reserva (${reservation.dateEnter} - ${reservation.dateExit}) que se cruza con las fechas seleccionadas."

    private fun valueChanged(value: Any, field: FieldRegistration){
        when(field){
            FieldRegistration.FULL_NAME -> updateState { copy(fullName = value as TextFieldValue) }
            FieldRegistration.SEX -> updateState { copy(sex = value as String) }
            FieldRegistration.COUNTRY_OF_RESIDENCE -> {
                updateState { copy(country = value as String) }

                val country = _catalogState.value.countries.find { it.name == value }

                country?.let {
                    getRegions(it.id)
                }
            }
            FieldRegistration.REGION_OF_RESIDENCE -> updateState { copy(region = value as String) }
            FieldRegistration.DOCUMENT_TYPE -> updateState { copy(documentType = value as String) }
            FieldRegistration.DOCUMENT_NUMBER -> updateState { copy(documentNumber = value as TextFieldValue) }
            FieldRegistration.CHECK_IN_DATE -> updateState { copy(checkInDate = value as String) }
            FieldRegistration.CHECK_OUT_DATE -> updateState { copy(checkOutDate = value as String) }
            FieldRegistration.TYPE_ROOM -> updateState { copy(typeRoom = value as String) }
            FieldRegistration.ROOM -> updateState { copy(room = value as TextFieldValue) }
            FieldRegistration.RATE -> updateState { copy(rate = value as TextFieldValue) }
            FieldRegistration.OBSERVATION -> updateState { copy(observation = value as TextFieldValue) }
            FieldRegistration.TRAVEL_REASON -> Unit
        }
    }

    private fun getCatalogInformation(){
        executeTask(
            task = { getCountriesUseCase.run() to getTypeRoomUseCase.run() },
            onSuccess = { (countries, typeRooms) ->
                _catalogState.update {
                    it.copy(
                        countries = countries,
                        typeRooms = typeRooms
                    )
                }
            },
            onError = {
                println("Error: $it")
            }
        )
    }

    private fun getRegions(countryId: String){
        executeTask(
            task = { getRegionsUseCase.run(countryId) },
            onSuccess = { regions ->
                _catalogState.update { it.copy(regions = regions) }
                if(regions.isEmpty()) updateState { copy(region = String.EMPTY) }
            }
        )
    }

    private fun resetData(){
        updateState { ReservationRegisterState() }
    }

    data class CatalogState(
        val countries: List<CountryDto> = emptyList(),
        val regions: List<RegionDto> = emptyList(),
        val typeRooms: List<TypeRoomDto> = emptyList()
    )
}
