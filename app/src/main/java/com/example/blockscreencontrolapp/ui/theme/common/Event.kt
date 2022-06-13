package com.example.blockscreencontrolapp.ui.theme.common

interface EventHandler<E> {
    fun obtainEvent(event:E)
}