package com.pe.losjardines.core.components.button

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pe.losjardines.core.values.BackgroundBrandColor
import com.pe.losjardines.core.values.DefaultTextColor
import com.pe.losjardines.core.values.LocalAppTypography

@Composable
fun EndingButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
){
    val typography = LocalAppTypography.current

    Button(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = BackgroundBrandColor,
            contentColor = Color.White
        )
    ){
        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            text = text,
            style = typography.titleLarge,
            color = DefaultTextColor
        )
    }
}