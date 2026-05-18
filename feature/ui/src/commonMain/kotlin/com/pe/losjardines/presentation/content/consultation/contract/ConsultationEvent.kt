package com.pe.losjardines.presentation.content.consultation.contract

import com.pe.losjardines.base.ui.BaseEvent
import com.pe.losjardines.presentation.content.consultation.screen.UpdateFieldParams
import com.pe.losjardines.presentation.content.utils.FieldFilter
import com.pe.losjardines.usecases.model.FilterValues

sealed class ConsultationEvent: BaseEvent {
    data class GetClientsRegister(val filter: FilterValues? = null): ConsultationEvent()
    data class ValueChanged(val value: String, val type: FieldFilter): ConsultationEvent()
    data object Filter: ConsultationEvent()
    data object ClearFilter: ConsultationEvent()
    data class UpdateClientInformation(val newValue: String, val fieldParams: UpdateFieldParams?): ConsultationEvent()
}