package com.pe.losjardines.core.components.picker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

object Picker {
    @Composable
    fun date(isShow: MutableState<Boolean>,onDateSelected: (String) -> Unit){
        DatePicker(
            isShow = isShow,
            onDateSelected = onDateSelected
        )
    }

    @Composable
    fun time(isShow: MutableState<Boolean>, onTimeSelected: (String) -> Unit) {
        TimePicker(
            isShow = isShow,
            onTimeSelected = onTimeSelected
        )
    }
}