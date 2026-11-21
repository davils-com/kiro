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

package com.davils.kiro.ui.motion.art.field

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.davils.kiro.ui.motion.art.ArtFrame
import com.davils.kiro.ui.motion.art.ArtRenderer
import com.davils.kiro.ui.motion.color.ColorGradient
import com.davils.kiro.ui.motion.color.colorsOr
import com.davils.kiro.ui.motion.noise.unitOf
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.cos
import kotlin.math.exp
import kotlin.math.floor
import kotlin.math.hypot
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.sqrt

private const val TAU: Float = (PI * 2).toFloat()
private const val RADIANS: Float = (PI / 180).toFloat()
private const val QUARTER_TURN: Float = (PI / 2).toFloat()
private const val EIGHTH_TURN: Float = (PI / 4).toFloat()
private const val HALF: Float = 0.5f
private const val HEX_RISE: Float = 0.8660254f
private const val SQUARE_RADIUS: Float = 0.70710677f
private const val CORNERS: Int = 4
private const val GRADIENT_STEPS: Int = 64
private const val SALT_RATE_X: Int = 31
private const val SALT_RATE_Y: Int = 37
private const val SALT_OFFSET_X: Int = 41
private const val SALT_OFFSET_Y: Int = 43

internal class HalftoneRenderer(private val style: HalftoneArtStyle) : ArtRenderer {
    private val path = Path()
    private val gradient = ColorGradient()
    private val waves = style.waves.coerceAtLeast(minimumValue = 1)
    private val wavelengths = style.wavelengths.ifEmpty { listOf(style.pitch) }
    private val speeds = style.speeds.ifEmpty { listOf(0f) }
    private val centerX = FloatArray(size = waves)
    private val centerY = FloatArray(size = waves)
    private val shift = FloatArray(size = waves)
    private val length = FloatArray(size = waves)
    private val cornerX = FloatArray(size = CORNERS)
    private val cornerY = FloatArray(size = CORNERS)
    private val normalize = 1f / sqrt(waves.toFloat())
    private var colors: List<Color> = emptyList()
    private var pointer: Offset? = null
    private var pitch = 1f
    private var gradientX = 1f
    private var gradientY = 0f
    private var extent = 1f
    private var radius = 1f
    private var pointerLength = 1f

    override fun DrawScope.drawFrame(frame: ArtFrame) {
        pitch = (style.pitch.toPx() / sqrt(frame.quality.density)).coerceAtLeast(minimumValue = 1f)
        pointer = frame.inputs.pointer
        colors = gradient.of(colors = frame.palette.colorsOr(style.colors), steps = GRADIENT_STEPS)
        prepareWaves(frame = frame)
        prepareShape()
        val angle = style.angle * RADIANS
        val isHex = style.pattern == HalftonePattern.Hex
        val skew = HALF.takeIf { isHex } ?: 0f
        val rise = HEX_RISE.takeIf { isHex } ?: 1f
        val stepX = Offset(x = cos(angle) * pitch, y = sin(angle) * pitch)
        val stepY = Offset(
            x = (skew * cos(angle) - rise * sin(angle)) * pitch,
            y = (skew * sin(angle) + rise * cos(angle)) * pitch,
        )
        val reach = hypot(x = size.width, y = size.height) * HALF + pitch
        val across = ceil(reach / pitch).toInt() + 1
        val down = ceil(reach / (pitch * rise)).toInt() + 1
        val center = Offset(x = size.width * HALF, y = size.height * HALF)
        for (row in -down..down) {
            val start = -across - floor(row * skew).toInt()
            for (column in start..start + 2 * across + 1) {
                val x = center.x + column * stepX.x + row * stepY.x
                val y = center.y + column * stepX.y + row * stepY.y
                if (isVisible(x = x, y = y)) dot(x = x, y = y)
            }
        }
    }

    private fun DrawScope.isVisible(x: Float, y: Float): Boolean =
        x > -pitch && y > -pitch && x < size.width + pitch && y < size.height + pitch

