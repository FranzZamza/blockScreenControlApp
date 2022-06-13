package com.example.blockscreencontrolapp.ui.theme.screens.login.models

sealed class LoginEvent {
    object ClickOnLoginButton : LoginEvent()
    object ClickOnSignUpButton : LoginEvent()
    object ActionClicked : LoginEvent()
    object RememberMeClicked : LoginEvent()
    object ForgetClicked : LoginEvent()
    data class FullNameChanged(val value: String) : LoginEvent()
    data class EmailChanged(val value: String) : LoginEvent()
    data class PasswordChanged(val value: String) : LoginEvent()
    data class PasswordDuplicateChanged(val value: String) : LoginEvent()
}
