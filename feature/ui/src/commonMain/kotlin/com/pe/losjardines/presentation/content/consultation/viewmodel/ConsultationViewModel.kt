package com.pe.losjardines.presentation.content.consultation.viewmodel

import com.pe.losjardines.base.ui.BaseViewModel
import com.pe.losjardines.presentation.content.consultation.contract.ConsultationEffect
import com.pe.losjardines.presentation.content.consultation.contract.ConsultationEvent
import com.pe.losjardines.presentation.content.consultation.contract.ConsultationState
import com.pe.losjardines.presentation.content.consultation.screen.UpdateFieldParams
import com.pe.losjardines.presentation.content.utils.FieldFilter
import com.pe.losjardines.presentation.content.utils.FieldRegistration.Companion.toFielTypeRegister
import com.pe.losjardines.usecases.content.GetClientsRegisterUseCase
import com.pe.losjardines.usecases.content.GetCountriesUseCase
import com.pe.losjardines.usecases.content.GetReasonTravelsUseCase
import com.pe.losjardines.usecases.content.GetRegionsUseCase
import com.pe.losjardines.usecases.content.UpdateClientInfoUseCase
import com.pe.losjardines.usecases.model.CountryDto
import com.pe.losjardines.usecases.model.FielTypeRegister.Companion.getRegisterUpdate
import com.pe.losjardines.usecases.model.FilterValues
import com.pe.losjardines.usecases.model.RegionDto
import com.pe.losjardines.usecases.model.TravelReasonDto
import com.pe.losjardines.usecases.model.UpdateParams
import com.pe.losjardines.utils.companions.EMPTY
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class ConsultationViewModel(
    private val getClientsRegisterUseCase: GetClientsRegisterUseCase,
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getRegionsUseCase: GetRegionsUseCase,
    private val getReasonTravelsUseCase: GetReasonTravelsUseCase,
    private val updateClientInfoUseCase: UpdateClientInfoUseCase
): BaseViewModel<ConsultationState, ConsultationEvent, ConsultationEffect>(
    ConsultationState()
) {
    val catalogState = MutableStateFlow(CatalogState())

    override fun onEvent(event: ConsultationEvent) {
        when(event){
            is ConsultationEvent.ClearFilter -> clearFilter()
            is ConsultationEvent.Filter -> getClientsRegister(FilterValues(uiState.value.monthFilter, uiState.value.yearFilter, uiState.value.searchDni))
            is ConsultationEvent.ValueChanged -> valueChanged(event.value, event.type)
            is ConsultationEvent.GetClientsRegister -> getClientsRegister(event.filter)
            is ConsultationEvent.UpdateClientInformation -> updateClientInformation(event.newValue, event.fieldParams)
        }
    }

    private fun updateClientInformation(newValue: String, fieldParams: UpdateFieldParams?) {
        val updateParams = UpdateParams(
           registrationDto = uiState.value.clientsRegister.firstOrNull{ it.id == fieldParams?.id },
           newValue = newValue,
           fieldTypeRegister = fieldParams?.field?.toFielTypeRegister()
        )

        executeUseCase(
            useCase = updateClientInfoUseCase,
            params = UpdateClientInfoUseCase.Params(updateParams),
            onSuccess = {
                val clientUpdate = uiState.value.clientsRegister.map {
                    if(it.id == fieldParams?.id){
                        updateParams.fieldTypeRegister?.getRegisterUpdate(newValue, updateParams.registrationDto!!)?: it
                    }else{
                        it
                    }
                }
                updateState { copy(clientsRegister = clientUpdate, loading = false) }
            },
            onError = {
                updateState { copy(loading = false) }
            }
        )
    }

    private fun getCatalogInformation(){
        getCountries()
        getTravelReasons()
        getRegions("PE")
    }

    private fun getCountries(){
        executeUseCase(
            useCase = getCountriesUseCase,
            params = Unit,
            onSuccess = { countries ->
                catalogState.update { it.copy(countries = countries) }
            }
        )
    }

    private fun getTravelReasons(){
        executeUseCase(
            useCase = getReasonTravelsUseCase,
            params = Unit,
            onSuccess = { travelReasons ->
                catalogState.update { it.copy(travelReasons = travelReasons) }
            }
        )
    }

    private fun getRegions(countryId: String){
        executeUseCase(
            useCase = getRegionsUseCase,
            params = GetRegionsUseCase.Params(countryId),
            onSuccess = { regions ->
                catalogState.update { it.copy(regions = regions) }
            }
        )
    }

    private fun clearFilter() {
        updateState {
            copy(
                monthFilter = String.EMPTY,
                yearFilter = String.EMPTY,
                searchDni = String.EMPTY,
                showClearFilter = false
            )
        }

        getClientsRegister()
    }

    private fun getClientsRegister(filter: FilterValues? = null) {
        getCatalogInformation()

        updateState { copy(loading = true)}
        executeUseCase(
            useCase = getClientsRegisterUseCase,
            params = GetClientsRegisterUseCase.Params(filter),
            onSuccess = {
                updateState { copy(clientsRegister = it, loading = false, showClearFilter = true) }
            },
            onError = {
                updateState { copy(loading = false) }
            }
        )
    }

    private fun valueChanged(value: String, type: FieldFilter) {
        when(type){
            FieldFilter.MONTH_FILTER -> updateState { copy(monthFilter = value) }
            FieldFilter.YEAR_FILTER -> updateState { copy(yearFilter = value) }
            FieldFilter.SEARCH_DNI_FILTER -> updateState { copy(searchDni = value) }
        }
    }

    data class CatalogState(
        val countries: List<CountryDto> = emptyList(),
        val regions: List<RegionDto> = emptyList(),
        val travelReasons: List<TravelReasonDto> = emptyList()
    )
}