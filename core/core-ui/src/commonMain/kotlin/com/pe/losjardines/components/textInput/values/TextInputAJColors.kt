package com.pe.losjardines.components.textInput.values

import androidx.compose.ui.graphics.Color
import com.pe.losjardines.values.BlackTextColor
import com.pe.losjardines.values.BrandIconColor
import com.pe.losjardines.values.FocusedBorderColor
import com.pe.losjardines.values.SoftTextColor

data class TextInputAJColors(
    val labelColor: Color = BlackTextColor,
    val placeholderColor: Color = SoftTextColor,
    val enableColor: Color = Color.Unspecified,
    val disableColor: Color = Color.Unspecified,
    val leadingIconColor: Color = Color.Unspecified,
    val trailingIconColor: Color = BrandIconColor,
    val focusedIndicatorColor: Color = FocusedBorderColor
)
