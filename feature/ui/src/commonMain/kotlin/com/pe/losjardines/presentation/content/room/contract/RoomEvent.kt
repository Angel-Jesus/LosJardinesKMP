package com.pe.losjardines.presentation.content.room.contract

import com.pe.losjardines.base.ui.BaseEvent
import com.pe.losjardines.usecases.model.RoomDto

sealed interface RoomEvent: BaseEvent {
    data object GetRoomState: RoomEvent
    data class UpdateStateRoom(val room: RoomDto): RoomEvent
}