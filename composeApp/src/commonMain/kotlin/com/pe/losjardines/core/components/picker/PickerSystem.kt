package com.pe.losjardines.core.components.picker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
expect fun DatePicker(isShow: MutableState<Boolean>, onDateSelected: (String) -> Unit)
@Composable
expect fun TimePicker(isShow: MutableState<Boolean>, onTimeSelected: (String) -> Unit)