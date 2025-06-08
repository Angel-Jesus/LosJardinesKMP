package com.pe.losjardines.presentation.contract.consult

import com.pe.losjardines.core.base.ui.BaseEvent
import com.pe.losjardines.domain.model.ClientFilter
import com.pe.losjardines.domain.model.ClientModel
import com.pe.losjardines.presentation.contract.registration.RegistrationEvent
import com.pe.losjardines.presentation.model.DataUpdate

sealed class ConsultEvent: BaseEvent {
    data class GetClient(val filter: ClientFilter = ClientFilter.None): ConsultEvent()
    data class DeleteForm(val id: Int): ConsultEvent()
    data class UpdateForm(val dataUpdate: DataUpdate): ConsultEvent()
}