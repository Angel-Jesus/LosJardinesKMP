package com.pe.losjardines.presentation.content.registration.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.pe.losjardines.base.error.getMessage
import com.pe.losjardines.base.ui.BaseViewModel
import com.pe.losjardines.presentation.content.registration.contract.RegistrationEffect
import com.pe.losjardines.presentation.content.registration.contract.RegistrationEvent
import com.pe.losjardines.presentation.content.registration.contract.RegistrationState
import com.pe.losjardines.presentation.content.utils.FieldRegistration
import com.pe.losjardines.presentation.content.utils.mapToState
import com.pe.losjardines.usecases.catalog.GetCountriesUseCase
import com.pe.losjardines.usecases.catalog.GetReasonTravelsUseCase
import com.pe.losjardines.usecases.catalog.GetRegionsUseCase
import com.pe.losjardines.usecases.catalog.GetTypeRoomUseCase
import com.pe.losjardines.usecases.content.CheckInReservationUseCase
import com.pe.losjardines.usecases.content.GetReservationByIdUseCase
import com.pe.losjardines.usecases.content.SaveCustomerRegistrationUseCase
import com.pe.losjardines.usecases.model.CountryDto
import com.pe.losjardines.usecases.model.RegionDto
import com.pe.losjardines.usecases.model.RegistrationDto
import com.pe.losjardines.usecases.model.TravelReasonDto
import com.pe.losjardines.usecases.model.TypeRoomDto
import com.pe.losjardines.utils.companions.EMPTY
import com.pe.losjardines.utils.getDateNow
import com.pe.losjardines.utils.getDayNow
import com.pe.losjardines.utils.toDateStringResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class RegistrationViewModel(
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getRegionsUseCase: GetRegionsUseCase,
    private val getReasonTravelsUseCase: GetReasonTravelsUseCase,
    private val getTypeRoomUseCase: GetTypeRoomUseCase,
    private val saveCustomerRegistrationUseCase: SaveCustomerRegistrationUseCase,
    private val checkInReservationUseCase: CheckInReservationUseCase,
    private val getReservationByIdUseCase: GetReservationByIdUseCase
): BaseViewModel<RegistrationState, RegistrationEvent, RegistrationEffect>(RegistrationState()) {

    private val _catalogState = MutableStateFlow(CatalogState())
    val countryCatalog = _catalogState.mapToState(viewModelScope, emptyList(), mapper = { it.countries })
    val regionCatalog = _catalogState.mapToState(viewModelScope, emptyList(), mapper = { it.regions })
    val reasonTravelCatalog = _catalogState.mapToState(viewModelScope, emptyList(), mapper = { it.travelReasons })
    val typeRoomsCatalog = _catalogState.mapToState(viewModelScope, emptyList(), mapper = { it.typeRooms })

    val fullName = uiState.mapToState(viewModelScope, TextFieldValue(), mapper = { it.fullName })
    val sex = uiState.mapToState(viewModelScope, String.EMPTY, mapper = { it.sex })
    val country = uiState.mapToState(viewModelScope, String.EMPTY, mapper = { it.country })
    val typeRoom = uiState.mapToState(viewModelScope, String.EMPTY, mapper = { it.typeRoom })
    val region = uiState.mapToState(viewModelScope, String.EMPTY, mapper = { it.region })
    val travelReason = uiState.mapToState(viewModelScope, String.EMPTY, mapper = { it.travelReason })
    val documentType = uiState.mapToState(viewModelScope, String.EMPTY, mapper = { it.documentType })
    val documentNumber = uiState.mapToState(viewModelScope, TextFieldValue(), mapper = { it.documentNumber })
    val checkInDate = uiState.mapToState(viewModelScope, getDayNow().toDateStringResult(), mapper = { it.checkInDate })
    val checkOutDate = uiState.mapToState(viewModelScope, String.EMPTY, mapper = { it.checkOutDate })
    val room = uiState.mapToState(viewModelScope, TextFieldValue(), mapper = { it.room })
    val fee = uiState.mapToState(viewModelScope, TextFieldValue(), mapper = { it.rate })
    val observation = uiState.mapToState(viewModelScope, TextFieldValue(), mapper = { it.observation })

    override fun onEvent(event: RegistrationEvent) {
        when(event){
            is RegistrationEvent.GetCatalogInformation -> getCatalogInformation()
            is RegistrationEvent.ValueChanged -> valueChanged(event.value, event.field)
            is RegistrationEvent.SaveData -> saveData()
            is RegistrationEvent.LoadReservation -> loadReservation(event.id)
        }
    }

    private fun saveData() {
        val collection = getDateNow().year.toString()
        val reservation = uiState.value.reservation

        val customerInformation = RegistrationDto(
            collection = collection,
            country = country.value,
            dateEnter = checkInDate.value,
            dateExit = checkOutDate.value,
            fee = fee.value.text,
            sex = sex.value,
            name = fullName.value.text,
            typeDocument = documentType.value,
            numberDocument = documentNumber.value.text,
            observation = observation.value.text,
            reasonTravel = travelReason.value,
            region = region.value,
            typeRoom = typeRoom.value,
            room = room.value.text
        )
        executeTask(
            task = {
                // Modo check-in: primero se actualiza el estado de la reserva (Firebase + local),
                // luego se registra al cliente. Cada paso degrada a "pendiente" si no hay conexión.
                if (reservation != null) checkInReservationUseCase.run(reservation)
                saveCustomerRegistrationUseCase.run(customerInformation)
            },
            onSuccess = {
                resetData()
                if (reservation != null) {
                    sendEffect(RegistrationEffect.CheckInCompleted)
                } else {
                    sendEffect(RegistrationEffect.SuccessSave("Información del cliente guardado correctamente"))
                }
            },
            onError = {
                sendEffect(RegistrationEffect.ErrorSave(it.getMessage().orEmpty()))
            }
        )
    }

    private fun loadReservation(id: Long) {
        executeTask(
            task = { getReservationByIdUseCase.run(id) },
            onSuccess = { reservation ->
                updateState {
                    copy(
                        fullName = TextFieldValue(reservation.userName),
                        sex = reservation.sex,
                        country = reservation.country,
                        region = reservation.region,
                        documentType = reservation.typeDocument,
                        documentNumber = TextFieldValue(reservation.numberDocument),
                        checkInDate = reservation.dateEnter,
                        checkOutDate = reservation.dateExit,
                        typeRoom = reservation.typeRoom,
                        room = TextFieldValue(reservation.room),
                        rate = TextFieldValue(reservation.fee),
                        observation = TextFieldValue(reservation.observation),
                        reservation = reservation
                    )
                }
                loadRegionsForSelectedCountry(reservation.country)
            },
            onError = {
                sendEffect(RegistrationEffect.ErrorSave(it.getMessage().orEmpty()))
            }
        )
    }

    private fun loadRegionsForSelectedCountry(countryName: String) {
        if (countryName.isEmpty()) return
        val country = _catalogState.value.countries.find { it.name == countryName } ?: return
        getRegions(country.id)
    }

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
            FieldRegistration.TRAVEL_REASON -> updateState { copy(travelReason = value as String) }
            FieldRegistration.CHECK_IN_DATE -> updateState { copy(checkInDate = value as String) }
            FieldRegistration.CHECK_OUT_DATE -> updateState { copy(checkOutDate = value as String) }
            FieldRegistration.TYPE_ROOM -> updateState { copy(typeRoom = value as String) }
            FieldRegistration.ROOM -> updateState { copy(room = value as TextFieldValue) }
            FieldRegistration.RATE -> updateState { copy(rate = value as TextFieldValue) }
            FieldRegistration.OBSERVATION -> updateState { copy(observation = value as TextFieldValue) }
        }
    }

    private fun getCatalogInformation(){
        executeThirdParallel(
            first = { getCountriesUseCase.run() },
            second = { getReasonTravelsUseCase.run() },
            third = { getTypeRoomUseCase.run() },
            onSuccess = { countries, reasons, typeRooms ->
                _catalogState.update {
                    it.copy(
                        countries = countries,
                        travelReasons = reasons,
                        typeRooms = typeRooms
                    )
                }
                // Si el formulario ya fue precargado (modo check-in), aseguramos las regiones del país.
                loadRegionsForSelectedCountry(uiState.value.country)
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
        updateState { RegistrationState() }
    }

    data class CatalogState(
        val countries: List<CountryDto> = emptyList(),
        val regions: List<RegionDto> = emptyList(),
        val travelReasons: List<TravelReasonDto> = emptyList(),
        val typeRooms: List<TypeRoomDto> = emptyList()
    )
}