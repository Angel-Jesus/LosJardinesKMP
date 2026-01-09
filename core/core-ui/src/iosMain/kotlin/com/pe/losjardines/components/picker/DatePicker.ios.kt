package com.pe.losjardines.components.picker

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
    return calendar.dateFromComponents(components)!!
}

private fun NSDate.toLocalDate(): LocalDate {
    val instant = Instant.fromEpochSeconds(
        this.timeIntervalSince1970.toLong()
    )
    return instant
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date
}

actual fun nativeDatePicker(
    initialDate: LocalDate,
    minDate: LocalDate?,
    maxDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: (() -> Unit)?
) {
    val picker = UIDatePicker().apply {
        datePickerMode = UIDatePickerMode.UIDatePickerModeDate
        preferredDatePickerStyle = UIDatePickerStyle.UIDatePickerStyleInline
        date = initialDate.toNSDate()
        minimumDate = minDate?.toNSDate()
        maximumDate = maxDate?.toNSDate()
    }

    val alert = UIAlertController.alertControllerWithTitle(
        title = "Selecciona fecha",
        message = "\n\n\n\n\n\n",
        preferredStyle = UIAlertControllerStyleActionSheet
    )

    picker.translatesAutoresizingMaskIntoConstraints = false
    alert.view.addSubview(picker)

    NSLayoutConstraint.activateConstraints(
        listOf(
            picker.centerXAnchor.constraintEqualToAnchor(alert.view.centerXAnchor),
            picker.topAnchor.constraintEqualToAnchor(alert.view.topAnchor, constant = 20.0)
        )
    )

    alert.addAction(
        UIAlertAction.actionWithTitle(
            "Aceptar",
            UIAlertActionStyleDefault
        ) {
            val localDate = picker.date.toLocalDate()
            onDateSelected(localDate)
        }
    )

    alert.addAction(
        UIAlertAction.actionWithTitle(
            "Cancelar",
            UIAlertActionStyleCancel
        ) {
            onDismiss?.invoke()
        }
    )

    val rootVC = UIApplication.sharedApplication
        .keyWindow
        ?.rootViewController

    rootVC?.presentViewController(alert, true, null)
}