package com.pe.losjardines.presentation.content.registration.contract

import com.pe.losjardines.base.ui.BaseEffect

sealed interface RegistrationEffect: BaseEffect {
    data class SuccessSave(val message: String? = null): RegistrationEffect
    data class ErrorSave(val message: String): RegistrationEffect
}