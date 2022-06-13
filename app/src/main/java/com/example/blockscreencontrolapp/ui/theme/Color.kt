package com.example.blockscreencontrolapp.ui.theme

import androidx.compose.ui.graphics.Color

data class Colors(
    val white:Color,
    val primary:Color,
    val secondary:Color,
    val mainText:Color,
    val secondaryText:Color,
    val outline: Color,
    val form:Color,
    val actionText:Color
)

val lightPalette=Colors(
    white = Color.White,
    primary = Color(0xFF1FCC79),
    secondary = Color(0xFFFF6464),
    mainText = Color(0xFF2E3E5C),
    secondaryText = Color(0xFF9FA5C0),
    outline = Color(0xFFD0DBEA),
    form = Color(0xFFF4F5F7),
    actionText = Color(0xFF3F76DD),
)
