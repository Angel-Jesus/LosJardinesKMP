package com.pe.losjardines.utils.files.model

val accommodationSection = listOf(
    AccommodationExcelMap(
        roomType = RoomType.SINGLE,
        cells = mapOf(
            AccommodationColumn.ROOM_WITH_BATH to CellPosition(27, 3),
            AccommodationColumn.BEDS to CellPosition(27, 7),
            AccommodationColumn.ARRIVALS to CellPosition(27, 9),
            AccommodationColumn.OCCUPIED_ROOMS to CellPosition(27, 11),
            AccommodationColumn.OVERNIGHTS to CellPosition(27, 13)
        )
    ),
    AccommodationExcelMap(
        roomType = RoomType.DOUBLE,
        cells = mapOf(
            AccommodationColumn.ROOM_WITH_BATH to CellPosition(28, 3),
            AccommodationColumn.BEDS to CellPosition(28, 7),
            AccommodationColumn.ARRIVALS to CellPosition(28, 9),
            AccommodationColumn.OCCUPIED_ROOMS to CellPosition(28, 11),
            AccommodationColumn.OVERNIGHTS to CellPosition(28, 13)
        )
    ),
    AccommodationExcelMap(
        roomType = RoomType.TRIPLE,
        cells = mapOf(
            AccommodationColumn.ROOM_WITH_BATH to CellPosition(30, 3),
            AccommodationColumn.BEDS to CellPosition(30, 7),
            AccommodationColumn.ARRIVALS to CellPosition(30, 9),
            AccommodationColumn.OCCUPIED_ROOMS to CellPosition(30, 11),
            AccommodationColumn.OVERNIGHTS to CellPosition(30, 13)
        )
    )
)
val dailyArrivalSection = listOf(

    DailyArrivalExcelMap(
        day = DailyArrivalDay.DAY_1,
        cell = CellPosition(row = 39, column = 2) // D40
    ),

    DailyArrivalExcelMap(
        day = DailyArrivalDay.DAY_2,
        cell = CellPosition(row = 39, column = 4) // F40
    ),

    DailyArrivalExcelMap(
        day = DailyArrivalDay.DAY_3,
        cell = CellPosition(row = 39, column = 6) // H40
    ),

    DailyArrivalExcelMap(
        day = DailyArrivalDay.DAY_4,
        cell = CellPosition(row = 39, column = 8) // J40
    ),

    DailyArrivalExcelMap(
        day = DailyArrivalDay.DAY_5,
        cell = CellPosition(row = 39, column = 10) // L40
    ),

    DailyArrivalExcelMap(
        day = DailyArrivalDay.DAY_6,
        cell = CellPosition(row = 39, column = 12) // N40
    ),

    DailyArrivalExcelMap(
        day = DailyArrivalDay.DAY_7,
        cell = CellPosition(row = 39, column = 14) // P40
    ),

    DailyArrivalExcelMap(
        day = DailyArrivalDay.DAY_8,
        cell = CellPosition(row = 39, column = 16) // R40
    ),

    DailyArrivalExcelMap(
        day = DailyArrivalDay.DAY_9,
        cell = CellPosition(row = 40, column = 2) // D41
    ),

    DailyArrivalExcelMap(
        day = DailyArrivalDay.DAY_10,
        cell = CellPosition(row = 40, column = 4) // F41
    ),

    DailyArrivalExcelMap(
        day = DailyArrivalDay.DAY_11,
        cell = CellPosition(row = 40, column = 6) // H41
    ),

    DailyArrivalExcelMap(
        day = DailyArrivalDay.DAY_12,
        cell = CellPosition(row = 40, column = 8) // J41
    ),

    DailyArrivalExcelMap(
        day = DailyArrivalDay.DAY_13,
        cell = CellPosition(row = 40, column = 10) // L41
    ),

    DailyArrivalExcelMap(
        day = DailyArrivalDay.DAY_14,
        cell = CellPosition(row = 40, column = 12) // N41
    ),

    DailyArrivalExcelMap(
        day = DailyArrivalDay.DAY_15,
        cell = CellPosition(row = 40, column = 14) // P41
    ),

    DailyArrivalExcelMap(
        day = DailyArrivalDay.DAY_16,
        cell = CellPosition(row = 40, column = 16) // R41
    ),

    DailyArrivalExcelMap(
        day = DailyArrivalDay.DAY_17,
        cell = CellPosition(row = 41, column = 2) // D42
    ),

    DailyArrivalExcelMap(
        day = DailyArrivalDay.DAY_18,
        cell = CellPosition(row = 41, column = 4) // F42
    ),

    DailyArrivalExcelMap(
        day = DailyArrivalDay.DAY_19,
        cell = CellPosition(row = 41, column = 6) // H42
    ),

    DailyArrivalExcelMap(
        day = DailyArrivalDay.DAY_20,
        cell = CellPosition(row = 41, column = 8) // J42
    ),

    DailyArrivalExcelMap(
        day = DailyArrivalDay.DAY_21,
        cell = CellPosition(row = 41, column = 10) // L42
    ),

    DailyArrivalExcelMap(
        day = DailyArrivalDay.DAY_22,
        cell = CellPosition(row = 41, column = 12) // N42
    ),

    DailyArrivalExcelMap(
        day = DailyArrivalDay.DAY_23,
        cell = CellPosition(row = 41, column = 14) // P42
    ),

    DailyArrivalExcelMap(
        day = DailyArrivalDay.DAY_24,
        cell = CellPosition(row = 41, column = 16) // R42
    ),

    DailyArrivalExcelMap(
        day = DailyArrivalDay.DAY_25,
        cell = CellPosition(row = 42, column = 2) // D43
    ),

    DailyArrivalExcelMap(
        day = DailyArrivalDay.DAY_26,
        cell = CellPosition(row = 42, column = 4) // F43
    ),

    DailyArrivalExcelMap(
        day = DailyArrivalDay.DAY_27,
        cell = CellPosition(row = 42, column = 6) // H43
    ),

    DailyArrivalExcelMap(
        day = DailyArrivalDay.DAY_28,
        cell = CellPosition(row = 42, column = 8) // J43
    ),

    DailyArrivalExcelMap(
        day = DailyArrivalDay.DAY_29,
        cell = CellPosition(row = 42, column = 10) // L43
    ),

    DailyArrivalExcelMap(
        day = DailyArrivalDay.DAY_30,
        cell = CellPosition(row = 42, column = 12) // N43
    ),

    DailyArrivalExcelMap(
        day = DailyArrivalDay.DAY_31,
        cell = CellPosition(row = 42, column = 14) // P43
    ),

    DailyArrivalExcelMap(
        day = DailyArrivalDay.TOTAL,
        cell = CellPosition(row = 42, column = 16) // S43
    )
)
val foreignResidenceSection = listOf(
    ForeignResidenceExcelMap(
        country = ForeignCountry.ARGENTINA,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(48, 4),      // E49
            ResidenceMetric.OVERNIGHTS to CellPosition(48, 6)     // G49
        )
    ),

    ForeignResidenceExcelMap(
        country = ForeignCountry.GERMANY,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(49, 4),
            ResidenceMetric.OVERNIGHTS to CellPosition(49, 6)
        )
    ),

    ForeignResidenceExcelMap(
        country = ForeignCountry.BELARUS,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(50, 4),
            ResidenceMetric.OVERNIGHTS to CellPosition(50, 6)
        )
    ),

    ForeignResidenceExcelMap(
        country = ForeignCountry.BOLIVIA,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(51, 4),
            ResidenceMetric.OVERNIGHTS to CellPosition(51, 6)
        )
    ),

    ForeignResidenceExcelMap(
        country = ForeignCountry.BRAZIL,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(52, 4),
            ResidenceMetric.OVERNIGHTS to CellPosition(52, 6)
        )
    ),

    ForeignResidenceExcelMap(
        country = ForeignCountry.CANADA,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(53, 4),
            ResidenceMetric.OVERNIGHTS to CellPosition(53, 6)
        )
    ),

    ForeignResidenceExcelMap(
        country = ForeignCountry.COLOMBIA,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(54, 4),
            ResidenceMetric.OVERNIGHTS to CellPosition(54, 6)
        )
    ),

    ForeignResidenceExcelMap(
        country = ForeignCountry.SOUTH_KOREA,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(55, 4),
            ResidenceMetric.OVERNIGHTS to CellPosition(55, 6)
        )
    ),

    ForeignResidenceExcelMap(
        country = ForeignCountry.COSTA_RICA,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(56, 4),
            ResidenceMetric.OVERNIGHTS to CellPosition(56, 6)
        )
    ),

    ForeignResidenceExcelMap(
        country = ForeignCountry.CHILE,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(57, 4),
            ResidenceMetric.OVERNIGHTS to CellPosition(57, 6)
        )
    ),

    ForeignResidenceExcelMap(
        country = ForeignCountry.CHINA,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(58, 4),
            ResidenceMetric.OVERNIGHTS to CellPosition(58, 6)
        )
    ),

    ForeignResidenceExcelMap(
        country = ForeignCountry.ECUADOR,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(59, 4),
            ResidenceMetric.OVERNIGHTS to CellPosition(59, 6)
        )
    ),

    ForeignResidenceExcelMap(
        country = ForeignCountry.USA,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(60, 4),
            ResidenceMetric.OVERNIGHTS to CellPosition(60, 6)
        )
    ),

    ForeignResidenceExcelMap(
        country = ForeignCountry.SPAIN,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(61, 4),
            ResidenceMetric.OVERNIGHTS to CellPosition(61, 6)
        )
    ),

    ForeignResidenceExcelMap(
        country = ForeignCountry.FRANCE,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(62, 4),
            ResidenceMetric.OVERNIGHTS to CellPosition(62, 6)
        )
    ),

    ForeignResidenceExcelMap(
        country = ForeignCountry.NETHERLANDS,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(63, 4),
            ResidenceMetric.OVERNIGHTS to CellPosition(63, 6)
        )
    ),

    ForeignResidenceExcelMap(
        country = ForeignCountry.INDIA,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(64, 4),
            ResidenceMetric.OVERNIGHTS to CellPosition(64, 6)
        )
    ),

    ForeignResidenceExcelMap(
        country = ForeignCountry.ISRAEL,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(65, 4),
            ResidenceMetric.OVERNIGHTS to CellPosition(65, 6)
        )
    ),

    ForeignResidenceExcelMap(
        country = ForeignCountry.ITALY,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(66, 4),
            ResidenceMetric.OVERNIGHTS to CellPosition(66, 6)
        )
    ),

    ForeignResidenceExcelMap(
        country = ForeignCountry.JAPAN,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(67, 4),
            ResidenceMetric.OVERNIGHTS to CellPosition(67, 6)
        )
    ),

    ForeignResidenceExcelMap(
        country = ForeignCountry.MEXICO,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(68, 4),
            ResidenceMetric.OVERNIGHTS to CellPosition(68, 6)
        )
    ),

    ForeignResidenceExcelMap(
        country = ForeignCountry.PANAMA,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(69, 4),
            ResidenceMetric.OVERNIGHTS to CellPosition(69, 6)
        )
    ),

    ForeignResidenceExcelMap(
        country = ForeignCountry.UNITED_KINGDOM,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(70, 4),
            ResidenceMetric.OVERNIGHTS to CellPosition(70, 6)
        )
    ),

    ForeignResidenceExcelMap(
        country = ForeignCountry.RUSSIA,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(71, 4),
            ResidenceMetric.OVERNIGHTS to CellPosition(71, 6)
        )
    ),

    ForeignResidenceExcelMap(
        country = ForeignCountry.SWITZERLAND,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(72, 4),
            ResidenceMetric.OVERNIGHTS to CellPosition(72, 6)
        )
    ),

    ForeignResidenceExcelMap(
        country = ForeignCountry.TURKEY,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(73, 4),
            ResidenceMetric.OVERNIGHTS to CellPosition(73, 6)
        )
    ),

    ForeignResidenceExcelMap(
        country = ForeignCountry.URUGUAY,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(74, 4),
            ResidenceMetric.OVERNIGHTS to CellPosition(74, 6)
        )
    ),

    ForeignResidenceExcelMap(
        country = ForeignCountry.VENEZUELA,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(75, 4),
            ResidenceMetric.OVERNIGHTS to CellPosition(75, 6)
        )
    ),

    ForeignResidenceExcelMap(
        country = ForeignCountry.AFRICA,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(76, 4),
            ResidenceMetric.OVERNIGHTS to CellPosition(76, 6)
        )
    ),

    ForeignResidenceExcelMap(
        country = ForeignCountry.OCEANIA,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(77, 4),
            ResidenceMetric.OVERNIGHTS to CellPosition(77, 6)
        )
    ),

    ForeignResidenceExcelMap(
        country = ForeignCountry.OTHER_AMERICA,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(78, 4),
            ResidenceMetric.OVERNIGHTS to CellPosition(78, 6)
        )
    ),

    ForeignResidenceExcelMap(
        country = ForeignCountry.OTHER_ASIA,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(79, 4),
            ResidenceMetric.OVERNIGHTS to CellPosition(79, 6)
        )
    ),

    ForeignResidenceExcelMap(
        country = ForeignCountry.OTHER_EUROPE,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(80, 4),
            ResidenceMetric.OVERNIGHTS to CellPosition(80, 6)
        )
    ),

    ForeignResidenceExcelMap(
        country = ForeignCountry.TOTAL,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(81, 4),
            ResidenceMetric.OVERNIGHTS to CellPosition(81, 6)
        )
    )
)
val peruResidenceSection = listOf(

    PeruResidenceExcelMap(
        region = PeruRegion.LIMA_METROPOLITANA_CALLAO,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(48, 13),      // N50
            ResidenceMetric.OVERNIGHTS to CellPosition(48, 15)     // P50
        )
    ),

    PeruResidenceExcelMap(
        region = PeruRegion.REGION_LIMA,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(50, 13),
            ResidenceMetric.OVERNIGHTS to CellPosition(50, 15)
        )
    ),

    PeruResidenceExcelMap(
        region = PeruRegion.AMAZONAS,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(52, 13),
            ResidenceMetric.OVERNIGHTS to CellPosition(52, 15)
        )
    ),

    PeruResidenceExcelMap(
        region = PeruRegion.ANCASH,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(53, 13),
            ResidenceMetric.OVERNIGHTS to CellPosition(53, 15)
        )
    ),

    PeruResidenceExcelMap(
        region = PeruRegion.APURIMAC,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(54, 13),
            ResidenceMetric.OVERNIGHTS to CellPosition(54, 15)
        )
    ),

    PeruResidenceExcelMap(
        region = PeruRegion.AREQUIPA,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(55, 13),
            ResidenceMetric.OVERNIGHTS to CellPosition(55, 15)
        )
    ),

    PeruResidenceExcelMap(
        region = PeruRegion.AYACUCHO,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(56, 13),
            ResidenceMetric.OVERNIGHTS to CellPosition(56, 15)
        )
    ),

    PeruResidenceExcelMap(
        region = PeruRegion.CAJAMARCA,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(57, 13),
            ResidenceMetric.OVERNIGHTS to CellPosition(57, 15)
        )
    ),

    PeruResidenceExcelMap(
        region = PeruRegion.CUSCO,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(58, 13),
            ResidenceMetric.OVERNIGHTS to CellPosition(58, 15)
        )
    ),

    PeruResidenceExcelMap(
        region = PeruRegion.HUANCAVELICA,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(59, 13),
            ResidenceMetric.OVERNIGHTS to CellPosition(59, 15)
        )
    ),

    PeruResidenceExcelMap(
        region = PeruRegion.HUANUCO,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(60, 13),
            ResidenceMetric.OVERNIGHTS to CellPosition(60, 15)
        )
    ),

    PeruResidenceExcelMap(
        region = PeruRegion.ICA,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(61, 13),
            ResidenceMetric.OVERNIGHTS to CellPosition(61, 15)
        )
    ),

    PeruResidenceExcelMap(
        region = PeruRegion.JUNIN,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(62, 13),
            ResidenceMetric.OVERNIGHTS to CellPosition(62, 15)
        )
    ),

    PeruResidenceExcelMap(
        region = PeruRegion.LA_LIBERTAD,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(63, 13),
            ResidenceMetric.OVERNIGHTS to CellPosition(63, 15)
        )
    ),

    PeruResidenceExcelMap(
        region = PeruRegion.LAMBAYEQUE,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(64, 13),
            ResidenceMetric.OVERNIGHTS to CellPosition(64, 15)
        )
    ),

    PeruResidenceExcelMap(
        region = PeruRegion.LORETO,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(65, 13),
            ResidenceMetric.OVERNIGHTS to CellPosition(65, 15)
        )
    ),

    PeruResidenceExcelMap(
        region = PeruRegion.MADRE_DE_DIOS,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(66, 13),
            ResidenceMetric.OVERNIGHTS to CellPosition(66, 15)
        )
    ),

    PeruResidenceExcelMap(
        region = PeruRegion.MOQUEGUA,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(67, 13),
            ResidenceMetric.OVERNIGHTS to CellPosition(67, 15)
        )
    ),

    PeruResidenceExcelMap(
        region = PeruRegion.PASCO,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(68, 13),
            ResidenceMetric.OVERNIGHTS to CellPosition(68, 15)
        )
    ),

    PeruResidenceExcelMap(
        region = PeruRegion.PIURA,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(69, 13),
            ResidenceMetric.OVERNIGHTS to CellPosition(69, 15)
        )
    ),

    PeruResidenceExcelMap(
        region = PeruRegion.PUNO,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(70, 13),
            ResidenceMetric.OVERNIGHTS to CellPosition(70, 15)
        )
    ),

    PeruResidenceExcelMap(
        region = PeruRegion.SAN_MARTIN,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(71, 13),
            ResidenceMetric.OVERNIGHTS to CellPosition(71, 15)
        )
    ),

    PeruResidenceExcelMap(
        region = PeruRegion.TACNA,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(72, 13),
            ResidenceMetric.OVERNIGHTS to CellPosition(72, 15)
        )
    ),

    PeruResidenceExcelMap(
        region = PeruRegion.TUMBES,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(73, 13),
            ResidenceMetric.OVERNIGHTS to CellPosition(73, 15)
        )
    ),

    PeruResidenceExcelMap(
        region = PeruRegion.UCAYALI,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(74, 13),
            ResidenceMetric.OVERNIGHTS to CellPosition(74, 15)
        )
    ),

    PeruResidenceExcelMap(
        region = PeruRegion.TOTAL_RESIDENTS,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(75, 13),
            ResidenceMetric.OVERNIGHTS to CellPosition(75, 15)
        )
    ),

    PeruResidenceExcelMap(
        region = PeruRegion.TOTAL_GENERAL,
        cells = mapOf(
            ResidenceMetric.ARRIVALS to CellPosition(79, 13),      // N80
            ResidenceMetric.OVERNIGHTS to CellPosition(79, 15)     // P80
        )
    )
)
val travelReasonSection = listOf(
    TravelReasonExcelMap(
        guestCategory = GuestCategory.FOREIGNERS,
        cells = mapOf(

            TravelReason.TOTAL_ARRIVALS to CellPosition(87, 1),          // C88

            TravelReason.VACATION_RECREATION to CellPosition(87, 3),     // E88
            TravelReason.FAMILY_VISIT to CellPosition(87, 5),            // G88
            TravelReason.EDUCATION to CellPosition(87, 7),               // I88
            TravelReason.HEALTH to CellPosition(87, 9),                 // K88
            TravelReason.RELIGION to CellPosition(87, 11),               // M88
            TravelReason.SHOPPING to CellPosition(87, 13),               // O88
            TravelReason.BUSINESS to CellPosition(87, 15),               // Q88
            TravelReason.WORK to CellPosition(87, 17),                   // S88
            TravelReason.OTHER to CellPosition(87, 19)                   // U88
        )
    ),
    TravelReasonExcelMap(
        guestCategory = GuestCategory.PERUVIANS,
        cells = mapOf(

            TravelReason.TOTAL_ARRIVALS to CellPosition(88, 1),          // C89

            TravelReason.VACATION_RECREATION to CellPosition(88, 3),     // E89
            TravelReason.FAMILY_VISIT to CellPosition(88, 5),            // G89
            TravelReason.EDUCATION to CellPosition(88, 7),               // I89
            TravelReason.HEALTH to CellPosition(88, 9),                 // K89
            TravelReason.RELIGION to CellPosition(88, 11),               // M89
            TravelReason.SHOPPING to CellPosition(88, 13),               // O89
            TravelReason.BUSINESS to CellPosition(88, 15),               // Q89
            TravelReason.WORK to CellPosition(88, 17),                   // S89
            TravelReason.OTHER to CellPosition(88, 19)                   // U89
        )
    ),
    TravelReasonExcelMap(
        guestCategory = GuestCategory.TOTAL,
        cells = mapOf(

            TravelReason.TOTAL_ARRIVALS to CellPosition(89, 1),          // C90

            TravelReason.VACATION_RECREATION to CellPosition(89, 3),     // E90
            TravelReason.FAMILY_VISIT to CellPosition(89, 5),            // G90
            TravelReason.EDUCATION to CellPosition(89, 7),               // I90
            TravelReason.HEALTH to CellPosition(89, 9),                 // K90
            TravelReason.RELIGION to CellPosition(89, 11),               // M90
            TravelReason.SHOPPING to CellPosition(89, 13),               // O90
            TravelReason.BUSINESS to CellPosition(89, 15),               // Q90
            TravelReason.WORK to CellPosition(89, 17),                   // S90
            TravelReason.OTHER to CellPosition(89, 19)                   // U90
        )
    )
)