package com.pe.losjardines.core.components.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.pe.losjardines.core.values.DefaultTextColor
import com.pe.losjardines.core.values.LocalAppTypography

@Composable
fun TextTitle(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = DefaultTextColor
){
    val typography = LocalAppTypography.current
    Text(
        modifier = modifier,
        text = text,
        style = typography.titleLarge,
        color = textColor
    )
}