package com.pe.losjardines.usecases.content

import com.pe.losjardines.usecases.model.RegistrationDto
import com.pe.losjardines.utils.files.model.ForeignCountry
import com.pe.losjardines.utils.files.model.GuestCategory
import com.pe.losjardines.utils.files.model.PeruRegion
import com.pe.losjardines.utils.files.model.RoomType
import com.pe.losjardines.utils.files.model.TravelReason
import java.time.LocalDate

/**
 * Genera datos de prueba que cubren TODOS los capítulos del reporte mensual:
 *  - Cap II  (Alojamiento): los 3 tipos de habitación.
 *  - Cap III (Llegadas por día): del día 1 al último día del mes anterior.
 *  - Cap IV  (Residencia): todos los países extranjeros y todas las regiones del Perú.
 *  - Cap V   (Motivo de viaje): todos los motivos, en extranjeros y peruanos.
 *
 * A cada país/región se le asigna un valor único (= índice + 1) usado como
 * llegadas (1 + acompañantes) y como noches (pernoctaciones), lo que permite
 * validar el ruteo exacto de los datos del Cap IV. El tipo de habitación, el
 * motivo de viaje y el día se rotan por registro para poblar los demás capítulos.
 *
 * El builder calcula además los agregados esperados de cada capítulo.
 */
object ReportMockData {

    // Nombres EXACTOS que ForeignCountry.getCountry(...) reconoce.
    private val FOREIGN_NAMES = listOf(
        "Argentina", "Alemania", "Bielorrusia", "Bolivia", "Brasil", "Canadá",
        "Colombia", "Corea del Sur", "Costa Rica", "Chile", "China (Rep. Popular)",
        "Ecuador", "Estados Unidos", "España", "Francia", "Países Bajos", "India",
        "Israel", "Italia", "Japón", "México", "Panamá", "Reino Unido", "Rusia",
        "Suiza", "Turquía", "Uruguay", "Venezuela",
        "África (Ghana, Marruecos, Sudáfrica…)", "Oceanía (Australia…)",
        "Otro país de América", "Otro país de Asia", "Otro país de Europa"
    )

    // Nombres EXACTOS que PeruRegion.getRegion(...) reconoce.
    private val PERU_NAMES = listOf(
        "Lima Metropolitana y Callao", "Región Lima", "Amazonas", "Áncash",
        "Apurímac", "Arequipa", "Ayacucho", "Cajamarca", "Cusco", "Huancavelica",
        "Huánuco", "Ica", "Junín", "La Libertad", "Lambayeque", "Loreto",
        "Madre de Dios", "Moquegua", "Pasco", "Piura", "Puno", "San Martín",
        "Tacna", "Tumbes", "Ucayali"
    )

    // Strings que RoomType.getRoomType(...) reconoce (Cap II).
    private val ROOM_TYPES = listOf("Simple", "Matrimonial", "Triple")

    // Strings que TravelReason.getTravelReason(...) reconoce; el último cae en OTHER (Cap V).
    private val REASON_NAMES = listOf(
        "Vacaciones, recreo u ocio",
        "Visitas a familiares y amigos",
        "Educación y formación",
        "Salud y atención médica",
        "Religión o peregrinaciones",
        "Compras (excepto para reventa)",
        "Negocios y motivos profesionales",
        "Trabajo remunerado en lugar de alojamiento",
        "Otro motivo"
    )

    data class RoomMetrics(val arrivals: Int, val occupiedRooms: Int, val overnights: Int)

    data class Result(
        val registrations: List<RegistrationDto>,
        // Cap IV
        val foreignValue: Map<ForeignCountry, Int>,
        val peruValue: Map<PeruRegion, Int>,
        val foreignTotal: Int,
        val peruTotal: Int,
        val generalTotal: Int,
        // Cap II
        val accommodation: Map<RoomType, RoomMetrics>,
        // Cap III
        val arrivalsByDay: Map<Int, Int>,
        val arrivalsTotal: Int,
        // Cap V (llegadas por categoría y motivo)
        val travelReason: Map<GuestCategory, Map<TravelReason, Int>>
    )

