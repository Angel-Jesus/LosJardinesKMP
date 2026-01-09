package com.pe.losjardines.presentation.login.viewmodel

import androidx.lifecycle.viewModelScope
import com.pe.losjardines.base.error.Failure
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
            is LoginEvent.EnterLogin -> enterLogin(event.email, event.password)
            is LoginEvent.CheckSession -> checkSession()
        }
    }

    private fun checkSession(){
        viewModelScope.launch {
            if(checkSessionUseCase.invoke()){
                sendEffect(LoginEffect.LoginSuccess)
            }
        }
    }

    private fun enterLogin(email: String, password: String){
        updateState { copy( messageTest = "espere....") }
        executeUseCase(
            useCase = loginUseCase,
            params = LoginUseCase.Params(email, password),
            onSuccess = {
                syncronizationUseCase.invoke()
                sendEffect(LoginEffect.LoginSuccess)
            },
            onError = { failure ->
                when(failure){
                    is Failure.FirebaseAuthFailure -> {
                        updateState { copy( messageTest = failure.message) }
                    }
                    else -> {
                        updateState { copy( messageTest = "Error desconocido") }
                    }
                }
            }
        )
    }
}