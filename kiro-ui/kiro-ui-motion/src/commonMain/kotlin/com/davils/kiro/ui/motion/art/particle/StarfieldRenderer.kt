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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.davils.kiro.ui.motion.art.ArtFrame
import com.davils.kiro.ui.motion.art.ArtRenderer
import com.davils.kiro.ui.motion.color.ColorGradient
import com.davils.kiro.ui.motion.color.colorsOr
import com.davils.kiro.ui.motion.noise.lerp
import com.davils.kiro.ui.motion.noise.smoothstep
import com.davils.kiro.ui.motion.noise.unitOf
import kotlin.math.roundToInt

private const val GRADIENT_STEPS: Int = 32
private const val SALT_X: Int = 1
private const val SALT_Y: Int = 2
private const val SALT_OFFSET: Int = 3
private const val LEAST: Float = 0.01f

internal class StarfieldRenderer(private val style: StarfieldArtStyle) : ArtRenderer {
    private val gradient = ColorGradient()

    override fun DrawScope.drawFrame(frame: ArtFrame) {
        val colors = gradient.of(colors = frame.palette.colorsOr(style.colors), steps = GRADIENT_STEPS)
        val count = (style.count * frame.quality.density).roundToInt()
        val phase = frame.time.phase(style.period)
        val nearest = style.nearest.coerceIn(minimumValue = LEAST, maximumValue = 1f)
        val reach = size.maxDimension * style.spread
        val vanishing = Offset(x = size.width * style.centerX, y = size.height * style.centerY)
        val minWidth = style.minWidth.toPx()
        val maxWidth = style.maxWidth.toPx()
        val fadeIn = style.fadeIn.coerceAtLeast(minimumValue = LEAST)
        val seed = frame.seed.value
        for (index in 0 until count) {
            val journey = journeyOf(phase = phase, offset = unitOf(x = index, y = SALT_OFFSET, seed = seed))
            val depth = depthOf(journey = journey, nearest = nearest)
            val direction = Offset(
                x = unitOf(x = index, y = SALT_X, seed = seed) * 2f - 1f,
                y = unitOf(x = index, y = SALT_Y, seed = seed) * 2f - 1f,
            )
            val tail = vanishing + direction * (reach / (depth * (1f + style.streak)))
            val isOutside = tail.x < 0f || tail.y < 0f || tail.x > size.width || tail.y > size.height
            if (isOutside) continue
            val closeness = journey * journey
            drawLine(
                color = colors[(closeness * (GRADIENT_STEPS - 1)).roundToInt()],
                start = tail,
                end = vanishing + direction * (reach / depth),
                strokeWidth = lerp(start = minWidth, end = maxWidth, fraction = closeness),
                cap = StrokeCap.Round,
                alpha = style.alpha * smoothstep(journey / fadeIn),
            )
        }
    }
}
