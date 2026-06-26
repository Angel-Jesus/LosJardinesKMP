package com.pe.losjardines.utils.files

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileOutputStream

actual class ExcelEditor actual constructor() {
    actual fun generarDesdeTemplate(
        templateStream: PlatformInputStream,
        outputFile: PlatformFile,
        updates: List<ExcelCellUpdate>
    ): Either<Failure, PlatformFile> = runCatching {

        val workbook = WorkbookFactory.create(
            templateStream.toJavaInputStream()
        ) as XSSFWorkbook

        workbook.setForceFormulaRecalculation(true)

        updates.forEach { update ->
            val sheet = workbook.getSheet(update.sheet) ?: return@forEach
            val row   = sheet.getRow(update.row) ?: sheet.createRow(update.row)
            val cell  = row.getCell(update.col)  ?: row.createCell(update.col)

            when (val v = update.value) {
                is Double  -> cell.setCellValue(v)
                is Int     -> cell.setCellValue(v.toDouble())
                is String  -> cell.setCellValue(v)
                is Boolean -> cell.setCellValue(v)
            }
        }

        val javaFile = outputFile.toJavaFile()
        javaFile.parentFile?.mkdirs()
        FileOutputStream(javaFile).use { workbook.write(it) }
        workbook.close()

        Either.Success(outputFile)
    }.getOrElse { error ->
        Either.Error(Failure.fromThrowable(error))
    }
}