package com.pe.losjardines.presentation.contract.registration

import com.pe.losjardines.core.base.ui.BaseUiState
import com.pe.losjardines.presentation.constance.ClientInfoTitle
import com.pe.losjardines.presentation.constance.RegisterKey
import com.pe.losjardines.presentation.constance.RegisterKey.*
import com.pe.losjardines.presentation.contract.registration.model.RegisterModel

data class RegistrationState(
    val registerMap: Map<RegisterKey, RegisterModel> = registerMapInit
): BaseUiState

val registerMapInit = mapOf(
    N_ROOM to RegisterModel(title = ClientInfoTitle.N_ROOM),
    NAME to RegisterModel(title = ClientInfoTitle.NAME),
    DNI to RegisterModel(title = ClientInfoTitle.DNI),
    COST to RegisterModel(title = ClientInfoTitle.COST),
    DATE to RegisterModel(title = ClientInfoTitle.DATE),
    TIME to RegisterModel(title = ClientInfoTitle.TIME),
    ORIGIN to RegisterModel(title = ClientInfoTitle.ORIGIN),
    OBSERVATIONS to RegisterModel(title = ClientInfoTitle.OBSERVATIONS),
)