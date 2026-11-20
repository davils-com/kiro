/*
 * Copyright 2026 Davils
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.davils.kiro.ui.components.skeleton

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.withInfiniteAnimationFrameMillis
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.icons.lucide.Image
import com.davils.kiro.ui.icons.lucide.Lucide
import kotlin.math.abs
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

private const val DIMMED_ALPHA: Float = 0.5f
private const val SHIMMER_ALPHA: Float = 0.6f
private const val SHIMMER_WIDTH: Float = 0.6f
private const val HALF: Float = 0.5f

@Composable
public fun Skeleton(
    modifier: Modifier = Modifier,
    shape: SkeletonShape = SkeletonShape.Rounded,
    style: SkeletonStyle = skeletonStyle(),
) {
    val outline = outlineOf(shape = shape, style = style)
    val animation = style.animation.takeIf { style.isAnimated } ?: SkeletonAnimation.None
    val phase = phaseOf(period = periodOf(animation = animation, style = style))
    Box(
        modifier = modifier
            .then(pulsing(animation = animation, phase = phase, style = style))
            .surface(shape = outline, color = style.color)
            .clip(outline)
            .then(shimmering(animation = animation, phase = phase, style = style)),
    )
}

internal fun outlineOf(shape: SkeletonShape, style: SkeletonStyle): Shape = when (shape) {
    SkeletonShape.Rectangle -> RectangleShape
    SkeletonShape.Rounded -> RoundedCornerShape(style.radius)
    SkeletonShape.Pill -> CircleShape
    SkeletonShape.Circle -> CircleShape
}

@Composable
private fun phaseOf(period: Duration?): State<Float> = produceState(initialValue = 0f, period) {
    period?.let { length -> tick(period = length) { phase -> value = phase } }
}

private suspend fun tick(period: Duration, onPhase: (Float) -> Unit) {
    val millis = period.inWholeMilliseconds.coerceAtLeast(1L)
    while (true) {
        withInfiniteAnimationFrameMillis { time -> onPhase((time % millis).toFloat() / millis) }
    }
}

private fun periodOf(animation: SkeletonAnimation, style: SkeletonStyle): Duration? = when (animation) {
    SkeletonAnimation.Pulse -> style.pulseDuration
    SkeletonAnimation.Shimmer -> style.shimmerDuration
    SkeletonAnimation.None -> null
}

private fun pulsing(animation: SkeletonAnimation, phase: State<Float>, style: SkeletonStyle): Modifier {
    if (animation != SkeletonAnimation.Pulse) return Modifier
    return Modifier.graphicsLayer {
        val wave = 1f - abs(phase.value - HALF) * 2f
        alpha = 1f - (1f - style.dimmedAlpha) * style.pulseEasing.transform(wave)
    }
}

private fun shimmering(animation: SkeletonAnimation, phase: State<Float>, style: SkeletonStyle): Modifier {
    if (animation != SkeletonAnimation.Shimmer) return Modifier
    return Modifier.drawWithCache {
        val band = size.width * style.shimmerWidth
        val colors = listOf(Color.Transparent, style.shimmerColor, Color.Transparent)
        onDrawWithContent {
            drawContent()
            val start = -band + (size.width + band * 2f) * phase.value
            drawRect(brush = Brush.horizontalGradient(colors = colors, startX = start, endX = start + band))
        }
    }
}

internal fun defaultSkeletonStyle(theme: Theme): SkeletonStyle = SkeletonStyle(
    radius = theme.radii.md,
    color = theme.colors.muted,
    animation = SkeletonAnimation.Pulse,
    dimmedAlpha = DIMMED_ALPHA,
    pulseDuration = 2.seconds,
    pulseEasing = CubicBezierEasing(a = 0.4f, b = 0f, c = 0.6f, d = 1f),
    shimmerColor = theme.colors.background.copy(alpha = SHIMMER_ALPHA),
    shimmerDuration = 1.5.seconds,
    shimmerWidth = SHIMMER_WIDTH,
    lineHeight = 14.dp,
    lineGap = 8.dp,
    avatarSize = 40.dp,
    buttonSize = DpSize(width = 96.dp, height = 36.dp),
    imageIcon = Lucide.Image,
    imageIconSize = 32.dp,
    gap = 12.dp,
    isAnimated = !theme.motion.isReduced,
)
