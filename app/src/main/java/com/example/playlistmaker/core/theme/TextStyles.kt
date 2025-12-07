package com.example.playlistmaker.core.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

enum class AppTextStyle {
    SettingMenuItem,
    AppBarTitle,
    TabText,
    TrackListTitle,
    TrackListDescription,
    WarningText,
    Button,
    PlayListTitle,
    SearchHistoryTitle,
    HintText

}

@Composable
fun AppTextStyle.toTextStyle(): TextStyle {
    val colors = LocalAppColors.current
    val sizes = LocalAppTextSizes.current

    return when (this) {
        AppTextStyle.SettingMenuItem -> TextStyle(
            color = colors.textPrimary,
            fontSize = sizes.regular,
            fontFamily = YsDisplayRegular
        )
        AppTextStyle.AppBarTitle -> TextStyle(
            color = colors.textPrimary,
            fontSize = sizes.medium,
            fontFamily = YsDisplayRegular
        )
        AppTextStyle.TabText -> TextStyle(
            color = colors.textPrimary,
            fontSize = sizes.regularSys,
            fontFamily = YsDisplayRegular,
            fontWeight = FontWeight.W700
        )

        AppTextStyle.TrackListTitle -> TextStyle(
            color = colors.textPrimary,
            fontSize = sizes.regular,
            fontFamily = YsDisplayRegular,
        )
        AppTextStyle.TrackListDescription -> TextStyle(
            color = colors.textSecondary,
            fontSize = sizes.regularYs,
            fontFamily = YsDisplayRegular,
        )
        AppTextStyle.WarningText -> TextStyle(
            color = colors.textPrimary,
            fontSize = sizes.mediumYs,
            fontFamily = YsDisplayMedium,
        )
        AppTextStyle.Button -> TextStyle(
            color = colors.secondaryTextButtonColor,
            fontSize = sizes.regularSys,
            fontFamily = YsDisplayMedium,
        )
        AppTextStyle.PlayListTitle -> TextStyle(
            color = colors.textPrimary,
            fontSize = sizes.regularS,
            fontFamily = YsDisplayRegular,
        )
        AppTextStyle.SearchHistoryTitle -> TextStyle(
            color = colors.textPrimary,
            fontSize = sizes.mediumYs,
            fontWeight = FontWeight.W700,
            fontFamily = YsDisplayRegular,
        )
        AppTextStyle.HintText -> TextStyle(
            color = colors.hintText,
            fontSize = sizes.regular,
            fontFamily = YsDisplayRegular,
        )

    }
}