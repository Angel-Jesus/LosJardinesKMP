package com.pe.losjardines.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.base.ui.BaseViewModel
import com.pe.losjardines.usecases.login.CheckSessionUseCase
import com.pe.losjardines.usecases.login.LoginUseCase
import com.pe.losjardines.presentation.contract.LoginEffect
import com.pe.losjardines.presentation.contract.LoginEvent
import com.pe.losjardines.presentation.contract.LoginState
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val checkSessionUseCase: CheckSessionUseCase
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
                updateState {
                    copy(messageTest = "Logueado papaiii")
                }
            }
        }
    }

    private fun enterLogin(email: String, password: String){
        updateState { copy( messageTest = "espere....") }
        executeUseCase(
            useCase = loginUseCase,
            params = LoginUseCase.Params(email, password),
            onSuccess = {
                updateState { copy( messageTest = "Logueado de forma exitosa") }
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