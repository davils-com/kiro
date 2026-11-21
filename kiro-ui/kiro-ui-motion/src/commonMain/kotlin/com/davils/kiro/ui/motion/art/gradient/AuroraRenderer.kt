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

package com.davils.kiro.ui.motion.art.gradient

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.lerp
import com.davils.kiro.ui.motion.art.ArtFrame
import com.davils.kiro.ui.motion.art.ArtRenderer
import com.davils.kiro.ui.motion.art.TriangleBatch
import com.davils.kiro.ui.motion.color.ColorGradient
import com.davils.kiro.ui.motion.color.colorsOr
import com.davils.kiro.ui.motion.noise.perlinNoise
import com.davils.kiro.ui.motion.noise.smoothstep
import com.davils.kiro.ui.motion.noise.unitOf
import com.davils.kiro.ui.motion.time.loopOf
import kotlin.math.abs
import kotlin.math.ceil

private const val HALF: Float = 0.5f
private const val GRADIENT_STEPS: Int = 64
private const val SIDE: Int = 4
private const val ROWS: Int = SIDE * 2 + 1
private const val MAX_SEGMENTS: Int = 3000
private const val LOOP: Double = 4096.0
private const val OVERTONE: Float = 1.9f
private const val RAYS: Float = 4f
private const val SHIFT: Double = 0.25
private const val SALT_SWAY: Int = 1
private const val SALT_OVERTONE: Int = 2
private const val SALT_TONE: Int = 3

internal class AuroraRenderer(private val style: AuroraArtStyle) : ArtRenderer {
    private val gradient = ColorGradient()
    private val batch = TriangleBatch()

    override fun DrawScope.drawFrame(frame: ArtFrame) {
        val tones = gradient.of(colors = frame.palette.colorsOr(style.colors), steps = GRADIENT_STEPS)
        val wavelength = style.wavelength.toPx().coerceAtLeast(minimumValue = 1f)
        val pieces = ceil(size.width / wavelength * style.segments * frame.quality.density).toInt()
        val segments = pieces.coerceIn(minimumValue = 2, maximumValue = MAX_SEGMENTS)
        val phase = frame.time.phase(style.period)
        for (band in 0 until style.bands.coerceAtLeast(minimumValue = 0)) {
            band(frame = frame, band = band, segments = segments, phase = phase, tones = tones)
            with(batch) { flushLayer(blendMode = style.blendMode) }
        }
    }

    private fun DrawScope.band(frame: ArtFrame, band: Int, segments: Int, phase: Double, tones: List<Color>) {
        val seed = frame.seed.value
        val wavelength = style.wavelength.toPx().coerceAtLeast(minimumValue = 1f)
        val amplitude = style.amplitude.toPx()
        val thickness = style.thickness.toPx()
        val base = size.height * (style.top + style.gap * band)
        val sway = unitOf(x = band, y = SALT_SWAY, seed = seed)
        val overtone = unitOf(x = band, y = SALT_OVERTONE, seed = seed)
        val shade = unitOf(x = band, y = SALT_TONE, seed = seed)
        val drift = (phase * (band + 1)).mod(LOOP).toFloat()
        val shift = (phase * SHIFT).mod(1.0).toFloat()
        val first = batch.size
        for (step in 0..segments) {
            val fraction = step.toFloat() / segments
            val x = size.width * fraction
            val along = x / wavelength
            val primary = loopOf(phase = along - phase, cycles = 1, offset = sway)
            val secondary = loopOf(phase = along * OVERTONE + phase * 2, cycles = 1, offset = overtone)
            val line = base + amplitude * (primary + HALF * secondary)
            val rays = perlinNoise(x = along * RAYS, y = drift, seed = seed + band)
            val strength = (1f - style.ripple * (HALF + HALF * rays)).coerceIn(minimumValue = 0f, maximumValue = 1f)
            val color = toneOf(tones = tones, tone = abs((fraction * HALF + shade + shift).mod(1f) * 2f - 1f))
            column(
                x = x,
                line = line,
                upper = thickness * style.lift * (HALF + HALF * strength),
                lower = thickness * (1f - style.lift),
                glow = color.copy(alpha = color.alpha * style.alpha * strength),
            )
        }
        mesh(first = first, segments = segments)
    }

    private fun column(x: Float, line: Float, upper: Float, lower: Float, glow: Color) {
        for (row in SIDE downTo 1) vertex(x = x, y = line - upper * row / SIDE, glow = glow, row = row)
        vertex(x = x, y = line, glow = glow, row = 0)
        for (row in 1..SIDE) vertex(x = x, y = line + lower * row / SIDE, glow = glow, row = row)
    }

    private fun vertex(x: Float, y: Float, glow: Color, row: Int) {
        val falloff = smoothstep(value = 1f - row.toFloat() / SIDE)
        batch.vertex(position = Offset(x = x, y = y), color = glow.copy(alpha = glow.alpha * falloff))
    }

    private fun mesh(first: Int, segments: Int) {
        for (step in 0 until segments) {
            for (row in 0 until ROWS - 1) {
                val corner = first + step * ROWS + row
                batch.triangle(first = corner, second = corner + ROWS, third = corner + 1)
                batch.triangle(first = corner + 1, second = corner + ROWS, third = corner + ROWS + 1)
            }
        }
    }
}

private fun toneOf(tones: List<Color>, tone: Float): Color {
    val position = tone.coerceIn(minimumValue = 0f, maximumValue = 1f) * (tones.size - 1)
    val index = position.toInt().coerceAtMost(maximumValue = tones.size - 2)
    return lerp(start = tones[index], stop = tones[index + 1], fraction = position - index)
}
