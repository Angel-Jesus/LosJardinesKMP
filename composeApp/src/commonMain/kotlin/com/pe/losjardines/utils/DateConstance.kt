package com.pe.losjardines.utils

enum class MonthFilter(val number: String, val displayName: String) {
    NONE("00", "Seleccionar mes"),
    JANUARY("01", "Enero"),
    FEBRUARY("02", "Febrero"),
    MARCH("03", "Marzo"),
    APRIL("04", "Abril"),
    MAY("05", "Mayo"),
    JUNE("06", "Junio"),
    JULY("07", "Julio"),
    AUGUST("08", "Agosto"),
    SEPTEMBER("09", "Septiembre"),
    OCTOBER("10", "Octubre"),
    NOVEMBER("11", "Noviembre"),
    DECEMBER("12", "Diciembre");

    companion object {
        fun getMonthValue(displayName: String): String = entries.find { it.displayName == displayName }?.number ?: NONE.number
        fun fromNumber(number: String): MonthFilter? = entries.find { it.number == number }
        fun fromDisplayName(name: String): MonthFilter? = entries.find { it.displayName == name }
    }
}