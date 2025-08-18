package com.pe.losjardines.utils.constance

enum class MonthFilter(val number: Int, val displayName: String) {
    NONE(0, "Seleccionar mes"),
    JANUARY(1, "Enero"),
    FEBRUARY(2, "Febrero"),
    MARCH(3, "Marzo"),
    APRIL(4, "Abril"),
    MAY(5, "Mayo"),
    JUNE(6, "Junio"),
    JULY(7, "Julio"),
    AUGUST(8, "Agosto"),
    SEPTEMBER(9, "Septiembre"),
    OCTOBER(10, "Octubre"),
    NOVEMBER(11, "Noviembre"),
    DECEMBER(12, "Diciembre");

    companion object {
        fun fromNumber(number: Int): MonthFilter? = entries.find { it.number == number }
        fun fromDisplayName(name: String): MonthFilter? = entries.find { it.displayName == name }
    }
}