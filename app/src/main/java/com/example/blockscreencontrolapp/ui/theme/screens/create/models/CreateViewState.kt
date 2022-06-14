package com.example.blockscreencontrolapp.ui.theme.screens.create.models

import com.example.blockscreencontrolapp.ui.theme.common.Guest


sealed class CreateAction {
    object OpenRoom: CreateAction()
    object None : CreateAction()
}

data class CreateViewState(
    val guests: List<Guest> = emptyList(),
    val roomName: String = "",
    val roomPassword: String = "",
    val createAction: CreateAction = CreateAction.None,
)
