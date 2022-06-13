package com.example.blockscreencontrolapp.ui.theme.screens.main.models


sealed class MainAction {
    object OpenRoom : MainAction()
    object None : MainAction()
}

data class MainViewState(
    val roomName: String = "",
    val roomPassword: String = "",
    val mainAction: MainAction = MainAction.None
)