    fun fullCoverage(year: Int, month: Int, lastDay: Int): Result {
        val registrations = mutableListOf<RegistrationDto>()
        val foreignValue = LinkedHashMap<ForeignCountry, Int>()
        val peruValue = LinkedHashMap<PeruRegion, Int>()

        // Acumuladores de los agregados esperados.
        val accArrivals = HashMap<RoomType, Int>()
        val accRooms = HashMap<RoomType, Int>()
        val accOvernights = HashMap<RoomType, Int>()
        val byDay = HashMap<Int, Int>()
        val reasonForeign = HashMap<TravelReason, Int>()
        val reasonPeru = HashMap<TravelReason, Int>()
        val reasonTotal = HashMap<TravelReason, Int>()

        var globalIndex = 0

        fun addRegistration(country: String, region: String, value: Int, category: GuestCategory) {
            val idx = globalIndex++
            val roomStr = ROOM_TYPES[idx % ROOM_TYPES.size]
            val reasonStr = REASON_NAMES[idx % REASON_NAMES.size]
            val day = (idx % lastDay) + 1            // cubre del día 1 al último día

            val enter = LocalDate.of(year, month, day)
            val exit = enter.plusDays(value.toLong()) // noches = value

            registrations += RegistrationDto(
                collection = "registrations",
                country = country,
                dateEnter = enter.toReportDate(),
                dateExit = exit.toReportDate(),
                fee = "120",
                name = "Huésped $idx",
                sex = if (idx % 2 == 0) "M" else "F",
                typeDocument = "DNI",
                numberDocument = "${10_000_000 + idx}",
                reasonTravel = reasonStr,
                region = region,
                typeRoom = roomStr,
                room = "$day",
                companions = List(value - 1) { "acompañante$it" } // llegadas = 1 + acompañantes = value
            )

            // Cap II
            val roomType = RoomType.getRoomType(roomStr)
            accArrivals[roomType] = (accArrivals[roomType] ?: 0) + value
            accRooms[roomType] = (accRooms[roomType] ?: 0) + 1
            accOvernights[roomType] = (accOvernights[roomType] ?: 0) + value
            // Cap III
            byDay[day] = (byDay[day] ?: 0) + value
            // Cap V
            val reason = TravelReason.getTravelReason(reasonStr)
            reasonTotal[reason] = (reasonTotal[reason] ?: 0) + value
            val byCategory = if (category == GuestCategory.FOREIGNERS) reasonForeign else reasonPeru
            byCategory[reason] = (byCategory[reason] ?: 0) + value
        }

        FOREIGN_NAMES.forEachIndexed { i, name ->
            val value = i + 1
            addRegistration(country = name, region = "", value = value, category = GuestCategory.FOREIGNERS)
            foreignValue[ForeignCountry.getCountry(name)!!] = value
        }

        PERU_NAMES.forEachIndexed { j, name ->
            val value = j + 1
            addRegistration(country = "Perú", region = name, value = value, category = GuestCategory.PERUVIANS)
            peruValue[PeruRegion.getRegion(name)!!] = value
        }

        val foreignTotal = foreignValue.values.sum()
        val peruTotal = peruValue.values.sum()
        val accommodation = accArrivals.keys.associateWith { rt ->
            RoomMetrics(accArrivals[rt] ?: 0, accRooms[rt] ?: 0, accOvernights[rt] ?: 0)
        }

        return Result(
            registrations = registrations,
            foreignValue = foreignValue,
            peruValue = peruValue,
            foreignTotal = foreignTotal,
            peruTotal = peruTotal,
            generalTotal = foreignTotal + peruTotal,
            accommodation = accommodation,
            arrivalsByDay = byDay,
            arrivalsTotal = foreignTotal + peruTotal,
            travelReason = mapOf(
                GuestCategory.FOREIGNERS to reasonForeign,
                GuestCategory.PERUVIANS to reasonPeru,
                GuestCategory.TOTAL to reasonTotal
            )
        )
    }

    // Formato d/M/yyyy esperado por las utilidades de fecha (sin ceros a la izquierda).
    private fun LocalDate.toReportDate(): String = "$dayOfMonth/$monthValue/$year"
}
