package com.pe.losjardines.utils.files

expect class PlatformInputStream {
    fun readBytes(): ByteArray   // Lee todo el contenido como bytes
    fun close()                  // Cierra el stream
}