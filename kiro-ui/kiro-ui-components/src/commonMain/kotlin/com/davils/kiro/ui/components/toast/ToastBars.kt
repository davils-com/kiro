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

package com.davils.kiro.ui.components.toast

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.withInfiniteAnimationFrameMillis
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.components.primitive.motion.sizeTransition
import com.davils.kiro.ui.components.theme

private const val SEGMENT: Float = 0.4f
private const val SWEEP_MILLIS: Long = 1200L

@Composable
internal fun ToastProgressBar(progress: ToastProgress, style: ToastStyle) {
    val determinate = progress as? ToastProgress.Determinate
    val fraction = animateFloatAsState(targetValue = determinate?.value ?: 0f, animationSpec = theme.sizeTransition())
    val sweep = sweepOf(isRunning = determinate == null && !theme.motion.isReduced)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(style.progressHeight)
            .clip(CircleShape)
            .drawBehind {
                drawRect(color = style.progressTrack)
                val isKnown = determinate != null
                val start = 0f.takeIf { isKnown } ?: -SEGMENT + (1f + SEGMENT) * sweep.value
                val length = fraction.value.takeIf { isKnown } ?: SEGMENT
                bar(start = start, length = length, color = style.progressColor)
            },
    )
}

@Composable
internal fun ToastTimerBar(remaining: Float, height: Dp, color: Color, modifier: Modifier) {
    val fraction = animateFloatAsState(targetValue = remaining, animationSpec = theme.sizeTransition())
    Box(
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .height(height)
                .drawBehind { bar(start = 0f, length = fraction.value, color = color) },
        ),
    )
}

private fun DrawScope.bar(start: Float, length: Float, color: Color) {
    drawRoundRect(
        color = color,
        topLeft = Offset(x = size.width * start, y = 0f),
        size = Size(width = size.width * length, height = size.height),
        cornerRadius = CornerRadius(size.height / 2f),
    )
}

@Composable
private fun sweepOf(isRunning: Boolean): State<Float> = produceState(initialValue = 0f, isRunning) {
    while (isRunning) {
        withInfiniteAnimationFrameMillis { time -> value = (time % SWEEP_MILLIS).toFloat() / SWEEP_MILLIS }
    }
}
