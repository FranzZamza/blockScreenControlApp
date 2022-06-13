package com.example.blockscreencontrolapp.ui.theme.screens.create.models


sealed class CreateAction {
    object OpenRoom: CreateAction()
    object None : CreateAction()
}

data class CreateViewState(
    val roomName: String = "",
    val roomPassword: String = "",
    val createAction: CreateAction = CreateAction.None

)
