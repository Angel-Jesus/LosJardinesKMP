package com.pe.losjardines.core.utils

interface ConnectionUtils {
    fun isNetworkAvailable(): Boolean
    fun checkIfInternetIsSlow(): Boolean
}