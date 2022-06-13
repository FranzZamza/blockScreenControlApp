package com.example.blockscreencontrolapp.ui.theme.screens.login.views

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.blockscreencontrolapp.R
import com.example.blockscreencontrolapp.ui.theme.components.BlockScreenButton
import com.example.blockscreencontrolapp.ui.theme.components.TextInput
import com.example.blockscreencontrolapp.ui.theme.screens.login.models.LoginViewState

@Composable
fun ForgotView(viewState: LoginViewState, onEmailChanged: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(32.dp))
        TextInput(
            modifier = Modifier.fillMaxWidth(),
            header = stringResource(id = R.string.email_hint),
            textFieldValue = viewState.emailValue,
            onTextFieldChange = onEmailChanged,
            isVisibleText = true
        )
        Spacer(modifier = Modifier.weight(1f))
        BlockScreenButton(
            buttonText = stringResource(id = R.string.button_login_text),
            modifier = Modifier.padding(24.dp)
        ) {}
    }
}