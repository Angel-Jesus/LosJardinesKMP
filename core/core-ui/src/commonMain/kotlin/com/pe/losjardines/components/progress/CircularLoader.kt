package com.pe.losjardines.components.progress

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Indicador de carga circular animado compuesto por segmentos que giran secuencialmente.
 *
 * @param modifier                Modificador de Compose para controlar el layout externo del componente.
 * @param size                    Tamaño total del loader (ancho y alto). Por defecto 64.dp.
 * @param segmentCount            Número de segmentos que conforman el círculo del loader. Por defecto 12.
 * @param segmentWidth            Ancho de cada segmento individual del loader. Por defecto 6.dp.
 * @param segmentHeight           Alto de cada segmento individual del loader. Por defecto 16.dp.
 * @param color                   Color base de los segmentos. La animación varía su opacidad para simular el giro. Por defecto Color.LightGray.
 * @param animationDurationMillis Duración en milisegundos de un ciclo completo de la animación. Por defecto 1000ms.
 */
@Composable
fun CircularLoader(
    modifier: Modifier = Modifier,
    size: Dp = 64.dp,
    segmentCount: Int = 12,
    segmentWidth: Dp = 6.dp,
    segmentHeight: Dp = 16.dp,
    color: Color = Color.LightGray,
    animationDurationMillis: Int = 1000
) {
    val infiniteTransition = rememberInfiniteTransition()

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = animationDurationMillis,
                easing = LinearEasing
            )
        )
    )

    Canvas(
        modifier = modifier.size(size)
    ) {
        val radius = size.toPx() / 2
        val angleStep = 360f / segmentCount

        repeat(segmentCount) { index ->
            val alpha = (index + 1).toFloat() / segmentCount

            rotate(
                degrees = rotation + index * angleStep,
                pivot = center
            ) {
                drawRoundRect(
                    color = color.copy(alpha = alpha),
                    topLeft = Offset(
                        x = center.x - segmentWidth.toPx() / 2,
                        y = center.y - radius
                    ),
                    size = Size(
                        width = segmentWidth.toPx(),
                        height = segmentHeight.toPx()
                    ),
                    cornerRadius = CornerRadius(
                        x = segmentWidth.toPx() / 2,
                        y = segmentWidth.toPx() / 2
                    )
                )
            }
        }
    }
}