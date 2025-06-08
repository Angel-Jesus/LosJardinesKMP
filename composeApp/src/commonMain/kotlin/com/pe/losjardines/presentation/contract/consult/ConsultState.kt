package com.pe.losjardines.presentation.contract.consult

import com.pe.losjardines.core.base.ui.BaseUiState
import com.pe.losjardines.domain.model.ClientModel
import com.pe.losjardines.presentation.enums.StateConsult

data class ConsultState(
    val clients: List<ClientModel> = emptyList(),
    val state: StateConsult = StateConsult.LOADING
): BaseUiState