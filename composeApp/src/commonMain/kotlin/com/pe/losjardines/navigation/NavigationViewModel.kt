package com.pe.losjardines.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pe.losjardines.usecases.login.CheckSessionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NavigationViewModel(
    private val checkSessionUseCase: CheckSessionUseCase
): ViewModel() {
    val isLoggedIn: MutableStateFlow<stateScreen> = MutableStateFlow(stateScreen.INIT)

    fun checkSession() {
        viewModelScope.launch {
            val state = checkSessionUseCase.invoke()
            if(state){
                isLoggedIn.value = stateScreen.CONTENT
            }else{
                isLoggedIn.value = stateScreen.LOGIN
            }
        }
    }
}


enum class stateScreen{
    INIT,
    LOGIN,
    CONTENT
}