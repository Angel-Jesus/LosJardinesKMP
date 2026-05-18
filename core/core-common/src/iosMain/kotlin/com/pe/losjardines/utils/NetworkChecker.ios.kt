package com.pe.losjardines.utils

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import platform.SystemConfiguration.SCNetworkReachabilityCreateWithName
import platform.SystemConfiguration.SCNetworkReachabilityGetFlags
import platform.SystemConfiguration.SCNetworkReachabilityFlagsVar
import platform.SystemConfiguration.kSCNetworkReachabilityFlagsReachable
import platform.SystemConfiguration.kSCNetworkReachabilityFlagsConnectionRequired

@OptIn(ExperimentalForeignApi::class)
class IosNetworkChecker : NetworkChecker {

    override fun isConnected(): Boolean {
        val reachability = SCNetworkReachabilityCreateWithName(null, "8.8.8.8")
            ?: return false

        return memScoped {
            val flags = alloc<SCNetworkReachabilityFlagsVar>()
            val gotFlags = SCNetworkReachabilityGetFlags(reachability, flags.ptr)

            if (!gotFlags) return@memScoped false

            val isReachable = flags.value and kSCNetworkReachabilityFlagsReachable != 0u
            val needsConnection = flags.value and kSCNetworkReachabilityFlagsConnectionRequired != 0u

            isReachable && !needsConnection
        }
    }
}