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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.davils.kiro.ui.motion.art.ArtFrame
import com.davils.kiro.ui.motion.art.ArtRenderer
import com.davils.kiro.ui.motion.color.blend
import com.davils.kiro.ui.motion.noise.lerp
import com.davils.kiro.ui.motion.noise.unitOf
import kotlin.math.PI
import kotlin.math.min
import kotlin.math.sin

private const val FULL_TURN: Double = PI * 2.0
private const val RADIANS: Double = PI / 180.0
private const val AXES: Int = 3

internal class WireframeRenderer(private val style: WireframeArtStyle) : ArtRenderer {
    private val vertices = solidVertices(style.solid)
    private val edges = solidEdges(vertices)
    private val turned = FloatArray(vertices.size)
    private val screen = FloatArray(vertices.size / AXES * 2)

    override val hasStatic: Boolean
        get() = true

    override fun DrawScope.drawStatic(frame: ArtFrame) {
        drawRect(color = style.background ?: frame.palette.background)
        if (style.guideAlpha <= 0f) return
        drawCircle(
            color = style.line ?: frame.palette.ink,
            radius = radiusOf() * silhouetteOf(style.perspective),
            center = centerOf(),
            alpha = style.guideAlpha,
            style = Stroke(width = style.lineWidth.toPx()),
        )
    }

    override fun DrawScope.drawFrame(frame: ArtFrame) {
        val seed = frame.seed.derive(style.salt).value
        val yaw = (unitOf(x = 0, seed = seed) + frame.time.phase(style.period)) * FULL_TURN
        val swing = sin(frame.time.phase(style.wobblePeriod) * FULL_TURN)
        val pitch = (style.tilt + style.wobble * swing) * RADIANS
        rotateVertices(vertices = vertices, yaw = yaw, pitch = pitch, into = turned)
        val center = centerOf()
        val radius = radiusOf()
        for (index in 0 until vertices.size / AXES) {
            val scale = perspectiveOf(depth = turned[index * AXES + 2], eye = style.perspective) * radius
            screen[index * 2] = center.x + turned[index * AXES] * scale
            screen[index * 2 + 1] = center.y - turned[index * AXES + 1] * scale
        }
        val far = style.line ?: frame.palette.ink
        val near = style.accent ?: frame.palette.accent(-1)
        drawEdges(far = far, near = near)
        if (style.dot.toPx() > 0f && frame.quality.density >= style.dotQuality.density) drawDots(far = far, near = near)
    }

    private fun DrawScope.centerOf(): Offset = Offset(x = size.width * style.centerX, y = size.height * style.centerY)

    private fun DrawScope.radiusOf(): Float = style.size * min(a = size.width, b = size.height)

    private fun DrawScope.drawEdges(far: Color, near: Color) {
        val width = style.lineWidth.toPx()
        for (edge in 0 until edges.size / 2) {
            val first = edges[edge * 2]
            val second = edges[edge * 2 + 1]
            val nearness = nearnessOf((turned[first * AXES + 2] + turned[second * AXES + 2]) / 2f)
            drawLine(
                color = blend(start = far, end = near, amount = nearness),
                start = Offset(x = screen[first * 2], y = screen[first * 2 + 1]),
                end = Offset(x = screen[second * 2], y = screen[second * 2 + 1]),
                strokeWidth = width,
                cap = StrokeCap.Round,
                alpha = lerp(start = style.farAlpha, end = style.nearAlpha, fraction = nearness),
            )
        }
    }

    private fun DrawScope.drawDots(far: Color, near: Color) {
        val radius = style.dot.toPx() / 2f
        for (index in 0 until vertices.size / AXES) {
            val nearness = nearnessOf(turned[index * AXES + 2])
            drawCircle(
                color = blend(start = far, end = near, amount = nearness),
                radius = radius * lerp(start = style.farDot, end = 1f, fraction = nearness),
                center = Offset(x = screen[index * 2], y = screen[index * 2 + 1]),
                alpha = lerp(start = style.farAlpha, end = style.dotAlpha, fraction = nearness),
            )
        }
    }
}
