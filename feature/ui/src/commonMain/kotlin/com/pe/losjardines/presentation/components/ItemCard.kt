package com.pe.losjardines.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.pe.losjardines.values.AppTheme
import com.pe.losjardines.values.AppTypography
import com.pe.losjardines.values.BackgroundBrandLightColor
import com.pe.losjardines.values.DividerGrayColor
import com.pe.losjardines.values.LocalAppTypographyCore
import com.pe.losjardines.values.SidebarBorderDark
import com.pe.losjardines.values.SoftTextColor
import losjardineskmp.feature.ui.generated.resources.Res
import losjardineskmp.feature.ui.generated.resources.user_icon
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ItemCard(
    modifier: Modifier = Modifier,
    icon: DrawableResource? = null,
    text: String,
    value: String,
    typography: AppTypography = LocalAppTypographyCore.current
){

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .border(BorderStroke(1.dp, SidebarBorderDark), RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            val modifier = if(icon == null) Modifier else Modifier.weight(1f)

            Text(
                modifier = modifier,
                text = text,
                style = typography.bottomNavDefault,
                color = SoftTextColor
            )

            icon?.let {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(icon),
                    contentDescription = null
                )
            }
        }

        Text(
            text = value,
            style = typography.headerMedium
        )
    }
}

@Preview
@Composable
private fun ItemCardPreview(){
    AppTheme{
        Box(modifier = Modifier.background(Color.White)){
            ItemCard(
                modifier = Modifier.width(150.dp),
                text = "Huespedes",
                value = "40",
                icon = Res.drawable.user_icon
            )
        }
    }
}