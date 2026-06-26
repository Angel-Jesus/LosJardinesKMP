package com.pe.losjardines.utils.files

data class ExcelCellUpdate(
    val sheet: String = "REPORTE MENSUAL",  // Nombre de la hoja, ej: "REPORTE MENSUAL"
    val row: Int,       // Fila en base 0 → fila 28 en Excel = índice 27
    val col: Int,       // Columna en base 0 → columna D en Excel = índice 3
    val value: Any      // Valor a insertar: puede ser Double, Int, String o Boolean
)
