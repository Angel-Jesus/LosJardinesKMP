package com.pe.losjardines.presentation.content.registration.viewmodel

import androidx.lifecycle.viewModelScope
import com.pe.losjardines.base.ui.BaseViewModel
import com.pe.losjardines.presentation.content.registration.contract.RegistrationEffect
import com.pe.losjardines.presentation.content.registration.contract.RegistrationEvent
import com.pe.losjardines.presentation.content.registration.contract.RegistrationState
import com.pe.losjardines.presentation.content.utils.FieldRegistration
import com.pe.losjardines.presentation.content.utils.mapToState
import com.pe.losjardines.usecases.content.GetCountriesUseCase
import com.pe.losjardines.usecases.content.GetReasonTravelsUseCase
import com.pe.losjardines.usecases.content.GetRegionsUseCase
import com.pe.losjardines.usecases.model.CountryDto
import com.pe.losjardines.usecases.model.RegionDto
import com.pe.losjardines.usecases.model.TravelReasonDto
import com.pe.losjardines.utils.companions.EMPTY
import com.pe.losjardines.utils.getDayNow
import com.pe.losjardines.utils.toDateStringResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class RegistrationViewModel(
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getRegionsUseCase: GetRegionsUseCase,
    private val getReasonTravelsUseCase: GetReasonTravelsUseCase
): BaseViewModel<RegistrationState, RegistrationEvent, RegistrationEffect>(RegistrationState()) {

    private val _catalogState = MutableStateFlow(CatalogState())
    val countryCatalog = _catalogState.mapToState(viewModelScope, emptyList(), mapper = { it.countries })
    val regionCatalog = _catalogState.mapToState(viewModelScope, emptyList(), mapper = { it.regions })

    val reasonTravelCatalog = _catalogState.mapToState(viewModelScope, emptyList(), mapper = { it.travelReasons })

    val fullName = uiState.mapToState(viewModelScope, String.EMPTY, mapper = { it.fullName })
    val sex = uiState.mapToState(viewModelScope, String.EMPTY, mapper = { it.sex })
    val country = uiState.mapToState(viewModelScope, String.EMPTY, mapper = { it.country })
    val region = uiState.mapToState(viewModelScope, String.EMPTY, mapper = { it.region })
    val travelReason = uiState.mapToState(viewModelScope, String.EMPTY, mapper = { it.travelReason })
    val documentType = uiState.mapToState(viewModelScope, String.EMPTY, mapper = { it.documentType })
    val documentNumber = uiState.mapToState(viewModelScope, String.EMPTY, mapper = { it.documentNumber })
    val checkInDate = uiState.mapToState(viewModelScope, getDayNow().toDateStringResult(), mapper = { it.checkInDate })
    val checkOutDate = uiState.mapToState(viewModelScope, String.EMPTY, mapper = { it.checkOutDate })
    val room = uiState.mapToState(viewModelScope, String.EMPTY, mapper = { it.room })
    val rate = uiState.mapToState(viewModelScope, String.EMPTY, mapper = { it.rate })
    val observation = uiState.mapToState(viewModelScope, String.EMPTY, mapper = { it.observation })

    override fun onEvent(event: RegistrationEvent) {
        when(event){
            is RegistrationEvent.GetCatalogInformation -> getCatalogInformation()
            is RegistrationEvent.ValueChanged -> valueChanged(event.value, event.field)
        }
    }

    private fun valueChanged(value: String, field: FieldRegistration){
        when(field){
            FieldRegistration.FULL_NAME -> updateState { copy(fullName = value) }
            FieldRegistration.SEX -> updateState { copy(sex = value) }
            FieldRegistration.COUNTRY_OF_RESIDENCE -> {
                updateState { copy(country = value) }

                val country = _catalogState.value.countries.find { it.name == value }

                country?.let {
                    getRegions(it.id)
                }
            }
            FieldRegistration.REGION_OF_RESIDENCE -> updateState { copy(region = value) }
            FieldRegistration.DOCUMENT_TYPE -> updateState { copy(documentType = value) }
            FieldRegistration.DOCUMENT_NUMBER -> updateState { copy(documentNumber = value) }
            FieldRegistration.TRAVEL_REASON -> updateState { copy(travelReason = value) }
            FieldRegistration.CHECK_IN_DATE -> updateState { copy(checkInDate = value) }
            FieldRegistration.CHECK_OUT_DATE -> updateState { copy(checkOutDate = value) }
            FieldRegistration.ROOM -> updateState { copy(room = value) }
            FieldRegistration.RATE -> updateState { copy(rate = value) }
            FieldRegistration.OBSERVATION -> updateState { copy(observation = value) }
        }
    }

    private fun getCatalogInformation(){
        getCountries()
        getTravelReasons()
    }

    private fun getCountries(){
        executeUseCase(
            useCase = getCountriesUseCase,
            params = Unit,
            onSuccess = { countries ->
                _catalogState.update { it.copy(countries = countries) }
            }
        )
    }

    private fun getTravelReasons(){
        executeUseCase(
            useCase = getReasonTravelsUseCase,
            params = Unit,
            onSuccess = { travelReasons ->
                _catalogState.update { it.copy(travelReasons = travelReasons) }
            }
        )
    }

    private fun getRegions(countryId: String){
        executeUseCase(
            useCase = getRegionsUseCase,
            params = GetRegionsUseCase.Params(countryId),
            onSuccess = { regions ->
                _catalogState.update { it.copy(regions = regions) }
                if(regions.isEmpty()) updateState { copy(region = String.EMPTY) }
            }
        )
    }

    data class CatalogState(
        val countries: List<CountryDto> = emptyList(),
        val regions: List<RegionDto> = emptyList(),
        val travelReasons: List<TravelReasonDto> = emptyList()
    )
}