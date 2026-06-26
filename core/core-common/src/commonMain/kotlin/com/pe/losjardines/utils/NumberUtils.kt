package com.pe.losjardines.utils

fun Double?.toSmartString(): String {
    if(this == null) return "0"

    val cents = kotlin.math.round(this * 100).toLong()
    val intPart = cents / 100
    val decPart = cents % 100

    return if (decPart == 0L) {
        intPart.toString()
    } else {
        "$intPart.${decPart.toString().padStart(2, '0')}"
    }
}

fun String?.orZero(): String = if(this.isNullOrBlank()) "0" else this