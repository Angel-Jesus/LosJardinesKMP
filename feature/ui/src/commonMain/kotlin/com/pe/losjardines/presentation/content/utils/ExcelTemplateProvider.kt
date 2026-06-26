package com.pe.losjardines.presentation.content.utils

import com.pe.losjardines.utils.files.PlatformFile
import com.pe.losjardines.utils.files.PlatformInputStream

expect class ExcelTemplateProvider() {
    // Abre la plantilla y retorna un stream listo para leer
    fun openTemplate(fileName: String): PlatformInputStream

    // Retorna el archivo de destino donde se guardará la copia
    fun outputFile(fileName: String): PlatformFile
}