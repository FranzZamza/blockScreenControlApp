package com.example.blockscreencontrolapp.ui.theme.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blockscreencontrolapp.ui.theme.AppTheme

@Composable
fun BlockScreenTextField(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    isVisibleText: Boolean = true,
    onTextValueChange: (String) -> Unit
) {
    TextField(
        modifier = modifier.height(56.dp),
        value = value,
        onValueChange = onTextValueChange,
        placeholder = {
            Text(
                text = placeholder,
                style = TextStyle(
                    color = AppTheme.colors.mainText,
                    fontSize = 12.sp
                )
            )
        },
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = AppTheme.colors.outline,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        visualTransformation = if (!isVisibleText) PasswordVisualTransformation()
        else VisualTransformation.None
    )
}