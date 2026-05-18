package com.pe.losjardines.components.loading

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.pe.losjardines.values.BackgroundBrandColor
import com.pe.losjardines.values.BackgroundOverlay
import losjardineskmp.core.core_ui.generated.resources.Res
import losjardineskmp.core.core_ui.generated.resources.logoaj
import org.jetbrains.compose.resources.painterResource

/**
 * Pantalla de carga semi-transparente con animación circular y logo central.
 *
 * @param title      Texto principal bajo el círculo (ej: "Préparation...")
 * @param subtitle   Texto secundario (ej: "Calcul de vos résultats")
 */
@Composable
fun LoadingAJ(
    title: String = "Preparación...",
    subtitle: String = "Calculo de resultado"
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundOverlay)
            .zIndex(1f),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedCircleWithLogo(size = 180.dp) {
                Image(
                    modifier = Modifier.size(100.dp),
                    painter = painterResource(Res.drawable.logoaj),
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = title,
                color = BackgroundBrandColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = subtitle,
                color = Color(0xFF888888),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun AnimatedCircleWithLogo(
    size: Dp,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "loading")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1400, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    val sweepAngle by infiniteTransition.animateFloat(
        initialValue = 80f,
        targetValue = 300f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "sweep"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(size)
    ) {
        Canvas(modifier = Modifier.size(size)) {
            val strokeWidth = 6.dp.toPx()
            val padding = strokeWidth / 2
            rotate(degrees = rotation) {
                drawArc(
                    color = BackgroundBrandColor,
                    startAngle = -90f,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    topLeft = Offset(padding, padding),
                    size = Size(
                        width = this.size.width - strokeWidth,
                        height = this.size.height - strokeWidth
                    ),
                    style = Stroke(
                        width = strokeWidth,
                        cap = StrokeCap.Round
                    )
                )
            }
        }

        Box(
            modifier = Modifier
                .size(size - 2.dp)
                .clip(CircleShape)
                .border(
                    width = 4.dp,
                    color = BackgroundBrandColor.copy(alpha = 0.4f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}