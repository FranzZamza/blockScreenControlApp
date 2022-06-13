package com.example.blockscreencontrolapp.ui.theme.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.blockscreencontrolapp.ui.theme.AppTheme

@Composable
fun BlockScreenTitle(id:Int) {
    Text(
        text = stringResource(id = id),
        style = TextStyle(
            color = AppTheme.colors.mainText,
            fontWeight = FontWeight.Bold,
            fontSize = 50.sp
        )
    )
}