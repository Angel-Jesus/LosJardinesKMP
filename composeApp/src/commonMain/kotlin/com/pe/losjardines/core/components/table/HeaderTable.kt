package com.pe.losjardines.core.components.table

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pe.losjardines.core.values.AppTypography
import com.pe.losjardines.core.values.BackgroundBrandColor
import com.pe.losjardines.core.values.DefaultTextColor

@Composable
fun HeaderTable(
    modifier: Modifier = Modifier,
    headerTitle: List<String>,
    headerWidth: List<Int>,
    scrollRow: ScrollState,
    typography: AppTypography
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(end = 16.dp, start = 16.dp, top = 16.dp)
            .horizontalScroll(scrollRow),
        horizontalArrangement = Arrangement.Center

    ) {
        headerTitle.forEachIndexed { index, title ->
            Text(
                text = title,
                modifier = Modifier
                    .width((headerWidth[index]).dp)
                    .height(50.dp)
                    .border(1.dp, Color.White)
                    .background(color = BackgroundBrandColor)
                    .wrapContentHeight(align = Alignment.CenterVertically),
                color = DefaultTextColor,
                style = typography.titleMedium,
                textAlign = TextAlign.Center,
            )
        }
    }
}