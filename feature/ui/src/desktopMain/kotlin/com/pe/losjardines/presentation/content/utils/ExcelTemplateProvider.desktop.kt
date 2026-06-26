package com.pe.losjardines.presentation.content.utils

import com.pe.losjardines.utils.files.PlatformFile
import com.pe.losjardines.utils.files.PlatformInputStream
import java.io.File

actual class ExcelTemplateProvider actual constructor(){
    // Abre la plantilla desde resources del JAR
    actual fun openTemplate(fileName: String): PlatformInputStream {
        val stream = this::class.java.classLoader
            .getResourceAsStream(fileName)               // lee de src/main/resources/
            ?: error("Plantilla '$fileName' no encontrada en resources")
        return PlatformInputStream(stream)
    }

    // La copia se guarda en ~/Reportes/
    actual fun outputFile(fileName: String): PlatformFile {
        val file = File(System.getProperty("user.home"), "Reportes/$fileName")
        return PlatformFile(file)
    }
}