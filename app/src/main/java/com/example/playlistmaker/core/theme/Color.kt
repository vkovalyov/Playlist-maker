package com.example.playlistmaker.core.theme

import androidx.compose.ui.graphics.Color

val Black = Color(color = 0xff1A1B22)
val White = Color(0xFFFFFFFF)
val Grey = Color(0xffAEAFB4)
val LightGrey = Color(0xffE6E8EB)
val BlueLight = Color(0xff9FBBF3)
val Blue = Color(0xff3772E7)

data class TextFieldColor(
    val focusedContainerColor: Color,
    val unfocusedContainerColor: Color,
    val disabledContainerColor: Color,
    val cursorBrush: Color,
)

data class SwitcherColor(
    val thumbColor: Color,
    val trackColor: Color,
)

data class AppColors(
    val textPrimary: Color,
    val textSecondary: Color,
    val textButtonPrimary: Color,
    val textButtonSecondary: Color,
    val icon: Color,
    val background: Color,
    val secondaryButtonColor: Color,
    val secondaryTextButtonColor: Color,
    val textFieldIcon: Color,
    val texField: TextFieldColor,
    val hintText: Color,
    val switcher: SwitcherColor
    // val switch
)

val LightAppColors = AppColors(
    textPrimary = Black,
    textSecondary = Grey,
    textButtonPrimary = Color(0xFFFFFFFF),
    textButtonSecondary = Color(0xFF2D2E33),
    icon = Grey,
    background = White,
    secondaryButtonColor = Black,
    secondaryTextButtonColor = White,
    textFieldIcon = Grey,
    texField = TextFieldColor(
        focusedContainerColor = LightGrey,
        unfocusedContainerColor = LightGrey,
        disabledContainerColor = LightGrey,
        cursorBrush = Blue
    ),
    hintText = Grey,
    switcher = SwitcherColor(thumbColor= Grey, trackColor= LightGrey)
)

val DarkAppColors = AppColors(
    textPrimary = White,
    textSecondary = White,
    textButtonPrimary = Color(0xFF2D2E33),
    textButtonSecondary = Color(0xFF),
    icon = White,
    background = Black,
    secondaryButtonColor = White,
    secondaryTextButtonColor = Black,
    textFieldIcon = Black,
    texField = TextFieldColor(
        focusedContainerColor = White,
        unfocusedContainerColor = White,
        disabledContainerColor = White,
        cursorBrush = Blue
    ),
    hintText = Black,
    switcher = SwitcherColor(thumbColor= Blue, trackColor= BlueLight)
)

