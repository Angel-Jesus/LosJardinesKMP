package com.pe.losjardines.presentation.content.utils

import com.pe.losjardines.utils.files.PlatformFile
import com.pe.losjardines.utils.files.PlatformInputStream

actual class ExcelTemplateProvider actual constructor(){
    actual fun openTemplate(fileName: String): PlatformInputStream {
        TODO("Not yet implemented")
    }

    actual fun outputFile(fileName: String): PlatformFile {
        TODO("Not yet implemented")
    }
}