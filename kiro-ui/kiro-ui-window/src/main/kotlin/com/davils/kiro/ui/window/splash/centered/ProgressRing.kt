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

package com.davils.kiro.ui.window.splash.centered

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.semantics.semantics
import com.davils.kiro.ui.window.theme
import kotlin.time.Duration

private const val FULL_TURN: Float = 360f

@Composable
internal fun ProgressRing(fraction: Float?, isFailed: Boolean, style: CenteredSplashStyle, modifier: Modifier) {
    val isReduced = theme.motion.isReduced
    val sweep = animateFloatAsState(
        targetValue = fraction?.let { value -> value * FULL_TURN } ?: style.ringSweep,
        animationSpec = followingOf(style = style, isReduced = isReduced),
        label = "sweep",
    )
    val turn = turnOf(isTurning = fraction == null && !isReduced && !isFailed, period = style.ringPeriod)
    val color = arcColorOf(isFailed = isFailed, style = style)
    Spacer(
        modifier = modifier
            .semantics { progressBarRangeInfo = rangeOf(fraction) }
            .drawBehind { drawRing(sweep = sweep.value, turn = turn.value, color = color, style = style) },
    )
}

private fun DrawScope.drawRing(sweep: Float, turn: Float, color: Color, style: CenteredSplashStyle) {
    val width = style.ringWidth.toPx()
    val inset = width / 2
    val topLeft = Offset(x = inset, y = inset)
    val bounds = Size(width = size.width - width, height = size.height - width)
    drawArc(
        color = style.ringTrack,
        startAngle = 0f,
        sweepAngle = FULL_TURN,
        useCenter = false,
        topLeft = topLeft,
        size = bounds,
        style = Stroke(width = width),
    )
    if (sweep <= 0f) return
    drawArc(
        color = color,
        startAngle = style.ringStart + turn,
        sweepAngle = sweep,
        useCenter = false,
        topLeft = topLeft,
        size = bounds,
        style = Stroke(width = width, cap = style.ringCap),
    )
}

@Composable
private fun turnOf(isTurning: Boolean, period: Duration): State<Float> {
    if (!isTurning) return remember { mutableFloatStateOf(0f) }
    val transition = rememberInfiniteTransition(label = "ring")
    val animation = tween<Float>(durationMillis = period.inWholeMilliseconds.toInt(), easing = LinearEasing)
    return transition.animateFloat(
        initialValue = 0f,
        targetValue = FULL_TURN,
        animationSpec = infiniteRepeatable(animation = animation, repeatMode = RepeatMode.Restart),
        label = "turn",
    )
}

private fun followingOf(style: CenteredSplashStyle, isReduced: Boolean): AnimationSpec<Float> {
    if (isReduced) return snap()
    return tween(durationMillis = style.ringDuration.inWholeMilliseconds.toInt(), easing = style.ringEasing)
}

private fun arcColorOf(isFailed: Boolean, style: CenteredSplashStyle): Color {
    if (isFailed) return style.failure.color
    return style.ringColor
}

private fun rangeOf(fraction: Float?): ProgressBarRangeInfo {
    if (fraction == null) return ProgressBarRangeInfo.Indeterminate
    return ProgressBarRangeInfo(current = fraction, range = 0f..1f)
}
