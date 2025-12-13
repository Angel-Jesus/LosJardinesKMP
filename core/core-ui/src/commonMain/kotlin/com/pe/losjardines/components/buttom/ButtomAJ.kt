package com.pe.losjardines.components.buttom

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pe.losjardines.values.AppTypography
import com.pe.losjardines.values.BackgroundBrandColor
import com.pe.losjardines.values.LocalAppTypographyCore

@Composable
fun ButtomAJ(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    onClick: () -> Unit,
    colors: ButtonColors = ButtonDefaults.buttonColors(containerColor = BackgroundBrandColor),
    typography: AppTypography = LocalAppTypographyCore.current
){
    Button(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
        colors = colors,
        shape = RoundedCornerShape(8.dp)
    ){
        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            text = text,
            style = typography.titleMedium
        )
    }
}