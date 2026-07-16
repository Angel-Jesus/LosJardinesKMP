package com.pe.losjardines.presentation.content.home.viewmodel

import com.pe.losjardines.base.ui.BaseViewModel
import com.pe.losjardines.presentation.content.home.contract.HomeEffect
import com.pe.losjardines.presentation.content.home.contract.HomeEvent
import com.pe.losjardines.presentation.content.home.contract.HomeUiState
import com.pe.losjardines.presentation.content.utils.ExcelTemplateProvider
import com.pe.losjardines.usecases.content.GenerateExcelReportUseCase
import com.pe.losjardines.usecases.content.UpdateRegisterByCloudUseCase
import com.pe.losjardines.usecases.model.FilterValues
import com.pe.losjardines.utils.DateParams
import com.pe.losjardines.utils.constance.MonthFilter
import com.pe.losjardines.utils.formatedWithZero
import com.pe.losjardines.utils.getDateNow
import com.pe.losjardines.utils.getDayNowParams
import com.pe.losjardines.utils.getLastMonth

class HomeViewModel(
    private val excelTemplateProvider: ExcelTemplateProvider,
    private val generateExcelReportUseCase: GenerateExcelReportUseCase,
    private val updateRegisterByCloudUseCase: UpdateRegisterByCloudUseCase
): BaseViewModel<HomeUiState, HomeEvent, HomeEffect>(HomeUiState()) {
    override fun onEvent(event: HomeEvent) {
        when(event){
            is HomeEvent.GenerateReport -> generateReport(event.filter)
            is HomeEvent.UpdateRegister -> updateRegisterByCloud()
        }
    }

    private fun generateReport(filter: FilterValues?) {
        updateState { copy(isLoading = true) }
        executeTask(
            task = {
                val dateParams = getDayNowParams()
                val stream = excelTemplateProvider.openTemplate("reporte_mensual_v1.xlsx")
                val outputFile = excelTemplateProvider.outputFile("${generateNameFile(filter, dateParams)}.xlsx")
                generateExcelReportUseCase.run(GenerateExcelReportUseCase.Params(stream, outputFile, filter))
            },
            onSuccess = {
                updateState { copy(isLoading = false) }
            },
            onError = {
                updateState { copy(isLoading = false) }
                println("Error generating report: $it")
            }
        )
    }

    private fun updateRegisterByCloud(){
        updateState { copy(isLoading = true) }
        executeTask(
            task = {
                updateRegisterByCloudUseCase.run()
            },
            onSuccess = {
                updateState { copy(isLoading = false) }
            },
            onError = {
                updateState { copy(isLoading = false) }
                println("Error updating register by cloud: $it")
            }
        )
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
}