package com.pe.losjardines.presentation.login.viewmodel

import androidx.lifecycle.viewModelScope
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.base.error.getMessage
import com.pe.losjardines.base.ui.BaseViewModel
import com.pe.losjardines.usecases.login.CheckSessionUseCase
import com.pe.losjardines.usecases.login.LoginUseCase
import com.pe.losjardines.presentation.login.contract.LoginEffect
import com.pe.losjardines.presentation.login.contract.LoginEvent
import com.pe.losjardines.presentation.login.contract.LoginState
import com.pe.losjardines.usecases.content.SyncronizationUseCase
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val checkSessionUseCase: CheckSessionUseCase,
    private val syncronizationUseCase: SyncronizationUseCase
): BaseViewModel<LoginState, LoginEvent, LoginEffect>(
    initialState = LoginState()
) {
    override fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EnterLogin -> enterLogin()
            is LoginEvent.CheckSession -> checkSession()
            is LoginEvent.UpdateValue -> updateValue(event.value, event.field)
        }
    }

    private fun updateValue(value: String, field: FieldType) {
        when(field){
            FieldType.EMAIL -> updateState { copy(email = value) }
            FieldType.PASSWORD -> updateState { copy(password = value) }
        }
    }

    private fun checkSession(){
        viewModelScope.launch {
            if(checkSessionUseCase.invoke()){
                sendEffect(LoginEffect.LoginSuccess)
            }
        }
    }

    private fun enterLogin(){
        updateState { copy( messageError = "", loading = true) }
        executeTask(
            task = { loginUseCase.run(uiState.value.email, uiState.value.password) },
            onSuccess = {
                syncronizationUseCase.invoke()
                updateState { copy(loading = false) }
                sendEffect(LoginEffect.LoginSuccess)
            },
            onError = { failure ->
                updateState { copy(loading = false, messageError = failure.getMessage().orEmpty().ifEmpty { "Error desconocido" }) }
            }
        )
    }

    fun hideErrorDialog(){
        updateState { copy( messageError = "") }
    }
}

enum class FieldType {
    EMAIL,
    PASSWORD
}