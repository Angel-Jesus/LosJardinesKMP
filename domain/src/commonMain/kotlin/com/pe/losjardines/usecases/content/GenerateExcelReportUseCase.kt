package com.pe.losjardines.usecases.content

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.usecases.model.FilterValues
import com.pe.losjardines.usecases.model.RegistrationDto
import com.pe.losjardines.utils.calculateNights
import com.pe.losjardines.utils.files.ExcelCellUpdate
import com.pe.losjardines.utils.files.ExcelEditor
import com.pe.losjardines.utils.files.PlatformFile
import com.pe.losjardines.utils.files.PlatformInputStream
import com.pe.losjardines.utils.files.model.AccommodationColumn
import com.pe.losjardines.utils.files.model.AccommodationExcelMap
import com.pe.losjardines.utils.files.model.DailyArrivalDay
import com.pe.losjardines.utils.files.model.DailyArrivalExcelMap
import com.pe.losjardines.utils.files.model.ExcelCellSection
import com.pe.losjardines.utils.files.model.ForeignCountry
import com.pe.losjardines.utils.files.model.ForeignCountry.Companion.getCountry
import com.pe.losjardines.utils.files.model.GuestCategory
import com.pe.losjardines.utils.files.model.PeruRegion
import com.pe.losjardines.utils.files.model.PeruRegion.Companion.getRegion
import com.pe.losjardines.utils.files.model.ResidenceExcelMap
import com.pe.losjardines.utils.files.model.ResidenceMetric
import com.pe.losjardines.utils.files.model.RoomType.Companion.getRoomType
import com.pe.losjardines.utils.files.model.TravelReason
import com.pe.losjardines.utils.files.model.TravelReason.Companion.getTravelReason
import com.pe.losjardines.utils.files.model.TravelReasonExcelMap
import com.pe.losjardines.utils.toDayOfMonth
import kotlin.coroutines.cancellation.CancellationException

