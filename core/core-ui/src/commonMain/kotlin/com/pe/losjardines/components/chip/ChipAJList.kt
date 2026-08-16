package com.pe.losjardines.components.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pe.losjardines.components.chip.model.ChipAJColors
import com.pe.losjardines.components.chip.model.ChipAJDefaults
import com.pe.losjardines.values.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

data class ChipAJItem(
    val text: String,
    val style: ChipAJColors,
    val enabled: Boolean = true
)

@Composable
fun ChipAJList(
    modifier: Modifier = Modifier,
    chips: List<ChipAJItem>,
    selectedChip: ChipAJItem? = null,
    onChipSelected: (ChipAJItem) -> Unit
){
    LazyRow(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
        items(items = chips, key = { it.text }) { chip ->
            ChipAJ(
                text = chip.text,
                style = chip.style,
                enabled = chip.enabled,
                isSelected = chip == selectedChip,
                onSelected = { onChipSelected(chip) }
            )
        }
    }
}

@Composable
private fun sampleChips(): List<ChipAJItem> = listOf(
    ChipAJItem(text = "Check-in", style = ChipAJDefaults.default()),
    ChipAJItem(text = "Occupied", style = ChipAJDefaults.default()),
    ChipAJItem(text = "Canceled", style = ChipAJDefaults.default())
)

@Preview
@Composable
private fun ChipAJListDefaultPreview() {
    AppTheme {
        Box(modifier = Modifier.background(Color.White).padding(16.dp)) {
            ChipAJList(
                chips = sampleChips(),
                selectedChip = null,
                onChipSelected = {}
            )
        }
    }
}

@Preview
@Composable
private fun ChipAJListSelectedPreview() {
    AppTheme {
        Box(modifier = Modifier.background(Color.White).padding(16.dp)) {
            ChipAJList(
                chips = sampleChips(),
                selectedChip = sampleChips()[1],
                onChipSelected = {}
            )
        }
    }
}

@Preview
@Composable
private fun ChipAJListWithDisabledPreview() {
    AppTheme {
        Box(modifier = Modifier.background(Color.White).padding(16.dp)) {
            ChipAJList(
                chips = listOf(
                    ChipAJItem(text = "Check-in", style = ChipAJDefaults.default()),
                    ChipAJItem(text = "Occupied", style = ChipAJDefaults.default(), enabled = false),
                    ChipAJItem(text = "Canceled", style = ChipAJDefaults.default())
                ),
                selectedChip = sampleChips().first(),
                onChipSelected = {}
            )
        }
    }
}