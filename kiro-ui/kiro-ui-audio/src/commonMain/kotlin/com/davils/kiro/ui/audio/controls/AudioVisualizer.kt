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

package com.davils.kiro.ui.audio.controls

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.withInfiniteAnimationFrameMillis
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import com.davils.kiro.ui.audio.AudioControlsStyle
import com.davils.kiro.ui.audio.audioControlsStyle
import com.davils.kiro.ui.audio.resizing
import com.davils.kiro.ui.core.content.LocalContentColor
import kotlin.math.abs
import kotlin.math.sin

/**
 * A few bars that dance while a track plays and settle while it is paused; they rest when the motion of the theme is
 * reduced.
 *
 * @param isPlaying whether the track plays.
 * @param modifier the modifier of the visualizer.
 * @param color the color of the bars.
 * @param style the style of the audio controls.
 * @since 1.0.0
 */
@Composable
public fun AudioVisualizer(
    isPlaying: Boolean,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
    style: AudioControlsStyle = audioControlsStyle(),
) {
    val amplitude = animateFloatAsState(
        targetValue = 1f.takeIf { isPlaying } ?: 0f,
        animationSpec = style.motion.resizing(),
    )
    val time = timeOf(isRunning = isPlaying && !style.motion.isReduced)
    val bars = style.visualizerBars
    Canvas(modifier = modifier.size(style.visualizerSize)) {
        val slot = size.width / (bars + (bars - 1) * style.visualizerGap)
        for (index in 0 until bars) {
            val phase = time.value * style.visualizerSpeed * (1f + index * style.visualizerWave)
            val wave = abs(sin(phase + index * style.visualizerSpread))
            val level = style.visualizerRest + (1f - style.visualizerRest) * wave * amplitude.value
            val height = size.height * level
            drawRoundRect(
                color = color,
                topLeft = Offset(x = index * slot * (1f + style.visualizerGap), y = size.height - height),
                size = Size(width = slot, height = height),
                cornerRadius = CornerRadius(slot / 2f),
            )
        }
    }
}

@Composable
private fun timeOf(isRunning: Boolean): State<Float> = produceState(initialValue = 0f, isRunning) {
    while (isRunning) {
        withInfiniteAnimationFrameMillis { millis -> value = millis.toFloat() }
    }
}
