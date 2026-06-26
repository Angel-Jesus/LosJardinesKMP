package com.pe.losjardines.presentation.content.utils

import android.content.Context
import com.pe.losjardines.utils.files.PlatformFile
import com.pe.losjardines.utils.files.PlatformInputStream
import org.koin.core.context.GlobalContext
import java.io.File

actual class ExcelTemplateProvider actual constructor(){
    private val context: Context
        get() = GlobalContext.get().get<Context>()

    // Abre la plantilla desde assets de Android
    actual fun openTemplate(fileName: String): PlatformInputStream {
        val stream = context.assets.open(fileName)   // lee de src/main/assets/
        return PlatformInputStream(stream)
    }

    // La copia se guarda en almacenamiento interno de la app
    actual fun outputFile(fileName: String): PlatformFile {
        val file = File(context.filesDir, fileName)
        return PlatformFile(file)
    }
}