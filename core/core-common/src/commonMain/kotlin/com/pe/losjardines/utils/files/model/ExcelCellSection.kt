package com.pe.losjardines.utils.files.model

data class ExcelCellSection(
    val chapterII: List<AccommodationExcelMap> = accommodationSection,
    val chapterIII: List<DailyArrivalExcelMap> = dailyArrivalSection,
    val chapterIV: ResidenceExcelMap = ResidenceExcelMap(peruResidenceSection, foreignResidenceSection),
    val chapterV: List<TravelReasonExcelMap> = travelReasonSection
)
