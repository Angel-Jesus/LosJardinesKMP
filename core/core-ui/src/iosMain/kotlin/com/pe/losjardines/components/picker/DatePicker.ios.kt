package com.pe.losjardines.components.picker

import androidx.compose.runtime.Composable
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import platform.Foundation.NSCalendar
import platform.Foundation.NSDate
import platform.Foundation.NSDateComponents
import platform.Foundation.timeIntervalSince1970
import platform.UIKit.NSLayoutConstraint
import platform.UIKit.UIAlertAction
import platform.UIKit.UIAlertActionStyleCancel
import platform.UIKit.UIAlertActionStyleDefault
import platform.UIKit.UIAlertController
import platform.UIKit.UIAlertControllerStyleActionSheet
import platform.UIKit.UIApplication
import platform.UIKit.UIDatePicker
import platform.UIKit.UIDatePickerMode
import platform.UIKit.UIDatePickerStyle

private fun LocalDate.toNSDate(): NSDate {
    val calendar = NSCalendar.currentCalendar
    val components = NSDateComponents().apply {
        year = this@toNSDate.year.toLong()
        month = this@toNSDate.monthNumber.toLong()
        day = this@toNSDate.dayOfMonth.toLong()
    }
    return calendar.dateFromComponents(components) ?: NSDate()
}

private fun NSDate.toLocalDate(): LocalDate {
    val instant = Instant.fromEpochSeconds(
        this.timeIntervalSince1970.toLong()
    )
    return instant
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date
}

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun NativeDatePicker(
    initialDate: LocalDate?,
    minDate: LocalDate?,
    maxDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    val rootVC = UIApplication.sharedApplication
        .keyWindow
        ?.rootViewController ?: return

    val picker = UIDatePicker().apply {
        datePickerMode = UIDatePickerMode.UIDatePickerModeDate
        preferredDatePickerStyle = UIDatePickerStyle.UIDatePickerStyleWheels
        date = initialDate?.toNSDate() ?: NSDate()
        minimumDate = minDate?.toNSDate()
        maximumDate = maxDate?.toNSDate()
        translatesAutoresizingMaskIntoConstraints = false
    }

    val alert = UIAlertController.alertControllerWithTitle(
        title = "Selecciona fecha",
        message = "\n\n\n\n\n\n\n\n\n",
        preferredStyle = UIAlertControllerStyleActionSheet
    )

    alert.view.addSubview(picker)

    NSLayoutConstraint.activateConstraints(listOf(
        picker.centerXAnchor.constraintEqualToAnchor(alert.view.centerXAnchor),
        picker.topAnchor.constraintEqualToAnchor(alert.view.topAnchor, constant = 50.0),
        picker.leadingAnchor.constraintEqualToAnchor(alert.view.leadingAnchor, constant = 8.0),
        picker.trailingAnchor.constraintEqualToAnchor(alert.view.trailingAnchor, constant = -8.0),
    ))

    alert.addAction(UIAlertAction.actionWithTitle("Aceptar", UIAlertActionStyleDefault) {
        onDateSelected(picker.date.toLocalDate())
    })

    alert.addAction(UIAlertAction.actionWithTitle("Cancelar", UIAlertActionStyleCancel) {
        onDismiss()
    })

    rootVC.presentViewController(alert, true, null)
}