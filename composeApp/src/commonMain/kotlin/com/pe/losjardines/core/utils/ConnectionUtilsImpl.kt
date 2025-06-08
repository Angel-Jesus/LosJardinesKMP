package com.pe.losjardines.core.utils

class ConnectionUtilsImpl: ConnectionUtils {
    override fun isNetworkAvailable(): Boolean {
        return true
    }

    override fun checkIfInternetIsSlow(): Boolean {
        return true
    }
}