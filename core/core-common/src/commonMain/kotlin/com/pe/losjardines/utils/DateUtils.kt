package com.pe.losjardines.utils

import com.pe.losjardines.utils.extension.toStrInt
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun getDayNow(): LocalDate =
    Clock.System
        .now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date

fun LocalDate.toDateStringResult(): String {
    val day = dayOfMonth.toString().padStart(2, '0')
    val month = monthNumber.toString().padStart(2, '0')
    val year = year.toString()

    return "$day/$month/$year"
}
fun getDateNow(): LocalDateTime{
    val currentDateTime = Clock.System.now()
    return currentDateTime.toLocalDateTime(TimeZone.UTC)
}

fun generateFirebaseDocumentId(): String{
    val dateTimeInUtc = getDateNow()
    val year = dateTimeInUtc.year
    val month = dateTimeInUtc.monthNumber.toStrInt()
    val day = dateTimeInUtc.dayOfMonth.toStrInt()
    val hour = dateTimeInUtc.time.hour.toStrInt()
    val minute = dateTimeInUtc.time.minute.toStrInt()
    val second = dateTimeInUtc.time.second.toStrInt()

    return "$year$month$day$hour$minute$second"
}