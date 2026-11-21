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

package com.davils.kiro.ui.motion.art.particle

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.davils.kiro.ui.motion.art.ArtFrame
import com.davils.kiro.ui.motion.art.ArtRenderer
import com.davils.kiro.ui.motion.color.colorsOr
import com.davils.kiro.ui.motion.noise.hashOf
import com.davils.kiro.ui.motion.noise.lerp
import com.davils.kiro.ui.motion.noise.pickOf
import com.davils.kiro.ui.motion.noise.unitOf
import com.davils.kiro.ui.motion.time.loopOf
import kotlin.math.roundToInt

private const val HALF: Float = 0.5f
private const val CYCLES: Int = 2
private const val SALT_X: Int = 1
private const val SALT_Y: Int = 2
private const val SALT_SIZE: Int = 3
private const val SALT_ALPHA: Int = 4
private const val SALT_COLOR: Int = 5
private const val SALT_CYCLES_X: Int = 6
private const val SALT_CYCLES_Y: Int = 7
private const val SALT_OFFSET_X: Int = 8
private const val SALT_OFFSET_Y: Int = 9
private const val SALT_BREATH: Int = 10

internal class BokehRenderer(private val style: BokehArtStyle) : ArtRenderer {
    override fun DrawScope.drawFrame(frame: ArtFrame) {
        val colors = frame.palette.colorsOr(style.colors)
        val count = (style.count * frame.quality.density).roundToInt().coerceAtLeast(minimumValue = 1)
        val wander = frame.time.phase(style.period)
        val breath = frame.time.phase(style.breathePeriod)
        for (index in 0 until count) {
            orb(frame = frame, index = index, colors = colors, wander = wander, breath = breath)
        }
    }

    private fun DrawScope.orb(frame: ArtFrame, index: Int, colors: List<Color>, wander: Double, breath: Double) {
        val key = hashOf(x = index, seed = frame.seed.value)
        val growth = unitOf(x = key, y = SALT_SIZE)
        val base = lerp(start = style.minRadius.toPx(), end = style.maxRadius.toPx(), fraction = growth)
        val pulse = loopOf(phase = breath, cycles = 1, offset = unitOf(x = key, y = SALT_BREATH))
        val radius = base * (1f + style.breathe * pulse)
        val reach = style.wander.toPx()
        val swayX = loopOf(
            phase = wander,
            cycles = 1 + pickOf(size = CYCLES, x = key, y = SALT_CYCLES_X),
            offset = unitOf(x = key, y = SALT_OFFSET_X),
        )
        val swayY = loopOf(
            phase = wander,
            cycles = 1 + pickOf(size = CYCLES, x = key, y = SALT_CYCLES_Y),
            offset = unitOf(x = key, y = SALT_OFFSET_Y),
        )
        val center = Offset(
            x = unitOf(x = key, y = SALT_X) * size.width + reach * swayX,
            y = unitOf(x = key, y = SALT_Y) * size.height + reach * swayY,
        )
        val isBefore = center.x < -radius || center.y < -radius
        val isAfter = center.x > size.width + radius || center.y > size.height + radius
        if (radius <= 0f || isBefore || isAfter) return
        val alpha = style.alpha * lerp(start = style.alphaFloor, end = 1f, fraction = unitOf(x = key, y = SALT_ALPHA))
        val color = colors[pickOf(size = colors.size, x = key, y = SALT_COLOR)]
        disc(color = color, alpha = alpha, center = center, radius = radius)
    }

    private fun DrawScope.disc(color: Color, alpha: Float, center: Offset, radius: Float) {
        val softness = style.softness.coerceIn(minimumValue = 0f, maximumValue = 1f)
        val solid = color.copy(alpha = color.alpha * alpha)
        drawCircle(
            brush = Brush.radialGradient(
                0f to solid,
                1f - softness to solid,
                1f to solid.copy(alpha = 0f),
                center = center,
                radius = radius,
            ),
            radius = radius,
            center = center,
        )
        if (style.rimAlpha <= 0f) return
        drawCircle(
            color = color,
            radius = radius * (1f - softness * HALF),
            center = center,
            alpha = alpha * style.rimAlpha,
            style = Stroke(width = style.rimWidth.toPx()),
        )
    }
}
