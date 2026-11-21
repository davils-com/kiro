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

package com.davils.kiro.ui.motion.art.geometry

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.withTransform
import com.davils.kiro.ui.motion.art.ArtFrame
import com.davils.kiro.ui.motion.art.ArtRenderer
import com.davils.kiro.ui.motion.color.cyclic
import com.davils.kiro.ui.motion.noise.rangeOf
import com.davils.kiro.ui.motion.noise.unitOf
import kotlin.math.min

private const val TURN: Double = 360.0
private const val ROLLER_SALT: Int = 0x68e31da4
private const val PEN_SALT: Int = 0x1b56c4e9
private const val OUTSIDE_SALT: Int = 0x7a6d76e9

internal class SpirographRenderer(private val style: SpirographArtStyle) : ArtRenderer {
    private val partial = Path()
    private var curves: List<Curve> = emptyList()
    private var builtSize = Size.Unspecified
    private var builtDensity = 0f
    private var builtSeed = 0
    private var builtQuality = 0f
    private var line = Stroke()

    override val hasStatic: Boolean
        get() = true

    override fun DrawScope.drawStatic(frame: ArtFrame) {
        drawRect(color = style.background ?: frame.palette.background)
    }

    override fun DrawScope.drawFrame(frame: ArtFrame) {
        prepare(frame)
        if (curves.isEmpty()) return
        val center = Offset(x = size.width * style.centerX, y = size.height * style.centerY)
        val colors = style.colors ?: frame.palette.accents
        val ink = frame.palette.ink
        val head = style.head ?: frame.palette.highlight
        val rotation = frame.time.phase(style.rotationPeriod)
        val drawing = frame.time.phase(style.drawPeriod)
        for (index in curves.indices) {
            val curve = curves[index]
            val color = colors.cyclic(index = (index + 1) * colors.size / (curves.size + 1), fallback = ink)
            val direction = 1 - 2 * (index % 2)
            val degrees = ((rotation * direction + index.toDouble() / curves.size) * TURN).toFloat()
            val cycle = (drawing + index.toDouble() / curves.size).mod(1.0)
            withTransform(
                transformBlock = {
                    translate(left = center.x, top = center.y)
                    rotate(degrees = degrees, pivot = Offset.Zero)
                },
            ) {
                drawCurve(curve = curve, color = color, cycle = cycle, direction = direction, head = head)
            }
        }
    }

    private fun DrawScope.prepare(frame: ArtFrame) {
        val isSame = size == builtSize && density == builtDensity && frame.seed.value == builtSeed
        if (isSame && frame.quality.density == builtQuality) return
        builtSize = size
        builtDensity = density
        builtSeed = frame.seed.value
        builtQuality = frame.quality.density
        line = Stroke(width = style.lineWidth.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        val seed = frame.seed.derive(style.salt).value
        val largest = style.size * min(a = size.width, b = size.height) / 2f
        val steps = (style.steps * frame.quality.density).toInt()
        val count = style.curves.coerceAtLeast(0)
        curves = List(count) { index ->
            val share = index.toFloat() / (count - 1).coerceAtLeast(1)
            val radius = largest * (1f - share * (1f - style.minSize))
            val isOutside = unitOf(x = index, seed = seed + OUTSIDE_SALT) < style.epicycles
            val target = rangeOf(from = style.minRoller, to = style.maxRoller, x = index, seed = seed + ROLLER_SALT)
            val ratio = ratioNear(target = target, loops = style.loops, isOutside = isOutside)
            val trochoid = Trochoid(
                fixed = ratio[1],
                rolling = ratio[0],
                pen = ratio[0] * rangeOf(from = style.minPen, to = style.maxPen, x = index, seed = seed + PEN_SALT),
                isOutside = isOutside,
            )
            Curve(points = trochoid.points(steps = steps, scale = radius / trochoid.extent))
        }
    }

    private fun DrawScope.drawCurve(curve: Curve, color: Color, cycle: Double, direction: Int, head: Color) {
        for (echo in style.echoes downTo 1) {
            val alpha = style.trailAlpha * (1f - echo.toFloat() / (style.echoes + 1))
            rotate(degrees = -direction * echo * style.echoAngle, pivot = Offset.Zero) {
                drawPath(path = curve.path, color = color, alpha = alpha, style = line)
            }
        }
        drawPath(path = curve.path, color = color, alpha = style.trailAlpha, style = line)
        val progress = drawingOf(cycle = cycle, share = style.drawShare)
        val fade = fadeOf(cycle = cycle, share = style.drawShare)
        val tip = curve.trace(progress = progress, into = partial)
        drawPath(path = partial, color = color, alpha = style.lineAlpha * fade, style = line)
        if (progress >= 1f) return
        drawCircle(color = color, radius = style.headGlow.toPx() / 2f, center = tip, alpha = style.glowAlpha)
        drawCircle(color = head, radius = style.headSize.toPx() / 2f, center = tip)
    }

    private class Curve(val points: FloatArray) {
        val path: Path = Path().apply {
            moveTo(x = points[0], y = points[1])
            for (index in 1 until points.size / 2) lineTo(x = points[index * 2], y = points[index * 2 + 1])
            close()
        }

        fun trace(progress: Float, into: Path): Offset {
            into.rewind()
            val last = points.size / 2 - 1
            val position = progress * last
            val whole = position.toInt().coerceAtMost(last)
            into.moveTo(x = points[0], y = points[1])
            for (index in 1..whole) into.lineTo(x = points[index * 2], y = points[index * 2 + 1])
            if (whole == last) return Offset(x = points[last * 2], y = points[last * 2 + 1])
            val fraction = position - whole
            val x = points[whole * 2] + (points[whole * 2 + 2] - points[whole * 2]) * fraction
            val y = points[whole * 2 + 1] + (points[whole * 2 + 3] - points[whole * 2 + 1]) * fraction
            into.lineTo(x = x, y = y)
            return Offset(x = x, y = y)
        }
    }
}
