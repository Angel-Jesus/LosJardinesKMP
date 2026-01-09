package com.pe.losjardines.presentation.content.registration.contract

import com.pe.losjardines.base.ui.BaseEvent
import com.pe.losjardines.presentation.content.utils.FieldRegistration

sealed class RegistrationEvent: BaseEvent {
    data object GetCatalogInformation: RegistrationEvent()
    data class ValueChanged(val value: String, val field: FieldRegistration): RegistrationEvent()
}