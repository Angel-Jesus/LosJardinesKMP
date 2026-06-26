package com.pe.losjardines.values

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import losjardineskmp.core.core_ui.generated.resources.Inter_Bold
import losjardineskmp.core.core_ui.generated.resources.Inter_Light
import losjardineskmp.core.core_ui.generated.resources.Inter_Medium
import losjardineskmp.core.core_ui.generated.resources.Inter_Regular
import losjardineskmp.core.core_ui.generated.resources.Inter_SemiBold
import losjardineskmp.core.core_ui.generated.resources.Res
import org.jetbrains.compose.resources.Font

val LocalAppTypographyCore = staticCompositionLocalOf<AppTypography> {
    error("No Typography provided")
}
data class AppTypography(
    // 24sp - Bold
    val headerLarge: TextStyle,
    // 22sp - SemiBold
    val headerMedium: TextStyle,
    // 20sp - SemiBold
    val headerSmall: TextStyle,
    // 20sp - SemiBold
    val titleLarge: TextStyle,
    // 18sp - Medium
    val titleMedium: TextStyle,
    // 16sp - Medium
    val titleSmall: TextStyle,
    // 24sp - Bold
    val titleBrand: TextStyle,
    // 16sp - Regular
    val bodyLarge: TextStyle,
    // 14sp - Regular
    val bodyMedium: TextStyle,
    // 12sp - Medium
    val bodySmall: TextStyle,
    // 16sp - SemiBold
    val buttonLarge: TextStyle,
    // 14sp - Medium
    val buttonMedium: TextStyle,
    // 12sp - Medium
    val labelSmall: TextStyle,
    // 14sp - Medium
    val labelMedium: TextStyle,
    // 12sp - Medium
    val bottomNavDefault: TextStyle,
    // 12sp - SemiBold
    val bottomNavSelected: TextStyle,
    // 12sp - Light
    val descriptionMedium: TextStyle
)

@Composable
fun appFontFamily(): FontFamily{
    return FontFamily(
        Font(Res.font.Inter_Light, FontWeight.Light),
        Font(Res.font.Inter_Regular, FontWeight.Normal),
        Font(Res.font.Inter_Medium, FontWeight.Medium),
        Font(Res.font.Inter_SemiBold, FontWeight.SemiBold),
        Font(Res.font.Inter_Bold, FontWeight.Bold)
    )
}

@Composable
fun appTypographyCore(): AppTypography {

    val appFontFamily = appFontFamily()

    return AppTypography(
        // 24sp - Bold
        headerLarge = TextStyle(
            fontFamily = appFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        ),
        // 22sp - SemiBold
        headerMedium = TextStyle(
            fontFamily = appFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp
        ),
        // 20sp - SemiBold
        headerSmall = TextStyle(
            fontFamily = appFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp
        ),
        // 20sp - SemiBold
        titleLarge = TextStyle(
            fontFamily = appFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp
        ),
        // 18sp - Medium
        titleMedium = TextStyle(
            fontFamily = appFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp
        ),
        // 16sp - Medium
        titleSmall = TextStyle(
            fontFamily = appFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        ),
        // 24sp - Bold
        titleBrand = TextStyle(
            fontFamily = appFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = BrandTextColor
        ),
        // 16sp - Regular
        bodyLarge = TextStyle(
            fontFamily = appFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        // 14sp - Regular
        bodyMedium = TextStyle(
            fontFamily = appFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        ),
        // 12sp - Medium
        bodySmall = TextStyle(
            fontFamily = appFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp
        ),
        // 16sp - SemiBold
        buttonLarge = TextStyle(
            fontFamily = appFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        ),
        // 14sp - Medium
        buttonMedium = TextStyle(
            fontFamily = appFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        ),
        // 12sp - Medium
        labelSmall = TextStyle(
            fontFamily = appFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp
        ),
        // 14sp - Medium
        labelMedium = TextStyle(
            fontFamily = appFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        ),
        // 12sp - Medium
        bottomNavDefault = TextStyle(
            fontFamily = appFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp
        ),
        // 12sp - SemiBold
        bottomNavSelected = TextStyle(
            fontFamily = appFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp
        ),
        descriptionMedium = TextStyle(
            fontFamily = appFontFamily,
            fontWeight = FontWeight.Light,
            fontSize = 12.sp
        )
    )
}