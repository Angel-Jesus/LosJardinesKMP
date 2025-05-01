package com.pe.losjardines.presentation.viewmodel

import com.pe.losjardines.core.base.ui.BaseViewModel
import com.pe.losjardines.presentation.contract.registration.RegistrationEffect
import com.pe.losjardines.presentation.contract.registration.RegistrationEvent
import com.pe.losjardines.presentation.contract.registration.RegistrationState

class RegistrationViewModel : BaseViewModel<RegistrationState, RegistrationEvent, RegistrationEffect>(
        initialState = RegistrationState()
) {
    override fun onEvent(event: RegistrationEvent) {
        when(event){
            is RegistrationEvent.OnValueChanged -> TODO()
            is RegistrationEvent.ResetForm -> resetForm()
        }
    }

    private fun resetForm(){
        updateState { copy(registerMap = RegistrationState().registerMap) }
    }
}