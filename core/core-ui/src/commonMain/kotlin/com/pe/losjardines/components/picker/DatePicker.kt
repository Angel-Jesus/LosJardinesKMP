package com.pe.losjardines.components.picker

import androidx.compose.runtime.Composable
import kotlinx.datetime.LocalDate

/** Date Picker multiplataforma
 * @param initialDate variable para inicializar la fecha marcada por defecto
 * @param minDate variable para tener la fecha minima habilitada para seleccionar
 * @param maxDate variable para tener la fecha maxima habilitada para seleccionar
 * @param onDateSelected callback que retorna la fecha seleccionada
 * @param onDismiss callback para cerrar el date picker
 **/
@Composable
expect fun NativeDatePicker(
    initialDate: LocalDate?,
    minDate: LocalDate? = null,
    maxDate: LocalDate? = null,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit = {}
)