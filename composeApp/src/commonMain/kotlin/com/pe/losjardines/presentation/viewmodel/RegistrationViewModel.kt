package com.pe.losjardines.presentation.viewmodel

import com.pe.losjardines.core.base.error.Failure
import com.pe.losjardines.core.base.ui.BaseViewModel
import com.pe.losjardines.domain.model.ClientModel
import com.pe.losjardines.domain.usecase.SendClientUseCase
import com.pe.losjardines.presentation.constance.RegisterKey
import com.pe.losjardines.presentation.constance.RegisterKey.COST
import com.pe.losjardines.presentation.constance.RegisterKey.DATE
import com.pe.losjardines.presentation.constance.RegisterKey.DNI
import com.pe.losjardines.presentation.constance.RegisterKey.NAME
import com.pe.losjardines.presentation.constance.RegisterKey.N_ROOM
import com.pe.losjardines.presentation.constance.RegisterKey.OBSERVATIONS
import com.pe.losjardines.presentation.constance.RegisterKey.ORIGIN
import com.pe.losjardines.presentation.constance.RegisterKey.TIME
import com.pe.losjardines.presentation.contract.registration.RegistrationEffect
import com.pe.losjardines.presentation.contract.registration.RegistrationEvent
import com.pe.losjardines.presentation.contract.registration.RegistrationState
import com.pe.losjardines.utils.companions.EMPTY

class RegistrationViewModel(
    private val sendClientUseCase: SendClientUseCase
) : BaseViewModel<RegistrationState, RegistrationEvent, RegistrationEffect>(
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
        val form = uiState.value.registerMap
        val dataClient = ClientModel(
            room = form[N_ROOM]?.value ?: String.EMPTY,
            date = form[NAME]?.value ?: String.EMPTY,
            time = form[DNI]?.value ?: String.EMPTY,
            name = form[COST]?.value ?: String.EMPTY,
            dni = form[DATE]?.value ?: String.EMPTY,
            price = form[TIME]?.value ?: String.EMPTY,
            origin = form[ORIGIN]?.value ?: String.EMPTY,
            observation = form[OBSERVATIONS]?.value ?: String.EMPTY
        )
        executeUseCase(
            useCase = sendClientUseCase,
            params = SendClientUseCase.Params(dataClient),
            onSuccess = ::handleSuccessSendForm,
            onError = ::handleErrorSendForm
        )
    }

    private fun handleSuccessSendForm(status: Boolean){

    }

    private fun handleErrorSendForm(failure: Failure){

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