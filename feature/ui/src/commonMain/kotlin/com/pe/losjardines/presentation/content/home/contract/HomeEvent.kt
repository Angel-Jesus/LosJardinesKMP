package com.pe.losjardines.presentation.content.home.contract

import com.pe.losjardines.base.ui.BaseEvent
import com.pe.losjardines.usecases.model.FilterValues

sealed interface HomeEvent: BaseEvent {
    data object GetSummary: HomeEvent
    data class GenerateReport(val filter: FilterValues? = null): HomeEvent
    data object UpdateRegister: HomeEvent
}