package com.pe.losjardines.core.components.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.pe.losjardines.core.values.BackgroundBrandColor
import com.pe.losjardines.core.values.DefaultTextColor
import com.pe.losjardines.core.values.LocalAppTypography

@Composable
fun EndingButton(
    modifier: Modifier = Modifier,
    text: String,
    leadingIcon: ImageVector? = null,
    style: TextStyle = LocalAppTypography.current.titleLarge,
    onClick: () -> Unit
){
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = BackgroundBrandColor,
            contentColor = Color.White
        )
    ){
        Row(modifier = Modifier.padding(vertical = 4.dp), horizontalArrangement = Arrangement.Center) {
            leadingIcon?.let {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = Color.White
                )

                Spacer(modifier = Modifier.width(8.dp))
            }

            Text(
                modifier = Modifier
                    .height(24.dp)
                    .wrapContentHeight(align = Alignment.CenterVertically),
                text = text,
                style = style,
                color = DefaultTextColor,
            )
        }
    }
}