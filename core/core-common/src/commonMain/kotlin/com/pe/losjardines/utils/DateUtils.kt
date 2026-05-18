package com.pe.losjardines.utils

import com.pe.losjardines.utils.companions.EMPTY
import com.pe.losjardines.utils.extension.toStrInt
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
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

fun String.toLocalDate(): LocalDate?{
    return try {
        val parts = this.split("/")
        if (parts.size != 3) return null

        val day   = parts[0].toInt()
        val month = parts[1].toInt()
        val year  = parts[2].toInt()

        LocalDate(year, month, day)
    } catch (_: Exception) {
        null
    }
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

fun LocalDate.toEpochMillis(): Long {
    return this.atTime(12, 0)
        .toInstant(TimeZone.UTC)
        .toEpochMilliseconds()
}

fun String.dateToEpochMillis(): Long? {
    return try {
        val parts = this.split("/")
        if (parts.size != 3) return null

        val day   = parts[0].toInt()
        val month = parts[1].toInt()
        val year  = parts[2].toInt()

        LocalDate(year, month, day)
            .atTime(12, 0)
            .toInstant(TimeZone.UTC)
            .toEpochMilliseconds()
    } catch (_: Exception) {
        null
    }
}

fun getDateInitToEpochMillis(): Long{
    val dateNow = getDateNow()
    val dateInit = LocalDate(dateNow.year, dateNow.monthNumber, 1)
    return dateInit.toEpochMillis()
}

fun getDateLastToEpochMillis(): Long{
    val dateNow = getDateNow()
    val dateLast = LocalDate(dateNow.year, dateNow.monthNumber, getLastDayOfMonth(dateNow.year, dateNow.monthNumber))
    return dateLast.toEpochMillis()
}

fun getDateToEpochMillis(day: Int, month: Int, year: Int): Long? {
    return try {
        LocalDate(year, month, day)
            .atTime(12, 0)
            .toInstant(TimeZone.UTC)
            .toEpochMilliseconds()
    } catch (_: Exception) {
        null
    }
}

fun getLastDayOfMonth(year: Int, month: Int): Int {
    val firstDayNextMonth = LocalDate(year, month, 1).plus(1, DateTimeUnit.MONTH)
    return firstDayNextMonth.minus(1, DateTimeUnit.DAY).dayOfMonth
}

fun Long?.toLocalDate(): String {
    if(this == null) return String.EMPTY
    val localDate = Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.UTC).date
    return localDate.toDateStringResult()
}
