package com.pe.losjardines.utils.functions

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Int.toStrInt(): String = if (this < 10) {
    "0$this"
} else {
    "$this"
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