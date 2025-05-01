package com.pe.losjardines.core.base.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<S : BaseUiState, E: BaseEvent, F: BaseEffect>(
    initialState: S
): ViewModel(){

    // Ui State
    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<S> = _uiState.asStateFlow()

    // Ui Effect
    private val _effect = Channel<F>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    // Ui Event
    abstract fun onEvent(event: E)

    // Send Effect to Channel
    protected fun sendEffect(effect: F){
        viewModelScope.launch {
            _effect.send(effect)
        }
    }

    // Update Ui State
    protected fun updateState(reducer: S.() -> S){
        viewModelScope.launch {
            _uiState.update {
                it.reducer()
            }
        }
    }

    protected fun clear(){
        viewModelScope.cancel()
    }
}