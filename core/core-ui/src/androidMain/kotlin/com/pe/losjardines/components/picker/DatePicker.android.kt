package com.pe.losjardines.components.picker

import kotlinx.datetime.LocalDate

actual fun nativeDatePicker(
    initialDate: LocalDate,
    minDate: LocalDate?,
    maxDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: (() -> Unit)?
) {
}