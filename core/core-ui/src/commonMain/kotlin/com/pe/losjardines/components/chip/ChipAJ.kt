package com.pe.losjardines.components.chip

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import com.pe.losjardines.values.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.pe.losjardines.components.chip.model.ChipAJColors
import com.pe.losjardines.components.chip.model.ChipAJDefaults
import com.pe.losjardines.values.LocalAppTypographyCore
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun ChipAJ(
    modifier: Modifier = Modifier,
    text: String,
    leadingIcon: ImageVector? = null,
    isSelected: Boolean = false,
    onSelected: () -> Unit = {},
    style: ChipAJColors = ChipAJDefaults.default(),
    enabled: Boolean = true
){
    val chipColor = when{
        !enabled -> style.disabled
        isSelected -> style.selected
        else -> style.normal
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .border(BorderStroke(1.dp, chipColor.borderColor), RoundedCornerShape(14.dp))
            .background(chipColor.containerColor)
            .clickable(
                enabled = enabled,
                onClick = onSelected
            ),
        contentAlignment = Alignment.Center
    ){
        Row(modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp), horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically){
            leadingIcon?.let {
                Icon(
                    modifier = Modifier.size(18.dp),
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = chipColor.contentColor
                )
            }

            Text(
                text = text,
                color = chipColor.contentColor,
                style = LocalAppTypographyCore.current.labelSmall
            )
        }
    }
}

@Preview
@Composable
private fun ChipAJNormalPreview() {
    AppTheme {
        Box(modifier = Modifier.background(Color.White).padding(16.dp)) {
            ChipAJ(text = "Check-in")
        }
    }
}

@Preview
@Composable
private fun ChipAJSelectedPreview() {
    AppTheme {
        Box(modifier = Modifier.background(Color.White).padding(16.dp)) {
            ChipAJ(text = "Check-in", isSelected = true)
        }
    }
}

@Preview
@Composable
private fun ChipAJDisabledPreview() {
    AppTheme {
        Box(modifier = Modifier.background(Color.White).padding(16.dp)) {
            ChipAJ(text = "Check-in", enabled = false)
        }
    }
}

@Preview
@Composable
private fun ChipAJWithIconPreview() {
    AppTheme {
        Box(modifier = Modifier.background(Color.White).padding(16.dp)) {
            ChipAJ(text = "Check-in", leadingIcon = Icons.Default.Check)
        }
    }
}

@Preview
@Composable
private fun ChipAJAllStatesPreview() {
    AppTheme {
        Column(
            modifier = Modifier.background(Color.White).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ChipAJ(text = "Normal")
            ChipAJ(text = "Seleccionado", isSelected = true)
            ChipAJ(text = "Deshabilitado", enabled = false)
            ChipAJ(text = "Con ícono", leadingIcon = Icons.Default.Check)
        }
    }
}
