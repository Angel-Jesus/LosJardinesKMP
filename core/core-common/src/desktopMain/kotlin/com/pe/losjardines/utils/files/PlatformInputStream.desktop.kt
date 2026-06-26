package com.pe.losjardines.utils.files

import java.io.InputStream

actual class PlatformInputStream(private val stream: InputStream) {
    actual fun readBytes(): ByteArray = stream.readBytes()
    actual fun close() = stream.close()

    fun toJavaInputStream(): InputStream = stream
}