package com.pe.losjardines.presentation.content.room.contract

import com.pe.losjardines.base.ui.BaseUiState
import com.pe.losjardines.usecases.model.RoomDto

data class RoomState(
    val loading: Boolean = false,
    val rooms: List<RoomDto> = emptyList()
): BaseUiState
