package com.pe.losjardines.utils.files.model

data class AccommodationExcelMap(
    val roomType: RoomType,
    val cells: Map<AccommodationColumn, CellPosition>
)

data class DailyArrivalExcelMap(
    val day: DailyArrivalDay,
    val cell: CellPosition
)

data class ResidenceExcelMap(
    val peruResidence: List<PeruResidenceExcelMap>,
    val foreignResidence: List<ForeignResidenceExcelMap>
)

data class ForeignResidenceExcelMap(
    val country: ForeignCountry,
    val cells: Map<ResidenceMetric, CellPosition>
)

data class PeruResidenceExcelMap(
    val region: PeruRegion,
    val cells: Map<ResidenceMetric, CellPosition>
)

data class TravelReasonExcelMap(
    val guestCategory: GuestCategory,
    val cells: Map<TravelReason, CellPosition>
)