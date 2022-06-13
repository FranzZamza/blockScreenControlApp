package com.example.blockscreencontrolapp.ui.theme.screens.main.views
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.blockscreencontrolapp.R
import com.example.blockscreencontrolapp.ui.theme.AppTheme
import com.example.blockscreencontrolapp.ui.theme.components.BlockScreenButton
import com.example.blockscreencontrolapp.ui.theme.components.TextInput
import com.example.blockscreencontrolapp.ui.theme.screens.main.models.MainViewState
@Composable
fun FindRoomView(
    viewState: State<MainViewState>,
    onRoomNameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    clickOnFindButton: () -> Unit
) {
    val context = LocalContext.current
    Column(
        Modifier
            .background(AppTheme.colors.form)
            .fillMaxSize(),
    ) {
        TextInput(
            header = stringResource(id = R.string.room_name_tint),
            textFieldValue = viewState.value.roomName,
            onTextFieldChange = onRoomNameChanged
        )
        Spacer(modifier = Modifier.height(24.dp))
        TextInput(
            header = stringResource(id = R.string.room_password_title),
            textFieldValue = viewState.value.roomPassword,
            onTextFieldChange = onPasswordChanged
        )
        Spacer(modifier = Modifier.height(24.dp))
        Box(
            Modifier
                .fillMaxSize()
                .padding(bottom = 78.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            BlockScreenButton(buttonText = stringResource(id = R.string.find_button),
                modifier = Modifier.padding(bottom = 10.dp),
                onClickAction = {
                    when {
                        viewState.value.roomName.isEmpty() -> Toast.makeText(
                            context,
                            "Room name is empty",
                            Toast.LENGTH_LONG
                        ).show()
                        viewState.value.roomPassword.isEmpty() -> Toast.makeText(
                            context,
                            "Room password is empty",
                            Toast.LENGTH_LONG
                        ).show()
                        else -> {
                            clickOnFindButton()
                        }
                    }
                })
        }
    }
}

