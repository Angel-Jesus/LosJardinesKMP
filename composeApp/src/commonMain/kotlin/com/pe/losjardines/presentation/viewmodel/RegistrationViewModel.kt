package com.pe.losjardines.presentation.viewmodel

import com.pe.losjardines.core.base.ui.BaseViewModel
import com.pe.losjardines.presentation.constance.RegisterKey
import com.pe.losjardines.presentation.contract.registration.RegistrationEffect
import com.pe.losjardines.presentation.contract.registration.RegistrationEvent
import com.pe.losjardines.presentation.contract.registration.RegistrationState

class RegistrationViewModel : BaseViewModel<RegistrationState, RegistrationEvent, RegistrationEffect>(
        initialState = RegistrationState()
) {
    override fun onEvent(event: RegistrationEvent) {
        when(event){
            is RegistrationEvent.OnValueChanged -> onValueChanged(event.key, event.value)
            is RegistrationEvent.ResetForm -> resetForm()
            is RegistrationEvent.SendForm -> sendForm()
        }
    }

    private fun sendForm(){

    }

    private fun onValueChanged(key: RegisterKey, value: String){
        updateState { copy(
            registerMap = uiState.value.registerMap.toMutableMap().apply {
                val field = this[key]!!
                this[key] = field.copy(value = value)
            }
        ) }
    }

    private fun resetForm(){
        updateState { copy(registerMap = RegistrationState().registerMap) }
    }
}