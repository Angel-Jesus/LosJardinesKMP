package com.pe.losjardines.utils.files

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure

/**
 * Abstracción para generar el reporte Excel a partir de un template.
 * Permite inyectar implementaciones de prueba (fakes) en los UseCases.
 */
interface ExcelGenerator {
    fun generarDesdeTemplate(
        templateStream: PlatformInputStream,
        outputFile: PlatformFile,
        updates: List<ExcelCellUpdate>,
        sheetName: String
    ): Either<Failure, PlatformFile>
}
