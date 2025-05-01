package com.pe.losjardines.presentation.contract.registration

import com.pe.losjardines.core.base.ui.BaseEvent
import com.pe.losjardines.presentation.constance.RegisterKey

sealed class RegistrationEvent: BaseEvent {
    data object SendForm: RegistrationEvent()
    data class OnValueChanged(val key: RegisterKey, val value: String): RegistrationEvent()
    data object ResetForm: RegistrationEvent()
}