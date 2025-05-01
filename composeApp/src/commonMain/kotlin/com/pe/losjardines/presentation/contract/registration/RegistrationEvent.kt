package com.pe.losjardines.presentation.contract.registration

import com.pe.losjardines.core.base.ui.BaseEvent

sealed class RegistrationEvent: BaseEvent {
    data class OnValueChanged(val key: String, val value: String): RegistrationEvent()
    data object ResetForm: RegistrationEvent()
}