class GenerateExcelReportUseCase(
    private val getClientsRegisterUseCase: GetClientsRegisterUseCase,
    private val excelEditor: ExcelEditor
) {
    data class Params(
        val templateStream: PlatformInputStream,
        val outputFile: PlatformFile,
        val filter: FilterValues? = null
    )

    @Throws(Exception::class, CancellationException::class, Failure::class)
    suspend fun run(params: Params): PlatformFile {
        val registrations = getClientsRegisterUseCase.run(params.filter)
        val sections = ExcelCellSection()

        val updates = getChapterII(sections.chapterII, registrations) +
            getChapterIII(sections.chapterIII, registrations) +
            getChapterIV(sections.chapterIV, registrations) +
            getChapterV(sections.chapterV, registrations)

        return when (val result = excelEditor.generarDesdeTemplate(params.templateStream, params.outputFile, updates)) {
            is Either.Success -> result.data
            is Either.Error -> throw result.error
        }
    }

    private fun getChapterII(
        chapterII: List<AccommodationExcelMap>,
        registrations: List<RegistrationDto>
    ): List<ExcelCellUpdate> {
        val updates = mutableListOf<ExcelCellUpdate>()

        for (map in chapterII) {
            val roomRegistrations = registrations.filter { getRoomType(it.typeRoom) == map.roomType }

            val arrivals = roomRegistrations.sumOf { 1 + it.companions.size }
            val occupiedRooms = roomRegistrations.size
            val overnights = roomRegistrations.sumOf { calculateNights(it.dateEnter, it.dateExit) }

            map.cells[AccommodationColumn.ARRIVALS]?.let { cell ->
                updates.add(ExcelCellUpdate(row = cell.row, col = cell.column, value = arrivals))
            }
            map.cells[AccommodationColumn.OCCUPIED_ROOMS]?.let { cell ->
                updates.add(ExcelCellUpdate(row = cell.row, col = cell.column, value = occupiedRooms))
            }
            map.cells[AccommodationColumn.OVERNIGHTS]?.let { cell ->
                updates.add(ExcelCellUpdate(row = cell.row, col = cell.column, value = overnights))
            }
        }

        return updates
    }

    private fun getChapterIII(
        chapterIII: List<DailyArrivalExcelMap>,
        registrations: List<RegistrationDto>
    ): List<ExcelCellUpdate> {
        val updates = mutableListOf<ExcelCellUpdate>()

        val arrivalsByDay = registrations.groupBy { it.dateEnter.toDayOfMonth() }

        for (map in chapterIII) {
            val count = if (map.day == DailyArrivalDay.TOTAL) {
                registrations.sumOf { 1 + it.companions.size }
            } else {
                arrivalsByDay[map.day.day]?.sumOf { 1 + it.companions.size } ?: 0
            }

            updates.add(ExcelCellUpdate(row = map.cell.row, col = map.cell.column, value = count))
        }

        return updates
    }

    private fun getChapterIV(
        chapterIV: ResidenceExcelMap,
        registrations: List<RegistrationDto>
    ): List<ExcelCellUpdate> {
        val updates = mutableListOf<ExcelCellUpdate>()

        val foreignRegistrations = registrations.filter { it.country.trim() != "Perú" }
        val peruRegistrations = registrations.filter { it.country.trim() == "Perú" }

        // Foreign residence
        val foreignByCountry = foreignRegistrations.groupBy { getCountry(it.country) }

        for (map in chapterIV.foreignResidence) {
            val arrivals:   Int
            val overnights: Int

            if (map.country == ForeignCountry.TOTAL) {
                arrivals   = foreignRegistrations.sumOf { 1 + it.companions.size }
                overnights = foreignRegistrations.sumOf { calculateNights(it.dateEnter, it.dateExit) }
            } else {
                val group = foreignByCountry[map.country] ?: emptyList()
                arrivals   = group.sumOf { 1 + it.companions.size }
                overnights = group.sumOf { calculateNights(it.dateEnter, it.dateExit) }
            }

            map.cells[ResidenceMetric.ARRIVALS]?.let { cell ->
                updates.add(ExcelCellUpdate(row = cell.row, col = cell.column, value = arrivals))
            }
            map.cells[ResidenceMetric.OVERNIGHTS]?.let { cell ->
                updates.add(ExcelCellUpdate(row = cell.row, col = cell.column, value = overnights))
            }
        }

        // Peru residence
        val peruByRegion = peruRegistrations.groupBy { getRegion(it.region) }

        for (map in chapterIV.peruResidence) {
            val arrivals:   Int
            val overnights: Int

            when (map.region) {
                PeruRegion.TOTAL_RESIDENTS -> {
                    arrivals   = peruRegistrations.sumOf { 1 + it.companions.size }
                    overnights = peruRegistrations.sumOf { calculateNights(it.dateEnter, it.dateExit) }
                }
                PeruRegion.TOTAL_GENERAL -> {
                    arrivals   = registrations.sumOf { 1 + it.companions.size }
                    overnights = registrations.sumOf { calculateNights(it.dateEnter, it.dateExit) }
                }
                else -> {
                    val group = peruByRegion[map.region] ?: emptyList()
                    arrivals   = group.sumOf { 1 + it.companions.size }
                    overnights = group.sumOf { calculateNights(it.dateEnter, it.dateExit) }
                }
            }

            map.cells[ResidenceMetric.ARRIVALS]?.let { cell ->
                updates.add(ExcelCellUpdate(row = cell.row, col = cell.column, value = arrivals))
            }
            map.cells[ResidenceMetric.OVERNIGHTS]?.let { cell ->
                updates.add(ExcelCellUpdate(row = cell.row, col = cell.column, value = overnights))
            }
        }

        return updates
    }

    private fun getChapterV(
        chapterV: List<TravelReasonExcelMap>,
        registrations: List<RegistrationDto>
    ): List<ExcelCellUpdate> {
        val updates = mutableListOf<ExcelCellUpdate>()

        val foreignRegistrations = registrations.filter { it.country.trim() != "Perú" }
        val peruRegistrations    = registrations.filter { it.country.trim() == "Perú" }

        for (map in chapterV) {
            val categoryRegs = when (map.guestCategory) {
                GuestCategory.FOREIGNERS -> foreignRegistrations
                GuestCategory.PERUVIANS  -> peruRegistrations
                GuestCategory.TOTAL      -> registrations
            }

            val byReason = categoryRegs.groupBy { getTravelReason(it.reasonTravel) }

            for ((reason, cell) in map.cells) {
                val count = when (reason) {
                    TravelReason.TOTAL_ARRIVALS -> categoryRegs.sumOf { 1 + it.companions.size }
                    else -> byReason[reason]?.sumOf { 1 + it.companions.size } ?: 0
                }
                updates.add(ExcelCellUpdate(row = cell.row, col = cell.column, value = count))
            }
        }

        return updates
    }
}