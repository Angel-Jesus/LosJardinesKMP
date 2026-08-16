package com.pe.losjardines.presentation.content.registration.contract

import com.pe.losjardines.base.ui.BaseEffect

sealed interface RegistrationEffect: BaseEffect {
    data class SuccessSave(val message: String? = null): RegistrationEffect
    data class ErrorSave(val message: String): RegistrationEffect
    /** El check-in y el registro finalizaron correctamente; la pantalla debe navegar a home. */
    data object CheckInCompleted: RegistrationEffect
}