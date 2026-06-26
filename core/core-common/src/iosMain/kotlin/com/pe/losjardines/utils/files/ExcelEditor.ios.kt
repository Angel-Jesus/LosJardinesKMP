package com.pe.losjardines.utils.files

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure

actual class ExcelEditor actual constructor() : ExcelGenerator {
    actual override fun generarDesdeTemplate(
        templateStream: PlatformInputStream,
        outputFile: PlatformFile,
        updates: List<ExcelCellUpdate>,
        sheetName: String
    ): Either<Failure, PlatformFile> = Either.Error(Failure.fromThrowable(Throwable("Not supported")))
}