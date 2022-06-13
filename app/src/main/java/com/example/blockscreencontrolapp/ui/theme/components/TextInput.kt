package com.example.blockscreencontrolapp.ui.theme.components
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.blockscreencontrolapp.ui.theme.AppTheme


@Composable
fun TextInput(
    modifier: Modifier = Modifier,
    header: String,
    textFieldValue: String,
    isVisibleText: Boolean = true,
    onTextFieldChange: (String) -> Unit
) {
    Column(modifier) {
        Text(
            text = header, color = AppTheme.colors.mainText,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(15.dp))

        BlockScreenTextField(
            value = textFieldValue,
            placeholder = "",
            onTextValueChange = onTextFieldChange,
            isVisibleText = isVisibleText,
            modifier = Modifier.fillMaxWidth()
        )
    }
}