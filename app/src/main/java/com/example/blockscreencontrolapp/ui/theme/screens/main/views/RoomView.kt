package com.example.blockscreencontrolapp.ui.theme.screens.main.views

import androidx.compose.runtime.*

@Composable
fun RoomView(
    onChangeStatusScreen: () -> Unit,
    onRoomScreenDestroy:()->Unit ,
) {
    LaunchedEffect(key1 = Unit, block = {
        onChangeStatusScreen()
    })
    DisposableEffect(key1 = {}) {
        onDispose(onRoomScreenDestroy)
    }
}
