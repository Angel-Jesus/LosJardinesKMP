package com.pe.losjardines.utils

enum class StateProcess(val description: String) {
    SYNC("SYNC"),
    PENDING_INSERT("PENDING_INSERT"),
    PENDING_UPDATE("PENDING_UPDATE"),
    PENDING_DELETE("PENDING_DELETE")
}