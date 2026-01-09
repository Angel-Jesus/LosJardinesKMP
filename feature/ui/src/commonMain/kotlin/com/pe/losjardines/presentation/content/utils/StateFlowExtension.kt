package com.pe.losjardines.presentation.content.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

fun <T, R> StateFlow<T>.mapToState(
    scope: CoroutineScope,
    initial: R,
    started: SharingStarted = SharingStarted.WhileSubscribed(5_000),
    mapper: (T) -> R
): StateFlow<R> =
    this
        .map(mapper)
        .distinctUntilChanged()
        .stateIn(scope, started, initial)