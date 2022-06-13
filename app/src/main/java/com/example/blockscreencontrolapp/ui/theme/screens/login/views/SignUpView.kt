package com.example.blockscreencontrolapp.ui.theme.screens.login.views

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.blockscreencontrolapp.R
import com.example.blockscreencontrolapp.ui.theme.AppTheme
import com.example.blockscreencontrolapp.ui.theme.components.BlockScreenButton
import com.example.blockscreencontrolapp.ui.theme.components.TextInput
import com.example.blockscreencontrolapp.ui.theme.screens.login.models.LoginViewState

@Composable
fun SignUpView(
    viewState: LoginViewState,
    onPasswordChange: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordDuplicateChange: (String) -> Unit,
    onFullNameChanged: (String) -> Unit,
    onSignUpClicked: () -> Unit,

) {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(16.dp))

        TextInput(
            header = stringResource(id = R.string.full_name_tint),
            textFieldValue = viewState.fullNameValue,
            onTextFieldChange = onFullNameChanged,
        )
        Spacer(modifier = Modifier.height(32.dp))


        TextInput(
            header = stringResource(id = R.string.email_hint),
            textFieldValue = viewState.emailValue,
            onTextFieldChange = onEmailChanged,
        )

        Spacer(modifier = Modifier.height(32.dp))

        TextInput(
            header = stringResource(id = R.string.password_hint),
            textFieldValue = viewState.passwordValue,
            onTextFieldChange = onPasswordChange
        )

        Spacer(modifier = Modifier.height(32.dp))

        TextInput(
            header = stringResource(id = R.string.repeat_password_hint),
            textFieldValue = viewState.passwordDuplicateValue,
            onTextFieldChange = onPasswordDuplicateChange
        )

        Spacer(modifier = Modifier.height(8.dp))

        BlockScreenButton(
            buttonText = stringResource(id = R.string.button_sign_up_text),
            modifier = Modifier.padding(24.dp),
        ) {
            when {
                viewState.emailValue.isEmpty() -> {
                    Toast.makeText(
                        context,
                        "Email is empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                viewState.passwordValue.length < 6 -> {
                    Toast.makeText(
                        context,
                        "Password too short",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                viewState.passwordDuplicateValue != viewState.passwordValue -> {
                    Toast.makeText(
                        context,
                        "Passwords are different",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> onSignUpClicked()
            }
        }

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp)
                .height(56.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = AppTheme.colors.form),
        ) {
            Row() {
                Image(
                    painter = painterResource(id = R.drawable.ic_google_logo),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(id = R.string.sign_up_google_text),
                    style = TextStyle(color = AppTheme.colors.mainText)
                )
            }
        }

    }
}