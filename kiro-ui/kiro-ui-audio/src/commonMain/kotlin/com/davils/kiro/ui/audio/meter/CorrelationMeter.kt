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

package com.davils.kiro.ui.audio.meter

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import com.davils.kiro.ui.audio.following
import kotlin.math.abs
import kotlin.math.min

/**
 * A phase correlation meter as editing programs show it beside a goniometer: a bar from the middle of a track to the
 * correlation of the two channels of a stereo signal, green towards `+1` where the signal stays intact in mono and
 * red towards `-1` where its channels cancel each other out.
 *
 * @param correlation the correlation between `-1` and `1`, measured by the caller; values outside are held at the ends.
 * @param modifier the modifier of the meter.
 * @param style the style of the meter.
 * @since 1.0.0
 */
@Composable
public fun CorrelationMeter(
    correlation: Float,
    modifier: Modifier = Modifier,
    style: CorrelationMeterStyle = correlationMeterStyle(),
) {
    val target = correlation.takeUnless { it.isNaN() }?.coerceIn(minimumValue = -1f, maximumValue = 1f) ?: 0f
    val shown by animateFloatAsState(targetValue = target, animationSpec = style.motion.following(style.response))
    val measurer = rememberTextMeasurer()
    val labels = remember(style, measurer) {
        val look = style.labelStyle.copy(color = style.labelColor)
        listOf(style.negative, style.center, style.positive).map { text -> measurer.measure(text = text, style = look) }
    }
    val height = with(LocalDensity.current) {
        style.thickness + style.labelGap + (labels.maxOfOrNull { label -> label.size.height } ?: 0).toDp()
    }
    Canvas(
        modifier = modifier.then(
            Modifier
                .size(width = style.length, height = height)
                .semantics {
                    contentDescription = style.description
                    stateDescription = style.readout(target)
                },
        ),
    ) {
        val thickness = style.thickness.toPx()
        val radius = CornerRadius(style.radius.toPx())
        drawRoundRect(color = style.track, size = Size(width = size.width, height = thickness), cornerRadius = radius)
        val middle = size.width / 2f
        val reach = middle * abs(shown)
        val color = style.positiveColor.takeIf { shown >= 0f } ?: style.negativeColor
        drawRoundRect(
            color = color,
            topLeft = Offset(x = min(a = middle, b = middle + middle * shown), y = 0f),
            size = Size(width = reach, height = thickness),
            cornerRadius = radius,
        )
        val center = style.centerWidth.toPx()
        drawRect(
            color = style.centerColor,
            topLeft = Offset(x = middle - center / 2f, y = 0f),
            size = Size(width = center, height = thickness),
        )
        val top = thickness + style.labelGap.toPx()
        val (negative, zero, positive) = labels
        drawText(negative, topLeft = Offset(x = 0f, y = top))
        drawText(zero, topLeft = Offset(x = middle - zero.size.width / 2f, y = top))
        drawText(positive, topLeft = Offset(x = size.width - positive.size.width, y = top))
    }
}
