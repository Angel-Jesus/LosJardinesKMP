package com.pe.losjardines.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pe.losjardines.values.AppTheme
import com.pe.losjardines.values.AppTypography
import com.pe.losjardines.values.BorderUnavailableRoom
import com.pe.losjardines.values.DividerGrayColor
import com.pe.losjardines.values.LocalAppTypographyCore
import losjardineskmp.feature.ui.generated.resources.Res
import losjardineskmp.feature.ui.generated.resources.ic_room
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ImageDescriptionItem(
    modifier: Modifier = Modifier,
    name: String,
    room: String,
    days: String,
    date: String,
    onClick: () -> Unit,
    typography: AppTypography = LocalAppTypographyCore.current
){
    Column {
        Row(
            modifier = modifier.clickable(onClick = onClick).padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.wrapContentHeight(),
                painter = painterResource(Res.drawable.ic_room),
                contentDescription = null
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    style = typography.titleMedium
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Habitación $room - $days días",
                    style = typography.descriptionMedium
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "Check-in: $date",
                    style = typography.descriptionMedium,
                    color = BorderUnavailableRoom
                )
            }

            Icon(
                modifier = Modifier.size(32.dp),
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = DividerGrayColor
            )

        }

        Spacer(modifier = Modifier.height(4.dp))

        HorizontalDivider(
            modifier = modifier,
            color = DividerGrayColor
        )
    }
}

@Preview
@Composable
private fun ImageDescriptionItemPreview(){
    AppTheme {
        Box(modifier = Modifier.background(Color.White)){
            ImageDescriptionItem(
                name = "Carlos Mendoza",
                room = "105",
                days = "3",
                date = "20 Mayo 2026",
                onClick = {}
            )
        }
    }
}