package com.pe.losjardines.presentation.content.room.viewmodel

import com.pe.losjardines.base.ui.BaseViewModel
import com.pe.losjardines.presentation.content.room.contract.RoomEffect
import com.pe.losjardines.presentation.content.room.contract.RoomEvent
import com.pe.losjardines.presentation.content.room.contract.RoomState
import com.pe.losjardines.usecases.content.GetRoomStateUseCase
import com.pe.losjardines.usecases.content.UpdateStateRoomUseCase
import com.pe.losjardines.usecases.model.RoomDto

class RoomViewModel(
    private val getRoomStateUseCase: GetRoomStateUseCase,
    private val updateStateRoomUseCase: UpdateStateRoomUseCase
): BaseViewModel<RoomState, RoomEvent, RoomEffect>(RoomState()) {
    override fun onEvent(event: RoomEvent) {
        when(event){
            is RoomEvent.GetRoomState -> getRoomState()
            is RoomEvent.UpdateStateRoom -> updateStateRoom(event.room)
        }
    }

    private fun updateStateRoom(room: RoomDto) {
        updateState { copy(loading = true) }

        executeUseCase(
            useCase = updateStateRoomUseCase,
            params = UpdateStateRoomUseCase.Params(room),
            onSuccess = {
                val roomUpdated = uiState.value.rooms.map {
                    if(it.room == room.room){
                        room
                    }else{
                        it
                    }
                }
                updateState { copy(rooms = roomUpdated, loading = false) }
            },
            onError = {
                updateState { copy(loading = false) }
                println("Error updateStateRoom: $it")
            }
        )
    }

    private fun getRoomState() {
        updateState { copy(loading = true) }
        executeUseCase(
            useCase = getRoomStateUseCase,
            params = Unit,
            onSuccess = {
                updateState {
                    copy(rooms = it, loading = false)
                }
            },
            onError = {
                updateState { copy(loading = false) }
                println("Error getRoomState: $it")
            }
        )
    }
}