package com.example.blockscreencontrolapp.ui.theme.screens.create.models

import com.example.blockscreencontrolapp.ui.theme.common.Guest


sealed class CreateRoomEvent {
    data class OnChangeRoomName(val value: String) : CreateRoomEvent()
    data class OnChangeRoomPassword(val value: String) : CreateRoomEvent()
    object ClickCreateButton : CreateRoomEvent()
    object OnChangeData : CreateRoomEvent()
}


