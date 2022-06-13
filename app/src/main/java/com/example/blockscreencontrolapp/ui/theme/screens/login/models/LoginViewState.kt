package com.example.blockscreencontrolapp.ui.theme.screens.login.models

enum class LoginSubState {
    SignIn, SignUp, Forgot
}

sealed class LoginAction {
    object OpenMainScreen : LoginAction()
    object None : LoginAction()
}

data class LoginViewState(
    val loginSubState: LoginSubState = LoginSubState.SignIn,
    val emailValue: String = "",
    val passwordValue: String = "",
    val passwordDuplicateValue: String = "",
    val fullNameValue: String = "",
    val rememberMeChecked: Boolean = true,
    val isLoginProcess: Boolean = false,
    val loginAction: LoginAction = LoginAction.None
)