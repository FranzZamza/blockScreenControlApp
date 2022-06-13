package com.example.blockscreencontrolapp.ui.theme.screens.login.views

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
fun SignInView(
    viewState: LoginViewState,
    onPasswordChange: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onRememberMeChange: (Boolean) -> Unit,
    onForgetClicked: () -> Unit,
    onSignInClicked: () -> Unit
) {
    val context = LocalContext.current
    Column()
    {
        Spacer(modifier = Modifier.height(32.dp))

        TextInput(
            modifier = Modifier.fillMaxWidth(),
            header = stringResource(id = R.string.email_hint),
            textFieldValue = viewState.emailValue,
            onTextFieldChange = onEmailChanged,
            isVisibleText = true
        )

        Spacer(modifier = Modifier.height(32.dp))


        TextInput(
            modifier = Modifier.fillMaxWidth(),
            header = stringResource(id = R.string.password_hint),
            textFieldValue = viewState.passwordValue,
            onTextFieldChange = onPasswordChange,
            isVisibleText = false,

            )

        Spacer(modifier = Modifier.height(40.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = viewState.rememberMeChecked,
                onCheckedChange = onRememberMeChange,
                colors = CheckboxDefaults.colors(checkedColor = AppTheme.colors.primary)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = stringResource(id = R.string.remember_me),
                style = TextStyle(color = AppTheme.colors.secondaryText)
            )

            Text(
                text = stringResource(id = R.string.forgot_text),
                style = TextStyle(color = AppTheme.colors.secondaryText),
                modifier = Modifier
                    .padding(start = 100.dp)
                    .clickable {
                        onForgetClicked()
                    }
            )
        }

        BlockScreenButton(
            buttonText = stringResource(id = R.string.button_login_text),
            modifier = Modifier.padding(24.dp)
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
                        "Password is not correct",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> onSignInClicked()
            }
        }

        Button(
            onClick = {},
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp, top = 24.dp)
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
                    text = stringResource(id = R.string.log_in_google_text),
                    style = TextStyle(color = AppTheme.colors.mainText)
                )
            }
        }

    }
}