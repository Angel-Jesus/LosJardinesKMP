package com.pe.losjardines.presentation.login.contract

import com.pe.losjardines.base.ui.BaseEvent
import com.pe.losjardines.presentation.login.viewmodel.FieldType

sealed interface LoginEvent: BaseEvent{
    data object CheckSession: LoginEvent
    data class UpdateValue(val value: String, val field: FieldType): LoginEvent
    data object EnterLogin: LoginEvent
}
