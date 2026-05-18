package com.pe.losjardines.components.picker

import androidx.compose.runtime.Composable
import kotlinx.datetime.LocalDate

@Composable
actual fun NativeDatePicker(
    initialDate: LocalDate?,
    minDate: LocalDate?,
    maxDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
}