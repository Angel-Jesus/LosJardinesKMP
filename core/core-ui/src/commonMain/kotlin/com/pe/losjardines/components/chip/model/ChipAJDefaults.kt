package com.pe.losjardines.components.chip.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color


@Immutable
data class ChipAJColors(
    val normal: ChipAJColorScheme,
    val selected: ChipAJColorScheme,
    val disabled: ChipAJColorScheme
)

@Immutable
data class ChipAJColorScheme(
    val containerColor: Color,
    val contentColor: Color,
    val borderColor: Color
)

object ChipAJDefaults {
    fun default() = ChipAJColors(
        normal = ChipAJColorScheme(
            containerColor = Color(0xFFE8F5E9),
            contentColor = Color(0xFF2E7D32),
            borderColor = Color(0xFFA5D6A7)
        ),
        selected = ChipAJColorScheme(
            containerColor = Color(0xFF2E7D32),
            contentColor = Color.White,
            borderColor = Color(0xFF2E7D32)
        ),
        disabled = ChipAJColorScheme(
            containerColor = Color(0xFFC8E6C9),
            contentColor = Color(0xFF81C784),
            borderColor = Color(0xFFC8E6C9)
        )
    )

    fun orange() = ChipAJColors(
        normal = ChipAJColorScheme(
            containerColor = Color(0xFFFFF3E0),
            contentColor = Color(0xFFF57C00),
            borderColor = Color(0xFFFFB74D)
        ),
        selected = ChipAJColorScheme(
            containerColor = Color(0xFFF57C00),
            contentColor = Color.White,
            borderColor = Color(0xFFF57C00)
        ),
        disabled = ChipAJColorScheme(
            containerColor = Color(0xFFFFE0B2),
            contentColor = Color(0xFFFFB74D),
            borderColor = Color(0xFFFFE0B2)
        )
    )

    fun red() = ChipAJColors(
        normal = ChipAJColorScheme(
            containerColor = Color(0xFFFFEBEE),
            contentColor = Color(0xFFD32F2F),
            borderColor = Color(0xFFEF9A9A)
        ),
        selected = ChipAJColorScheme(
            containerColor = Color(0xFFD32F2F),
            contentColor = Color.White,
            borderColor = Color(0xFFD32F2F)
        ),
        disabled = ChipAJColorScheme(
            containerColor = Color(0xFFFFCDD2),
            contentColor = Color(0xFFEF9A9A),
            borderColor = Color(0xFFFFCDD2)
        )
    )

}