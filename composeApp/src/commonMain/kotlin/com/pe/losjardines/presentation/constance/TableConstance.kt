package com.pe.losjardines.presentation.constance

import com.pe.losjardines.presentation.constance.ClientInfoTitle.COST
import com.pe.losjardines.presentation.constance.ClientInfoTitle.DATE
import com.pe.losjardines.presentation.constance.ClientInfoTitle.DNI
import com.pe.losjardines.presentation.constance.ClientInfoTitle.NAME
import com.pe.losjardines.presentation.constance.ClientInfoTitle.N_ROOM
import com.pe.losjardines.presentation.constance.ClientInfoTitle.OBSERVATIONS
import com.pe.losjardines.presentation.constance.ClientInfoTitle.ORIGIN
import com.pe.losjardines.presentation.constance.ClientInfoTitle.TIME

object TableConstance {
    private const val N_ROOM_WIDTH = 150
    private const val NAME_WIDTH = 220
    private const val DNI_WIDTH = 150
    private const val COST_WIDTH = 120
    private const val DATE_WIDTH = 150
    private const val TIME_WIDTH = 150
    private const val ORIGIN_WIDTH = 120
    private const val OBSERVATIONS_WIDTH = 220

    val headerTitleText = listOf(
        N_ROOM,
        NAME,
        DNI,
        COST,
        DATE,
        TIME,
        ORIGIN,
        OBSERVATIONS
    )

    val headerTitleWidth = listOf(
        N_ROOM_WIDTH,
        NAME_WIDTH,
        DNI_WIDTH,
        COST_WIDTH,
        DATE_WIDTH,
        TIME_WIDTH,
        ORIGIN_WIDTH,
        OBSERVATIONS_WIDTH
    )
}