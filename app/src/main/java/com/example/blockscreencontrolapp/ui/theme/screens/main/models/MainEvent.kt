package com.example.blockscreencontrolapp.ui.theme.screens.main.models

sealed class MainEvent {
    data class OnChangeRoomName(val value: String) : MainEvent()
    data class OnChangeRoomPassword(val value: String) : MainEvent()
    object OnChangeScreenStatus: MainEvent()
    object ClickOnConnect: MainEvent()
    object DestroyRoomScreen: MainEvent()
}
