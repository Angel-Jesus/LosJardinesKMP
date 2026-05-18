package com.pe.losjardines.utils

import java.net.InetSocketAddress
import java.net.Socket

actual class NetworkChecker {
    actual fun isConnected(): Boolean {
        return try {
            // Intenta conectar a DNS de Google con timeout de 1.5s
            Socket().use { socket ->
                socket.connect(InetSocketAddress("8.8.8.8", 53), 1500)
                true
            }
        } catch (e: Exception) {
            false
        }
    }
}