package com.pe.losjardines.presentation.content.consultation.viewmodel

import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.base.error.getMessage
import com.pe.losjardines.base.ui.BaseViewModel
import com.pe.losjardines.presentation.content.consultation.contract.ConsultationEffect
import com.pe.losjardines.presentation.content.consultation.contract.ConsultationEvent
import com.pe.losjardines.presentation.content.consultation.contract.ConsultationState
import com.pe.losjardines.presentation.components.table.UpdateFieldParams
import com.pe.losjardines.presentation.content.utils.FieldFilter
import com.pe.losjardines.presentation.content.utils.FieldRegistration.Companion.toFielTypeRegister
import com.pe.losjardines.usecases.content.GetClientsRegisterUseCase
import com.pe.losjardines.usecases.catalog.GetCountriesUseCase
import com.pe.losjardines.usecases.catalog.GetReasonTravelsUseCase
import com.pe.losjardines.usecases.catalog.GetRegionsUseCase
import com.pe.losjardines.usecases.content.DeleteClientUseCase
import com.pe.losjardines.usecases.content.UpdateClientInfoUseCase
import com.pe.losjardines.usecases.model.CountryDto
import com.pe.losjardines.usecases.model.FielTypeRegister.Companion.getRegisterUpdate
import com.pe.losjardines.usecases.model.FilterValues
import com.pe.losjardines.usecases.model.RegionDto
import com.pe.losjardines.usecases.model.TravelReasonDto
import com.pe.losjardines.usecases.model.UpdateParams
import com.pe.losjardines.utils.companions.EMPTY
import com.pe.losjardines.utils.constance.MonthFilter
import com.pe.losjardines.utils.getCurrentYear
import com.pe.losjardines.utils.getDayNowParams
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class ConsultationViewModel(
    private val getClientsRegisterUseCase: GetClientsRegisterUseCase,
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getRegionsUseCase: GetRegionsUseCase,
    private val getReasonTravelsUseCase: GetReasonTravelsUseCase,
    private val updateClientInfoUseCase: UpdateClientInfoUseCase,
    private val deleteClientUseCase: DeleteClientUseCase
): BaseViewModel<ConsultationState, ConsultationEvent, ConsultationEffect>(
    ConsultationState(yearFilter = getDayNowParams().year.toString())
) {
    val catalogState = MutableStateFlow(CatalogState())

    override fun onEvent(event: ConsultationEvent) {
        when(event){
            is ConsultationEvent.ClearFilter -> clearFilter()
            is ConsultationEvent.Filter -> getClientsRegister(FilterValues(uiState.value.monthFilter, uiState.value.yearFilter, uiState.value.searchDni))
            is ConsultationEvent.ValueChanged -> valueChanged(event.value, event.type)
            is ConsultationEvent.GetClientsRegister -> getClientsRegister(event.filter)
            is ConsultationEvent.UpdateClientInformation -> updateClientInformation(event.newValue, event.fieldParams)
            is ConsultationEvent.DeleteClientInformation -> deleteClientInformation(event.id, event.idFirebase)
        }
    }

    private fun deleteClientInformation(id: Long, idFirebase: String){
        updateState { copy(loading = true) }
        executeTask(
            task = { deleteClientUseCase.run(id, idFirebase) },
            onSuccess = {
                getClientsRegister()
                updateState { copy(loading = false) }
            },
            onError = ::handleError
        )
    }

    private fun updateClientInformation(newValue: String, fieldParams: UpdateFieldParams?) {
        if(fieldParams == null) return

        updateState { copy(loading = true) }

        val updateParams = UpdateParams(
           registrationDto = uiState.value.clientsRegister.firstOrNull{ it.id == fieldParams.id },
           newValue = newValue,
           fieldTypeRegister = fieldParams.field?.toFielTypeRegister()
        )

        executeTask(
            task = { updateClientInfoUseCase.run(updateParams) },
            onSuccess = {
                val clientUpdate = uiState.value.clientsRegister.map {
                    if(it.id == fieldParams.id){
                        updateParams.registrationDto?.let { registrationDto ->
                            updateParams.fieldTypeRegister?.getRegisterUpdate(newValue, registrationDto)
                        } ?: it
                    }else{
                        it
                    }
                }
                updateState { copy(clientsRegister = clientUpdate, loading = false) }
            },
            onError = ::handleError
        )
    }

    private fun getCatalogInformation(){
        executeThirdParallel(
            first = { getCountriesUseCase.run() },
            second = { getReasonTravelsUseCase.run() },
            third = { getRegionsUseCase.run("PE") },
            onSuccess = { countries, travelReasons, regions ->
                catalogState.update {
                    it.copy(
                        countries = countries,
                        travelReasons = travelReasons,
                        regions = regions
                    )
                }
            }
        )
    }

    private fun clearFilter() {
        updateState {
            copy(
                monthFilter = MonthFilter.NONE.displayName,
                yearFilter = getCurrentYear(),
                searchDni = String.EMPTY,
                showClearFilter = false
            )
        }

        getClientsRegister()
    }

    private fun getClientsRegister(filter: FilterValues? = null) {
        updateState { copy(loading = true, clientsRegister = emptyList())}
        getCatalogInformation()

        executeTask(
            task = { getClientsRegisterUseCase.run(filter) },
            onSuccess = {
                updateState { copy(clientsRegister = it, loading = false, showClearFilter = filter != null) }
            },
            onError = ::handleError
        )
    }

    private fun valueChanged(value: String, type: FieldFilter) {
        when(type){
            FieldFilter.MONTH_FILTER -> updateState { copy(monthFilter = value) }
            FieldFilter.YEAR_FILTER -> updateState { copy(yearFilter = value) }
            FieldFilter.SEARCH_DNI_FILTER -> updateState { copy(searchDni = value) }
        }
    }

    private fun handleError(failure: Failure){
        updateState { copy(loading = false, errorMessage = failure.getMessage()) }
    }

    fun hideErrorMessage(){
        updateState { copy(errorMessage = null) }
    }

    data class CatalogState(
        val countries: List<CountryDto> = emptyList(),
        val regions: List<RegionDto> = emptyList(),
        val travelReasons: List<TravelReasonDto> = emptyList()
    )
}