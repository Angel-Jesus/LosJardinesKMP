package com.pe.losjardines.presentation.content.home.viewmodel

import com.pe.losjardines.base.ui.BaseViewModel
import com.pe.losjardines.presentation.content.home.contract.HomeEffect
import com.pe.losjardines.presentation.content.home.contract.HomeEvent
import com.pe.losjardines.presentation.content.home.contract.HomeUiState
import com.pe.losjardines.presentation.content.utils.ExcelTemplateProvider
import com.pe.losjardines.usecases.content.GenerateExcelReportUseCase
import com.pe.losjardines.usecases.content.GetReservationsUseCase
import com.pe.losjardines.usecases.content.GetRoomStateUseCase
import com.pe.losjardines.usecases.content.UpdateRegisterByCloudUseCase
import com.pe.losjardines.usecases.content.UpdateReservationByCloudUseCase
import com.pe.losjardines.usecases.model.FilterValues
import com.pe.losjardines.usecases.model.ReservationStatus
import com.pe.losjardines.utils.DateParams
import com.pe.losjardines.utils.constance.MonthFilter
import com.pe.losjardines.utils.formatedWithZero
import com.pe.losjardines.utils.getDayNow
import com.pe.losjardines.utils.getDayNowParams
import com.pe.losjardines.utils.getLastMonth
import com.pe.losjardines.utils.toDateStringResult

class HomeViewModel(
    private val excelTemplateProvider: ExcelTemplateProvider,
    private val generateExcelReportUseCase: GenerateExcelReportUseCase,
    private val updateRegisterByCloudUseCase: UpdateRegisterByCloudUseCase,
    private val updateReservationByCloudUseCase: UpdateReservationByCloudUseCase,
    private val getReservationsUseCase: GetReservationsUseCase,
    private val getRoomStateUseCase: GetRoomStateUseCase
): BaseViewModel<HomeUiState, HomeEvent, HomeEffect>(HomeUiState()) {
    override fun onEvent(event: HomeEvent) {
        when(event){
            is HomeEvent.GetSummary -> getHomeSummary()
            is HomeEvent.GenerateReport -> generateReport(event.filter)
            is HomeEvent.UpdateRegister -> updateRegisterByCloud()
            is HomeEvent.DismissReportResult -> updateState { copy(showReportResult = false) }
        }
    }

    private fun getHomeSummary() {
        updateState { copy(isLoading = true) }
        executeTwoParallel(
            first = { getReservationsUseCase.run(ReservationStatus.CHECK_IN, FIRST_PAGE) },
            second = { getRoomStateUseCase.run() },
            onSuccess = { reservations, rooms ->
                val today = getDayNow().toDateStringResult()
                val availableRooms = rooms.count { it.state }
                val reservationsToday = reservations.count { it.dateEnter == today }

                updateState {
                    copy(
                        isLoading = false,
                        reservation = reservations.size.toString(),
                        available = availableRooms.toString(),
                        reservationNow = reservationsToday.toString(),
                        reservations = reservations.take(RECENT_RESERVATIONS_LIMIT)
                    )
                }
            },
            onError = {
                updateState { copy(isLoading = false) }
                println("Error loading home summary: $it")
            }
        )
    }

    private fun generateReport(filter: FilterValues?) {
        updateState { copy(isLoading = true) }
        executeTask(
            task = {
                val reportFilter = filter ?: buildLastMonthFilter()
                val dateParams = getDayNowParams()
                val stream = excelTemplateProvider.openTemplate("reporte_mensual_v1.xlsx")
                val outputFile = excelTemplateProvider.outputFile("${generateNameFile(reportFilter, dateParams)}.xlsx")
                generateExcelReportUseCase.run(GenerateExcelReportUseCase.Params(stream, outputFile, reportFilter))
            },
            onSuccess = {
                updateState { copy(isLoading = false, showReportResult = true, reportSuccess = true) }
            },
            onError = {
                updateState { copy(isLoading = false, showReportResult = true, reportSuccess = false) }
                println("Error generating report: $it")
            }
        )
    }

    private fun updateRegisterByCloud(){
        updateState { copy(isLoading = true) }
        executeTwoParallel(
            first = { updateRegisterByCloudUseCase.run() },
            second = { updateReservationByCloudUseCase.run() },
            onSuccess = { _, _ ->
                updateState { copy(isLoading = false) }
            },
            onError = {
                updateState { copy(isLoading = false) }
                println("Error updating register by cloud: $it")
            }
        )
    }

    private fun buildLastMonthFilter(): FilterValues {
        val lastMonth = getLastMonth()
        val monthName = MonthFilter.fromNumber(lastMonth.month)?.displayName.orEmpty()
        return FilterValues(month = monthName, year = lastMonth.year.toString())
    }

    private fun generateNameFile(filter: FilterValues?, dateParams: DateParams): String{
        if(filter != null){
            val monthName = MonthFilter.fromDisplayName(filter.month)?.displayName.orEmpty().uppercase()
            return "$monthName ${filter.year}_${dateParams.year}${dateParams.month.formatedWithZero()}${dateParams.day.formatedWithZero()}${dateParams.hour.formatedWithZero()}${dateParams.minute.formatedWithZero()}${dateParams.second.formatedWithZero()}"
        } else {
            val lastMonth = getLastMonth()
            val monthName = MonthFilter.fromNumber(lastMonth.month)?.displayName.orEmpty().uppercase()
            return "$monthName ${lastMonth.year}_${dateParams.year}${dateParams.month.formatedWithZero()}${dateParams.day.formatedWithZero()}${dateParams.hour.formatedWithZero()}${dateParams.minute.formatedWithZero()}${dateParams.second.formatedWithZero()}"
        }
    }

    companion object {
        private const val FIRST_PAGE = 1
        private const val RECENT_RESERVATIONS_LIMIT = 5
    }
}