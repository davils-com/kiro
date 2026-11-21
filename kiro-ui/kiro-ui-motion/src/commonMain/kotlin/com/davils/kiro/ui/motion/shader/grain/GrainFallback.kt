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

package com.davils.kiro.ui.motion.shader.grain

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.davils.kiro.ui.motion.art.ArtFrame
import com.davils.kiro.ui.motion.art.ArtRenderer
import com.davils.kiro.ui.motion.noise.unitOf

private const val LEVELS: Int = 3
private const val SALT_Y: Int = 1
private const val SALT_TONE: Int = 2
private const val SALT_STRENGTH: Int = 3

internal class GrainFallback(private val style: GrainArtStyle) : ArtRenderer {
    private val batches: List<MutableList<Offset>> = List(LEVELS * 2) { mutableListOf() }

    override fun DrawScope.drawFrame(frame: ArtFrame) {
        if (size.minDimension <= 0f) return
        for (batch in batches) batch.clear()
        val pattern = style.patternOf(frame)
        val seed = frame.seed.value
        val count = (style.dots * frame.quality.density).toInt()
        for (dot in 0 until count) {
            val x = unitOf(x = dot, y = pattern, seed = seed) * size.width
            val y = unitOf(x = dot, y = pattern, seed = seed + SALT_Y) * size.height
            val tone = batchOf(isShade = unitOf(x = dot, y = pattern, seed = seed + SALT_TONE) < style.balance)
            val strength = unitOf(x = dot, y = pattern, seed = seed + SALT_STRENGTH)
            val level = (strength * LEVELS).toInt().coerceAtMost(maximumValue = LEVELS - 1)
            batches[tone + level] += Offset(x = x, y = y)
        }
        val width = style.cell.toPx().coerceAtLeast(minimumValue = 1f)
        for (index in batches.indices) {
            val isShade = index < LEVELS
            val color = colorOf(frame = frame, isShade = isShade)
            val strength = (index % LEVELS + 1f) / LEVELS
            drawPoints(
                points = batches[index],
                pointMode = PointMode.Points,
                color = color,
                strokeWidth = width,
                cap = StrokeCap.Square,
                alpha = (style.amount * strength * strength).coerceIn(minimumValue = 0f, maximumValue = 1f),
            )
        }
    }

    private fun colorOf(frame: ArtFrame, isShade: Boolean): Color {
        if (isShade) return style.shadeOf(frame)
        return style.toneOf(frame)
    }
}

private fun batchOf(isShade: Boolean): Int {
    if (isShade) return 0
    return LEVELS
}
