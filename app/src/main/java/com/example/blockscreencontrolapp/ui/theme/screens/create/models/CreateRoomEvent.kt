package com.example.blockscreencontrolapp.ui.theme.screens.create.models


sealed class CreateRoomEvent {
    data class OnChangeRoomName(val value: String) : CreateRoomEvent()
    data class OnChangeRoomPassword(val value: String) : CreateRoomEvent()
    object ClickCreateButton : CreateRoomEvent()
}


