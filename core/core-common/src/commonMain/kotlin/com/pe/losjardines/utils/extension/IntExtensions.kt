package com.pe.losjardines.utils.extension

fun Int.toStrInt(): String = if (this < 10) {
    "0$this"
} else {
    "$this"
}