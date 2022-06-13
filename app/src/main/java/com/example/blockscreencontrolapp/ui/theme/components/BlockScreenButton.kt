package com.example.blockscreencontrolapp.ui.theme.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.blockscreencontrolapp.ui.theme.AppTheme


@Composable
fun BlockScreenButton(
    buttonText: String,
    modifier: Modifier = Modifier,
    onClickAction: () -> Unit,
) {
        Button(
            onClick = onClickAction,
            modifier
                .height(56.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = AppTheme.colors.primary)
        ) {
            Text(
                text = buttonText,
                color = AppTheme.colors.white
            )
        }
}