package com.pe.losjardines.utils

import com.pe.losjardines.utils.extension.toStrInt
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


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