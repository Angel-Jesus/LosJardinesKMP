package com.pe.losjardines.core.components.picker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
actual fun DatePicker(
    isShow: MutableState<Boolean>,
    onDateSelected: (String) -> Unit
) {
}

@Composable
actual fun TimePicker(
    isShow: MutableState<Boolean>,
    onTimeSelected: (String) -> Unit
) {
}