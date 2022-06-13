package com.example.blockscreencontrolapp.ui.theme.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blockscreencontrolapp.ui.theme.common.EventHandler
import com.example.blockscreencontrolapp.ui.theme.screens.login.models.LoginAction
import com.example.blockscreencontrolapp.ui.theme.screens.login.models.LoginEvent
import com.example.blockscreencontrolapp.ui.theme.screens.login.models.LoginSubState
import com.example.blockscreencontrolapp.ui.theme.screens.login.models.LoginViewState
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.Exception

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel(), EventHandler<LoginEvent> {

    private val _viewState = MutableLiveData(LoginViewState())
    val viewState: LiveData<LoginViewState> = _viewState

    private val _viewAction: MutableLiveData<LoginAction> = MutableLiveData(LoginAction.None)
    val viewAction: LiveData<LoginAction> = _viewAction

    override fun obtainEvent(event: LoginEvent) {
        when (event) {
            LoginEvent.ClickOnLoginButton -> userLogin(
                viewState.value!!.emailValue,
                viewState.value!!.passwordValue
            )
            LoginEvent.ClickOnSignUpButton -> createUser(
                viewState.value!!.emailValue,
                viewState.value!!.passwordValue,
                viewState.value!!.fullNameValue
            )
            LoginEvent.ActionClicked -> switchActionState()
            LoginEvent.RememberMeClicked -> rememberMeClickedChange()
            is LoginEvent.EmailChanged -> emailChanged(event.value)
            is LoginEvent.PasswordChanged -> passwordChanged(event.value)
            is LoginEvent.PasswordDuplicateChanged -> passwordDuplicateChanged(event.value)
            is LoginEvent.FullNameChanged -> fullNameChanged(event.value)
            is LoginEvent.ForgetClicked -> forgetClicked()
        }
    }

    private fun userLogin(emailValue: String, passwordValue: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.postValue(_viewState.value?.copy(isLoginProcess = true))
            try {
                _viewState.postValue(
                    _viewState.value?.copy(
                        // isLoginProcess = true,
                        loginAction = LoginAction.OpenMainScreen
                    )
                )

                FirebaseAuth.getInstance().signInWithEmailAndPassword(emailValue, passwordValue)
                    .await()
            } catch (e: Exception) {
                Log.e("loginUser", e.stackTraceToString())
            }
        }
    }

    private fun createUser(
        email: String,
        password: String,
        fullName: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.postValue(_viewState.value?.copy(isLoginProcess = true))
            try {

                val auth = userAuthenticate(email, password)
                createUserAtRealTimeDB(auth, fullName)
                _viewState.postValue(
                    _viewState.value?.copy(
                        // isLoginProcess = true,
                        loginAction = LoginAction.OpenMainScreen
                    )
                )
            } catch (e: Exception) {
                Log.e("createUser", e.stackTraceToString())
            }
        }
    }

    private suspend fun userAuthenticate(email: String, password: String): AuthResult =
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).await()

    private suspend fun createUserAtRealTimeDB(auth: AuthResult, fullName: String) {
        auth.user?.let {
            Firebase.database.reference.child("users").child(it.uid).child("name")
                .setValue(fullName).await()
        }
    }

    private fun fullNameChanged(value: String) {
        _viewState.postValue(_viewState.value?.copy(fullNameValue = value))
    }


    private fun passwordDuplicateChanged(value: String) {
        _viewState.postValue(_viewState.value?.copy(passwordDuplicateValue = value))
    }


    private fun forgetClicked() {
        _viewState.postValue(_viewState.value?.copy(loginSubState = LoginSubState.Forgot))
    }

    private fun passwordChanged(value: String) {
        _viewState.postValue(_viewState.value?.copy(passwordValue = value))
    }

    private fun emailChanged(value: String) {
        _viewState.postValue(_viewState.value?.copy(emailValue = value))
    }

    private fun switchActionState() {
        when (_viewState.value?.loginSubState) {
            LoginSubState.SignIn -> _viewState.postValue(_viewState.value?.copy(loginSubState = LoginSubState.SignUp))
            LoginSubState.SignUp -> _viewState.postValue(_viewState.value?.copy(loginSubState = LoginSubState.SignIn))
            LoginSubState.Forgot -> _viewState.postValue(_viewState.value?.copy(loginSubState = LoginSubState.Forgot))
            else -> {}
        }
    }


    private fun rememberMeClickedChange() =
        _viewState.postValue(_viewState.value?.rememberMeChecked?.let {
            _viewState.value?.copy(rememberMeChecked = !it)
        })


}