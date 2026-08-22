package com.pe.losjardines.base.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pe.losjardines.base.error.Failure
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
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
    // Jobs
    private var executeJob: Job? = null
    private var executeParallelJob: Job? = null

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


    protected fun <Result> executeTask(
        task: suspend () -> Result,
        onSuccess: suspend (Result) -> Unit,
        onError: suspend (Failure) -> Unit = {}
    ){
        executeJob?.cancel()
        executeJob = viewModelScope.launch {
            try {
                val result = task.invoke()
                onSuccess(result)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Throwable){
                onError(Failure.fromThrowable(throwable = e))
                println("Error: ${e.message}")
            }
        }
    }

    protected fun <R1, R2> executeTwoParallel(
        first: suspend () -> R1,
        second: suspend () -> R2,
        onSuccess: suspend (R1, R2) -> Unit,
        onError: suspend (Failure) -> Unit = {}
    ) {
        executeParallelJob?.cancel()
        executeParallelJob = viewModelScope.launch {
            try {
                val r1 = async { first() }
                val r2 = async { second() }
                onSuccess(r1.await(), r2.await())
            } catch (e: CancellationException) {
                throw e
            } catch (e: Throwable) {
                onError(Failure.fromThrowable(e))
                println("Error: ${e.message}")
            }
        }
    }

    // ── 3 use cases en paralelo ────────────────────────────────────────────────
    protected fun <R1, R2, R3> executeThirdParallel(
        first: suspend () -> R1,
        second: suspend () -> R2,
        third: suspend () -> R3,
        onSuccess: suspend (R1, R2, R3) -> Unit,
        onError: suspend (Failure) -> Unit = {}
    ) {
        executeParallelJob?.cancel()
        executeParallelJob = viewModelScope.launch {
            try {
                val r1 = async { first() }
                val r2 = async { second() }
                val r3 = async { third() }
                onSuccess(r1.await(), r2.await(), r3.await())
            } catch (e: CancellationException) {
                throw e
            } catch (e: Throwable) {
                onError(Failure.fromThrowable(e))
                println("Error: ${e.message}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        _effect.close()
    }
}