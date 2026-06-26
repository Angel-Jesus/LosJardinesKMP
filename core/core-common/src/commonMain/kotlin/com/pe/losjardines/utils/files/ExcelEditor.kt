package com.pe.losjardines.utils.files

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure

expect class ExcelEditor() : ExcelGenerator {
    override fun generarDesdeTemplate(
        templateStream: PlatformInputStream,
        outputFile: PlatformFile,
        updates: List<ExcelCellUpdate>,
        sheetName: String
    ): Either<Failure, PlatformFile>
}