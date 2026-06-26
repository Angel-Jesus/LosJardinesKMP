package com.pe.losjardines.utils.files

expect class PlatformFile {
    val path: String        // Ruta del archivo en el sistema
    val name: String        // Nombre del archivo con extensión
    fun exists(): Boolean   // Verifica si el archivo existe
}
