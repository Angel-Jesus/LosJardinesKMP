package com.pe.losjardines.utils

import java.net.InetSocketAddress
import java.net.Socket

class DesktopNetworkChecker : NetworkChecker {
    override fun isConnected(): Boolean {
        return try {
            Socket().use { socket ->
                socket.connect(InetSocketAddress("8.8.8.8", 53), 1500)
                true
            }
        } catch (e: Exception) {
            false
        }
    }
}