    private fun DrawScope.dot(x: Float, y: Float) {
        val diameter = pitch * style.maxDot * valueAt(x = x, y = y)
        if (diameter < pitch * style.minDot || diameter <= 0f) return
        val across = ((x - size.width * HALF) * gradientX + (y - size.height * HALF) * gradientY) / extent + HALF
        val color = colors[stepOf(fraction = across)]
        when (style.shape) {
            HalftoneShape.Circle -> drawCircle(
                color = color,
                radius = diameter * HALF,
                center = Offset(x = x, y = y),
                alpha = style.alpha,
            )

            HalftoneShape.Square -> polygon(x = x, y = y, radius = diameter * SQUARE_RADIUS, color = color)

            HalftoneShape.Diamond -> polygon(x = x, y = y, radius = diameter * HALF, color = color)
        }
    }

    private fun DrawScope.polygon(x: Float, y: Float, radius: Float, color: Color) {
        path.rewind()
        path.moveTo(x = x + cornerX[0] * radius, y = y + cornerY[0] * radius)
        for (corner in 1 until CORNERS) {
            path.lineTo(x = x + cornerX[corner] * radius, y = y + cornerY[corner] * radius)
        }
        path.close()
        drawPath(path = path, color = color, alpha = style.alpha)
    }

    private fun valueAt(x: Float, y: Float): Float {
        var sum = 0f
        for (wave in 0 until waves) {
            val distance = hypot(x = x - centerX[wave], y = y - centerY[wave])
            sum += sin(TAU * (distance / length[wave] - shift[wave]))
        }
        var value = HALF + HALF * sum * normalize
        val touch = pointer
        if (touch != null) value += ripple(x = x, y = y, touch = touch)
        return value.coerceIn(minimumValue = 0f, maximumValue = 1f)
    }

    private fun ripple(x: Float, y: Float, touch: Offset): Float {
        val distance = hypot(x = x - touch.x, y = y - touch.y)
        val falloff = distance / radius
        val wave = HALF + HALF * sin(TAU * (distance / pointerLength - shift[0]))
        return style.pointerStrength * exp(-falloff * falloff) * wave
    }

    private fun DrawScope.prepareWaves(frame: ArtFrame) {
        val phase = frame.time.phase(style.period)
        val wander = frame.time.phase(style.wanderPeriod)
        val seed = frame.seed.value
        radius = style.pointerRadius.toPx().coerceAtLeast(minimumValue = 1f)
        pointerLength = style.pointerWavelength.toPx().coerceAtLeast(minimumValue = 1f)
        val gradientAngle = style.gradientAngle * RADIANS
        gradientX = cos(gradientAngle)
        gradientY = sin(gradientAngle)
        extent = (abs(size.width * gradientX) + abs(size.height * gradientY)).coerceAtLeast(minimumValue = 1f)
        for (wave in 0 until waves) {
            length[wave] = wavelengths[wave % wavelengths.size].toPx().coerceAtLeast(minimumValue = 1f)
            shift[wave] = (phase * speeds[wave % speeds.size]).mod(1.0).toFloat()
            val rateX = 1f + unitOf(x = wave, seed = seed + SALT_RATE_X)
            val rateY = 1f + unitOf(x = wave, seed = seed + SALT_RATE_Y)
            val turnX = (wander * rateX).mod(1.0).toFloat() + unitOf(x = wave, seed = seed + SALT_OFFSET_X)
            val turnY = (wander * rateY).mod(1.0).toFloat() + unitOf(x = wave, seed = seed + SALT_OFFSET_Y)
            centerX[wave] = size.width * (HALF + style.orbit * sin(TAU * turnX))
            centerY[wave] = size.height * (HALF + style.orbit * cos(TAU * turnY))
        }
    }

    private fun prepareShape() {
        val isSquare = style.shape == HalftoneShape.Square
        val start = style.angle * RADIANS + (EIGHTH_TURN.takeIf { isSquare } ?: 0f)
        for (corner in 0 until CORNERS) {
            cornerX[corner] = cos(start + corner * QUARTER_TURN)
            cornerY[corner] = sin(start + corner * QUARTER_TURN)
        }
    }
}

private fun stepOf(fraction: Float): Int {
    val step = (fraction * (GRADIENT_STEPS - 1)).roundToInt()
    return step.coerceIn(minimumValue = 0, maximumValue = GRADIENT_STEPS - 1)
}
