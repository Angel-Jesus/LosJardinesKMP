package com.pe.losjardines.core.values

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import losjardineskmp.composeapp.generated.resources.Body_Regular
import losjardineskmp.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font

data class AppTypography(
    val titleLarge: TextStyle,
    val bodyLarge: TextStyle
)

@Composable
fun AppFontFamily(): FontFamily{
    return FontFamily(Font(Res.font.Body_Regular, FontWeight.Normal))
}

@Composable
fun AppTypography(): AppTypography {
    val appFontFamily = AppFontFamily()
    return AppTypography(
        titleLarge = TextStyle(
            fontFamily = appFontFamily,
            fontWeight = FontWeight.W500,
            fontSize = 18.sp
        ),
        bodyLarge = TextStyle(
            fontFamily = appFontFamily,
            fontWeight = FontWeight.W300,
            fontSize = 14.sp
        )
    )
}