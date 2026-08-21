package com.pe.losjardines.presentation.content.reservation.viewmodel

import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.base.error.getMessage
import com.pe.losjardines.base.ui.BaseViewModel
import com.pe.losjardines.presentation.content.reservation.contract.ReservationEffect
import com.pe.losjardines.presentation.content.reservation.contract.ReservationEvent
import com.pe.losjardines.presentation.content.reservation.contract.ReservationState
import com.pe.losjardines.presentation.components.table.UpdateFieldParams
import com.pe.losjardines.usecases.catalog.GetCountriesUseCase
import com.pe.losjardines.usecases.catalog.GetRegionsUseCase
import com.pe.losjardines.usecases.catalog.GetTypeRoomUseCase
import com.pe.losjardines.usecases.content.DeleteReservationUseCase
import com.pe.losjardines.usecases.content.GetReservationsUseCase
import com.pe.losjardines.usecases.model.CountryDto
import com.pe.losjardines.usecases.model.RegionDto
import com.pe.losjardines.usecases.model.ReservationStatus
import com.pe.losjardines.usecases.model.TypeRoomDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class ReservationViewModel(
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getRegionsUseCase: GetRegionsUseCase,
    private val getTypeRoomUseCase: GetTypeRoomUseCase,
    private val getReservationsUseCase: GetReservationsUseCase,
    private val deleteReservationUseCase: DeleteReservationUseCase
): BaseViewModel<ReservationState, ReservationEvent, ReservationEffect>(ReservationState()) {
    val catalogState = MutableStateFlow(CatalogState())

    override fun onEvent(event: ReservationEvent) {
        when(event){
            is ReservationEvent.GetReservations -> getReservations()
            is ReservationEvent.UpdateReservationInformation -> updateReservationInformation(event.newValue, event.fieldParams)
            is ReservationEvent.DeleteReservationInformation -> deleteReservationInformation(event.id, event.idFirebase)
            is ReservationEvent.FilterReservations -> filterReservations(event.reservationStatus)
        }
    }

    private fun filterReservations(reservationStatus: ReservationStatus?) {
        if (reservationStatus == null) {
            updateState { copy(reservationStatus = ReservationStatus.CHECK_IN) }
            getReservations(ReservationStatus.CHECK_IN)
            return
        }

        if(reservationStatus == uiState.value.reservationStatus) return

        updateState { copy(reservationStatus = reservationStatus) }
        getReservations(reservationStatus)
    }

    private fun getReservations(
        status: ReservationStatus = ReservationStatus.CHECK_IN,
        page: Int = 1
    ) {
        getCatalogInformation()

        updateState { copy(loading = true) }
        executeTask(
            task = { getReservationsUseCase.run(status, page) },
            onSuccess = {
                updateState { copy(reservations = it, loading = false, showClearFilter = true) }
            },
            onError = ::handleError
        )
    }

    private fun updateReservationInformation(newValue: String, fieldParams: UpdateFieldParams?) {
        updateState { copy(loading = true) }
        // TODO: mapear `newValue` al campo `fieldParams?.field` de la reserva y persistir con el use case.
        updateState { copy(loading = false) }
    }

    private fun deleteReservationInformation(id: Long, idFirebase: String) {
        updateState { copy(loading = true) }
        executeTask(
            task = { deleteReservationUseCase.run(id, idFirebase) },
            onSuccess = {
                getReservations()
                updateState { copy(loading = false) }
            },
            onError = ::handleError
        )
    }

    private fun getCatalogInformation(){
        executeThirdParallel(
            first = { getCountriesUseCase.run() },
            second = { getRegionsUseCase.run("PE") },
            third = { getTypeRoomUseCase.run() },
            onSuccess = { countries, regions, typeRooms ->
                catalogState.update {
                    it.copy(
                        countries = countries,
                        regions = regions,
                        typeRooms = typeRooms
                    )
                }
            }
        )
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
        val typeRooms: List<TypeRoomDto> = emptyList()
    )
}
