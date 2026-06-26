package com.pe.losjardines.usecases.content

import com.pe.losjardines.usecases.model.FilterValues
import com.pe.losjardines.utils.constance.MonthFilter
import com.pe.losjardines.utils.files.ExcelEditor
import com.pe.losjardines.utils.files.PlatformFile
import com.pe.losjardines.utils.files.PlatformInputStream
import com.pe.losjardines.utils.files.model.AccommodationColumn
import com.pe.losjardines.utils.files.model.CellPosition
import com.pe.losjardines.utils.files.model.DailyArrivalDay
import com.pe.losjardines.utils.files.model.ExcelCellSection
import com.pe.losjardines.utils.files.model.ForeignCountry
import com.pe.losjardines.utils.files.model.PeruRegion
import com.pe.losjardines.utils.files.model.ResidenceMetric
import com.pe.losjardines.utils.files.model.TravelReason
import kotlinx.coroutines.runBlocking
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File
import java.io.FileInputStream
import java.time.LocalDate

/**
 * Genera el Excel real (ExcelEditor + plantilla reporte_mensual.xlsx) con un
 * mock que cubre todos los países y regiones, y valida —leyendo el archivo
 * generado— que cada dato quedó escrito en la celda correcta del Capítulo IV
 * (Residencia: extranjeros y residentes del Perú).
 */
class GenerateExcelReportFileValidationTest {

    private val previousMonth: LocalDate = LocalDate.now().minusMonths(1)
    private val year = previousMonth.year
    private val month = previousMonth.monthValue
    private val lastDay = previousMonth.lengthOfMonth()

    @Test
    fun `el excel generado se llena correctamente para todos los paises y regiones`() = runBlocking {
        val mock = ReportMockData.fullCoverage(year, month, lastDay)

        val useCase = GenerateExcelReportUseCase(
            getClientsRegisterUseCase = GetClientsRegisterUseCase(
                firestoreRepository = FakeFirestoreRepository(),
                databaseRepository = FakeDatabaseRepository(mock.registrations)
            ),
            excelEditor = ExcelEditor() // implementación real (Apache POI)
        )

        val template = javaClass.classLoader?.getResourceAsStream("reporte_mensual.xlsx")
            ?: error("No se encontró la plantilla reporte_mensual.xlsx en resources de test")

        // Ruta fija e inspeccionable: <módulo domain>/build/generated-reports/
        val outputFile = File("build/generated-reports/reporte_validacion_${year}_${month}.xlsx")
            .apply { parentFile?.mkdirs() }

        val params = GenerateExcelReportUseCase.Params(
            templateStream = PlatformInputStream(template),
            outputFile = PlatformFile(outputFile),
            filter = FilterValues(
                month = MonthFilter.fromNumber(month)!!.displayName,
                year = year.toString()
            )
        )

        val output = useCase.run(params)
        assertTrue("El archivo de salida no fue generado", File(output.path).exists())
        println("Reporte generado en: ${File(output.path).absolutePath}")

        val section = ExcelCellSection()

        WorkbookFactory.create(FileInputStream(output.path)).use { workbook ->
            val sheet = workbook.getSheet("REPORTE MENSUAL")
                ?: error("La hoja 'REPORTE MENSUAL' no existe en el archivo generado")

            println("\n===== CAPÍTULO II · ALOJAMIENTO (por tipo de habitación) =====")
            section.chapterII.forEach { map ->
                val metrics = mock.accommodation[map.roomType] ?: ReportMockData.RoomMetrics(0, 0, 0)
                assertCell(sheet, map.cells[AccommodationColumn.ARRIVALS], metrics.arrivals, "Cap II llegadas ${map.roomType}")
                assertCell(sheet, map.cells[AccommodationColumn.OCCUPIED_ROOMS], metrics.occupiedRooms, "Cap II habitaciones ${map.roomType}")
                assertCell(sheet, map.cells[AccommodationColumn.OVERNIGHTS], metrics.overnights, "Cap II pernoctaciones ${map.roomType}")
            }

            println("\n===== CAPÍTULO III · LLEGADAS POR DÍA =====")
            section.chapterIII.forEach { map ->
                val expected = if (map.day == DailyArrivalDay.TOTAL) {
                    mock.arrivalsTotal
                } else {
                    mock.arrivalsByDay[map.day.day] ?: 0
                }
                assertCell(sheet, map.cell, expected, "Cap III ${map.day}")
            }

            println("\n===== CAPÍTULO IV · RESIDENCIA EXTRANJERA =====")
            // --- Residencia extranjera ---
            section.chapterIV.foreignResidence.forEach { map ->
                val expected = if (map.country == ForeignCountry.TOTAL) {
                    mock.foreignTotal
                } else {
                    mock.foreignValue[map.country] ?: 0
                }
                assertCell(sheet, map.cells[ResidenceMetric.ARRIVALS], expected, "Llegadas extranjero ${map.country}")
                assertCell(sheet, map.cells[ResidenceMetric.OVERNIGHTS], expected, "Pernoctaciones extranjero ${map.country}")
            }

            println("\n===== CAPÍTULO IV · RESIDENCIA PERÚ =====")
            // --- Residencia Perú ---
            section.chapterIV.peruResidence.forEach { map ->
                val expected = when (map.region) {
                    PeruRegion.TOTAL_RESIDENTS -> mock.peruTotal
                    PeruRegion.TOTAL_GENERAL -> mock.generalTotal
                    else -> mock.peruValue[map.region] ?: 0
                }
                assertCell(sheet, map.cells[ResidenceMetric.ARRIVALS], expected, "Llegadas Perú ${map.region}")
                assertCell(sheet, map.cells[ResidenceMetric.OVERNIGHTS], expected, "Pernoctaciones Perú ${map.region}")
            }

            println("\n===== CAPÍTULO V · MOTIVO DE VIAJE =====")
            section.chapterV.forEach { map ->
                val reasons = mock.travelReason[map.guestCategory] ?: emptyMap()
                val totalCategory = reasons.values.sum()
                map.cells.forEach { (reason, pos) ->
                    val expected = if (reason == TravelReason.TOTAL_ARRIVALS) totalCategory else (reasons[reason] ?: 0)
                    assertCell(sheet, pos, expected, "Cap V ${map.guestCategory} $reason")
                }
            }
        }
    }

    private fun assertCell(sheet: Sheet, pos: CellPosition?, expected: Int, label: String) {
        if (pos == null) return
        val actual = sheet.getRow(pos.row)?.getCell(pos.column)?.numericCellValue
            ?: error("Celda vacía en ($label) fila=${pos.row} col=${pos.column}")

        val ok = actual == expected.toDouble()
        val mark = if (ok) "✓" else "✗"
        // Se imprime SIEMPRE (éxito y error); el assert luego falla el test si no coincide.
        println("$mark $label [fila=${pos.row}, col=${pos.column}] esperado=$expected, real=${actual.toInt()}")

        assertEquals("$label en fila=${pos.row} col=${pos.column}", expected.toDouble(), actual, 0.0)
    }
}
