package com.pe.losjardines.utils

import platform.Network.NWPathMonitor
import platform.Network.nw_path_status_satisfied
import platform.darwin.dispatch_get_main_queue

actual class NetworkChecker {
    actual fun isConnected(): Boolean {
        val monitor = NWPathMonitor()
        var isConnected = false

        monitor.setUpdateHandler { path ->
            isConnected = path.status == nw_path_status_satisfied
        }
        monitor.startWithQueue(dispatch_get_main_queue())

        // Pequeña pausa para obtener el estado inicial del path
        platform.Foundation.NSRunLoop.currentRunLoop.runUntilDate(
            platform.Foundation.NSDate.dateWithTimeIntervalSinceNow(0.1)
        )

        monitor.cancel()
        return isConnected
    }
}