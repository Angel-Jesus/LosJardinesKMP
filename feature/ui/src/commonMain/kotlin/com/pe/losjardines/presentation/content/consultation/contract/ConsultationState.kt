package com.pe.losjardines.presentation.content.consultation.contract

import com.pe.losjardines.base.ui.BaseUiState
import com.pe.losjardines.usecases.model.RegistrationDto
import com.pe.losjardines.utils.constance.MonthFilter

data class ConsultationState(
    val loading: Boolean = false,
    val showClearFilter: Boolean = false,
    val monthFilter: String = MonthFilter.NONE.displayName,
    val yearFilter: String = "",
    val searchDni: String = "",
    val clientsRegister: List<RegistrationDto> = emptyList()
): BaseUiState
