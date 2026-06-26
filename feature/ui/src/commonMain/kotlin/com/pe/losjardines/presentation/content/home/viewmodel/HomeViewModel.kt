package com.pe.losjardines.presentation.content.home.viewmodel

import com.pe.losjardines.base.ui.BaseViewModel
import com.pe.losjardines.presentation.content.home.contract.HomeEffect
import com.pe.losjardines.presentation.content.home.contract.HomeEvent
import com.pe.losjardines.presentation.content.home.contract.HomeUiState
import com.pe.losjardines.presentation.content.utils.ExcelTemplateProvider
import com.pe.losjardines.usecases.content.GenerateExcelReportUseCase
import com.pe.losjardines.usecases.model.FilterValues
import com.pe.losjardines.utils.getDayNowParams

class HomeViewModel(
    private val excelTemplateProvider: ExcelTemplateProvider,
    private val generateExcelReportUseCase: GenerateExcelReportUseCase
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
                val stream = excelTemplateProvider.openTemplate("reporte_mensual.xlsx")
                val outputFile = excelTemplateProvider.outputFile(
                    "${dateParams.year}_${dateParams.month}_${dateParams.day}_reporte_mensual.xlsx"
                )
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

    }
}