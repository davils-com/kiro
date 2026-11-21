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

package com.davils.kiro.ui.motion.shader.silk

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.davils.kiro.ui.motion.art.ArtFrame
import com.davils.kiro.ui.motion.art.ArtRenderer
import com.davils.kiro.ui.motion.noise.unitOf
import com.davils.kiro.ui.motion.shader.loopOf
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

private const val TURN: Float = 2f * PI.toFloat()
private const val SKEW: Float = 0.35f
private const val WIDEN: Float = 0.6f
private const val SHEEN_SHARE: Float = 0.6f
private const val SALT_OFFSET: Int = 29

internal class SilkFallback(private val style: SilkArtStyle) : ArtRenderer {
    override fun DrawScope.drawFrame(frame: ArtFrame) {
        if (size.minDimension <= 0f) return
        drawRect(color = style.groundOf(frame))
        val tint = style.colorOf(frame)
        val shadow = style.shadowOf(frame)
        val sheen = style.sheenOf(frame)
        band(frame = frame, index = 0, color = tint, alpha = style.intensity * tint.alpha)
        band(frame = frame, index = 1, color = shadow, alpha = style.shadowAlpha)
        band(frame = frame, index = 2, color = sheen, alpha = style.sheenAlpha * SHEEN_SHARE)
    }

    private fun DrawScope.band(frame: ArtFrame, index: Int, color: Color, alpha: Float) {
        val radians = style.radiansOf(frame) + index * SKEW
        val direction = Offset(x = cos(radians), y = sin(radians))
        val width = (style.scale * size.maxDimension * TURN * (1f + index * WIDEN)).coerceAtLeast(minimumValue = 1f)
        val fraction = frame.time.loopOf(style.period)
        val offset = (fraction * (index + 1) + unitOf(x = index, seed = frame.seed.value + SALT_OFFSET)) * width * 2f
        val start = center + direction * offset
        val strong = color.copy(alpha = alpha.coerceIn(minimumValue = 0f, maximumValue = 1f))
        val brush = Brush.linearGradient(
            colors = listOf(color.copy(alpha = 0f), strong),
            start = start,
            end = start + direction * width,
            tileMode = TileMode.Mirror,
        )
        drawRect(brush = brush)
    }
}
