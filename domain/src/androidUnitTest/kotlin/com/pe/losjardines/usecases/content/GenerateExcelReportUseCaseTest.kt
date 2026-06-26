package com.pe.losjardines.usecases.content

import com.pe.losjardines.usecases.model.FilterValues
import com.pe.losjardines.usecases.model.RegistrationDto
import com.pe.losjardines.utils.constance.MonthFilter
import com.pe.losjardines.utils.files.PlatformFile
import com.pe.losjardines.utils.files.PlatformInputStream
import com.pe.losjardines.utils.getDateToEpochMillis
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.File
import java.time.LocalDate

class GenerateExcelReportUseCaseTest {

    // Mes anterior al de hoy (día 1 al último día).
    private val previousMonth: LocalDate = LocalDate.now().minusMonths(1)
    private val year = previousMonth.year
    private val month = previousMonth.monthValue
    private val lastDay = previousMonth.lengthOfMonth()

    /**
     * Datos mock distribuidos a lo largo del mes anterior: primer día,
     * mitad de mes y último día. Totales esperados:
     *  - llegadas (1 + acompañantes): 2 + 1 + 3 = 6
     *  - pernoctaciones: 2 + 1 + 1 = 4
     */
    private fun previousMonthRegistrations(): List<RegistrationDto> = listOf(
        registration(day = 1, exitDay = 3, country = "Perú", region = "Lima", companions = listOf("c1")),       // 2 llegadas, 2 noches
        registration(day = 15, exitDay = 16, country = "Perú", region = "Cusco", companions = emptyList()),     // 1 llegada, 1 noche
        registration(day = lastDay, exitDay = lastDay, country = "Chile", region = "", companions = listOf("x", "y")) // 3 llegadas, 1 noche
    )

    private fun registration(
        day: Int,
        exitDay: Int,
        country: String,
        region: String,
        companions: List<String>
    ) = RegistrationDto(
        collection = "registrations",
        country = country,
        dateEnter = "$day/$month/$year",
        dateExit = "$exitDay/$month/$year",
        fee = "100",
        name = "Huésped $day",
        sex = "M",
        typeDocument = "DNI",
        numberDocument = "1000000$day",
        reasonTravel = "Vacaciones, recreación u ocio",
        region = region,
        typeRoom = "Simple",
        room = "$day",
        companions = companions
    )

    @Test
    fun `genera el reporte del mes anterior consultando del dia 1 al ultimo dia`() = runBlocking {
        val registrations = previousMonthRegistrations()
        val fakeDb = FakeDatabaseRepository(registrations)
        val fakeExcel = FakeExcelGenerator()

        val getClients = GetClientsRegisterUseCase(FakeFirestoreRepository(), fakeDb)
        val useCase = GenerateExcelReportUseCase(getClients, fakeExcel)

        val filter = FilterValues(
            month = MonthFilter.fromNumber(month)!!.displayName,
            year = year.toString()
        )
        val params = GenerateExcelReportUseCase.Params(
            templateStream = PlatformInputStream(ByteArrayInputStream(ByteArray(0))),
            outputFile = PlatformFile(File.createTempFile("reporte", ".xlsx")),
            filter = filter
        )

        val output = useCase.run(params)

        println("===== Reporte mes anterior: $month/$year (día 1 a $lastDay) =====")

        // 1. Devuelve el archivo de salida.
        println("✓ Archivo de salida: ${output.path}")
        assertEquals(params.outputFile.path, output.path)

        // 2. El repositorio se consultó con el rango del mes anterior: día 1 → último día.
        println("✓ Rango consultado: init=${fakeDb.lastDateInit} (día 1), last=${fakeDb.lastDateLast} (día $lastDay)")
        assertEquals(getDateToEpochMillis(1, month, year), fakeDb.lastDateInit)
        assertEquals(getDateToEpochMillis(lastDay, month, year), fakeDb.lastDateLast)

        // 3. Todos los registros mock caen dentro del mes anterior.
        val allInMonth = registrations.all { it.dateEnter.month() == month && it.dateEnter.yearPart() == year }
        println("✓ ${registrations.size} registros, todos en el mes anterior = $allInMonth")
        assertTrue(allInMonth)

        // 4. Agregaciones del reporte sobre los datos del mes anterior.
        val values = fakeExcel.capturedUpdates.map { it.value }
        println("✓ Celdas calculadas: ${fakeExcel.capturedUpdates.size} | total llegadas=6 presente=${values.contains(6)} | total pernoctaciones=4 presente=${values.contains(4)}")
        assertTrue("Se esperaban celdas calculadas", fakeExcel.capturedUpdates.isNotEmpty())
        assertTrue("Total de llegadas esperado = 6", values.contains(6))
        assertTrue("Total de pernoctaciones esperado = 4", values.contains(4))
    }

    private fun String.month(): Int = split("/")[1].toInt()
    private fun String.yearPart(): Int = split("/")[2].toInt()
}
