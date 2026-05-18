package com.pe.losjardines.components.picker

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

/**
 * Convierte LocalDate a milisegundos UTC (requerido por Material3 DatePicker).
 * Se usa mediodía (12:00) para evitar desfases por zona horaria.
 */
private fun LocalDate.toUtcMillis(): Long {
    return this.atTime(12, 0)
        .toInstant(TimeZone.UTC)
        .toEpochMilliseconds()
}

/**
 * Convierte milisegundos UTC a LocalDate.
 */
private fun Long.toLocalDate(): LocalDate {
    return Instant.fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.UTC)
        .date
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
actual fun NativeDatePicker(
    initialDate: LocalDate?,
    minDate: LocalDate?,
    maxDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss:  () -> Unit
) {

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDate?.toUtcMillis(),
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val date = utcTimeMillis.toLocalDate()
                val afterMin  = minDate == null || date >= minDate
                val beforeMax = maxDate == null || date <= maxDate
                return afterMin && beforeMax
            }
        }
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis
                        ?.toLocalDate()
                        ?.let(onDateSelected)
                    onDismiss()
                }
            ) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}