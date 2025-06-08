package com.pe.losjardines.core.components.picker

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.pe.losjardines.core.values.BrandTextColor
import com.pe.losjardines.core.values.LocalAppTypography
import com.pe.losjardines.core.values.PickerHorizontalColor
import com.pe.losjardines.core.values.PickerString
import com.pe.losjardines.core.values.UnfocusedBorderColor
import network.chaintech.kmp_date_time_picker.ui.datepicker.WheelDatePickerView
import network.chaintech.kmp_date_time_picker.ui.timepicker.WheelTimePickerView
import network.chaintech.kmp_date_time_picker.utils.DateTimePickerView
import network.chaintech.kmp_date_time_picker.utils.TimeFormat
import network.chaintech.kmp_date_time_picker.utils.WheelPickerDefaults
import network.chaintech.kmp_date_time_picker.utils.timeToString

@Composable
actual fun DatePicker(
    isShow: MutableState<Boolean>,
    onDateSelected: (String) -> Unit
) {
    val typography = LocalAppTypography.current

    WheelDatePickerView(
        title = PickerString.DATE,
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(top = 50.dp, bottom = 50.dp, start = 24.dp, end = 24.dp),
        showDatePicker = isShow.value,
        doneLabel = PickerString.DONE,
        titleStyle = typography.titleMedium,
        doneLabelStyle = typography.titleBrand,
        dateTextColor = BrandTextColor,
        selectorProperties = WheelPickerDefaults.selectorProperties(
            borderColor = UnfocusedBorderColor
        ),
        rowCount = 5,
        height = 150.dp,
        dragHandle = {
            HorizontalDivider(
                modifier = Modifier
                    .padding(top = 50.dp)
                    .width(50.dp)
                    .clip(CircleShape),
                thickness = 4.dp,
                color = UnfocusedBorderColor
            )
        },
        shape = RoundedCornerShape(
            topStart = 18.dp,
            topEnd = 18.dp,
            bottomEnd = 18.dp,
            bottomStart = 18.dp
        ),
        dateTimePickerView = DateTimePickerView.DIALOG_VIEW,
        onDoneClick = { localDate ->
            val day = localDate.dayOfMonth.toString().padStart(2, '0')
            val month = localDate.monthNumber.toString().padStart(2, '0')
            val year = localDate.year.toString()
            onDateSelected("$day/$month/$year")
            isShow.value = false
        },
        onDismiss = {
            isShow.value = false
        }
    )
}

@Composable
actual fun TimePicker(
    isShow: MutableState<Boolean>,
    onTimeSelected: (String) -> Unit
) {
    val typography = LocalAppTypography.current

    WheelTimePickerView(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp, bottom = 50.dp, start = 24.dp, end = 24.dp),
        showTimePicker = isShow.value,
        title = PickerString.TIME,
        doneLabel = PickerString.DONE,
        titleStyle = typography.titleMedium,
        doneLabelStyle = typography.titleBrand,
        textColor = BrandTextColor,
        timeFormat = TimeFormat.AM_PM,
        selectorProperties = WheelPickerDefaults.selectorProperties(
            borderColor = UnfocusedBorderColor
        ),
        rowCount = 5,
        height = 180.dp,
        dragHandle = {
            HorizontalDivider(
                modifier = Modifier
                    .padding(top = 18.dp)
                    .width(50.dp)
                    .clip(CircleShape),
                thickness = 4.dp,
                color = PickerHorizontalColor
            )
        },
        shape = RoundedCornerShape(
            topStart = 18.dp,
            topEnd = 18.dp,
            bottomEnd = 18.dp,
            bottomStart = 18.dp
        ),
        dateTimePickerView = DateTimePickerView.DIALOG_VIEW,
        onDoneClick = { localTime ->
            onTimeSelected(timeToString(localTime, "hh:mm a"))
            isShow.value = false
        },
        onDismiss = {
            isShow.value = false
        }
    )
}