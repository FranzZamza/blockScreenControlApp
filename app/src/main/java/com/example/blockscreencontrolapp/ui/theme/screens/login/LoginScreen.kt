package com.example.blockscreencontrolapp.ui.theme.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.blockscreencontrolapp.R
import com.example.blockscreencontrolapp.navigation.NavigationTree
import com.example.blockscreencontrolapp.ui.theme.AppTheme
import com.example.blockscreencontrolapp.ui.theme.screens.login.models.LoginAction
import com.example.blockscreencontrolapp.ui.theme.screens.login.models.LoginEvent
import com.example.blockscreencontrolapp.ui.theme.screens.login.models.LoginSubState
import com.example.blockscreencontrolapp.ui.theme.screens.login.models.LoginViewState
import com.example.blockscreencontrolapp.ui.theme.screens.login.views.ForgotView
import com.example.blockscreencontrolapp.ui.theme.screens.login.views.SignInView
import com.example.blockscreencontrolapp.ui.theme.screens.login.views.SignUpView


@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    navController: NavController
) {
    val viewState = loginViewModel.viewState.observeAsState(LoginViewState())
    Surface(
        modifier = Modifier
            .background(AppTheme.colors.form)
            .fillMaxSize()

    ) {

        with(viewState.value) {
            LazyColumn(contentPadding = PaddingValues(start = 18.dp, end = 16.dp)) {
                item {
                    Text(
                        modifier = Modifier.padding(top = 36.dp),
                        text = when (loginSubState) {
                            LoginSubState.SignIn -> stringResource(R.string.sign_in_title)
                            LoginSubState.SignUp -> stringResource(R.string.sign_up_title)
                            LoginSubState.Forgot -> stringResource(R.string.forgot_title)
                        },
                        style = TextStyle(
                            color = AppTheme.colors.mainText,
                            fontWeight = FontWeight.Bold,
                            fontSize = 50.sp
                        )
                    )
                }
                item {
                    Row() {
                        Text(
                            text = when (loginSubState) {
                                LoginSubState.SignUp -> stringResource(R.string.sign_up_subtitle)
                                LoginSubState.SignIn -> stringResource(R.string.sign_in_subtitle)
                                LoginSubState.Forgot -> stringResource(R.string.forgot_subtitle)
                            },
                            style = TextStyle(
                                color = AppTheme.colors.secondaryText,
                                fontSize = 14.sp
                            )
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        if (loginSubState != LoginSubState.Forgot) {
                            Text(
                                modifier = Modifier.clickable {
                                    loginViewModel.obtainEvent(LoginEvent.ActionClicked)
                                },
                                text = when (loginSubState) {
                                    LoginSubState.SignUp -> stringResource(R.string.sign_up_subtitle_action)
                                    LoginSubState.SignIn -> stringResource(R.string.sign_in_subtitle_action)
                                    else -> ""
                                },
                                style = TextStyle(
                                    color = AppTheme.colors.actionText,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    when (loginSubState) {
                        LoginSubState.SignUp -> SignUpView(
                            viewState = this@with,
                            onEmailChanged = { loginViewModel.obtainEvent(LoginEvent.EmailChanged(it)) },
                            onPasswordChange = {
                                loginViewModel.obtainEvent(
                                    LoginEvent.PasswordChanged(
                                        it
                                    )
                                )
                            },
                            onPasswordDuplicateChange = {
                                loginViewModel.obtainEvent(
                                    LoginEvent.PasswordDuplicateChanged(
                                        it
                                    )
                                )
                            },
                            onFullNameChanged = {
                                loginViewModel.obtainEvent(
                                    LoginEvent.FullNameChanged(
                                        it
                                    )
                                )
                            },
                            onSignUpClicked = {
                                loginViewModel.obtainEvent(
                                    LoginEvent.ClickOnSignUpButton
                                )
                            },
                        )
                        LoginSubState.SignIn -> SignInView(viewState = this@with,
                            onEmailChanged = {
                                loginViewModel.obtainEvent(LoginEvent.EmailChanged(it))
                            },
                            onPasswordChange = {
                                loginViewModel.obtainEvent(LoginEvent.PasswordChanged(it))
                            },
                            onRememberMeChange = {
                                loginViewModel.obtainEvent(LoginEvent.RememberMeClicked)
                            },
                            onForgetClicked = {
                                loginViewModel.obtainEvent(LoginEvent.ForgetClicked)
                            },
                            onSignInClicked = {
                                loginViewModel.obtainEvent(LoginEvent.ClickOnLoginButton)
                            }

                        )
                        LoginSubState.Forgot -> ForgotView(
                            viewState = this@with,
                            onEmailChanged = { loginViewModel.obtainEvent(LoginEvent.EmailChanged(it)) })
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = viewState.value, block = {
        when (viewState.value.loginAction) {
            is LoginAction.OpenMainScreen -> navController.navigate(NavigationTree.MainScreen.name)
            else -> {}
        }
    })
}