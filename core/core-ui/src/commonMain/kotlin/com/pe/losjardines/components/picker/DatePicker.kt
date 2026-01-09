package com.pe.losjardines.components.picker

import kotlinx.datetime.LocalDate

expect fun nativeDatePicker(
    initialDate: LocalDate,
    minDate: LocalDate? = null,
    maxDate: LocalDate? = null,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: (() -> Unit)? = null
)