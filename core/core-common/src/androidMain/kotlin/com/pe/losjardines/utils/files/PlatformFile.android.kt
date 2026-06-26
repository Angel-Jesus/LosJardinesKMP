package com.pe.losjardines.utils.files

import java.io.File

actual class PlatformFile( private val file: File ) {
    actual val path: String get() = file.absolutePath
    actual val name: String get() = file.name
    actual fun exists(): Boolean = file.exists()

    // Función extra solo disponible en Android/Desktop (no en common)
    // Permite obtener el java.io.File real cuando se necesita en Apache POI
    fun toJavaFile(): File = file
}