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

package com.davils.kiro.ui.motion.art.line

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.davils.kiro.ui.motion.art.ArtFrame
import com.davils.kiro.ui.motion.art.ArtRenderer
import com.davils.kiro.ui.motion.color.colorsOr
import com.davils.kiro.ui.motion.color.cyclic
import com.davils.kiro.ui.motion.noise.pickOf
import com.davils.kiro.ui.motion.noise.unitOf
import kotlin.math.hypot
import kotlin.math.roundToInt

private const val COLOR_SALT: Int = 0x3c6ef372

internal class RippleRenderer(private val style: RippleArtStyle) : ArtRenderer {
    override val hasStatic: Boolean
        get() = true

    override fun DrawScope.drawStatic(frame: ArtFrame) {
        drawRect(color = style.background ?: frame.palette.background)
        val spacing = style.guideSpacing.toPx()
        if (style.guideAlpha <= 0f || spacing <= 0f) return
        val center = originOf()
        val stroke = Stroke(width = style.lineWidth.toPx())
        val color = style.guide ?: frame.palette.ink
        for (guide in 0 until style.guides) {
            val alpha = style.guideAlpha * (1f - guide.toFloat() / style.guides)
            drawCircle(color = color, radius = spacing * (guide + 1), center = center, alpha = alpha, style = stroke)
        }
    }

    override fun DrawScope.drawFrame(frame: ArtFrame) {
        val seed = frame.seed.derive(style.salt).value
        val center = rippleOriginOf(base = originOf(), pointer = frame.inputs.pointer, follow = style.pointerFollow)
        val reach = style.reach * hypot(x = size.width / 2f, y = size.height / 2f)
        val count = (style.rings * frame.quality.density).roundToInt()
        val colors = frame.palette.colorsOr(style.colors)
        val shift = pickOf(size = colors.size, x = 0, seed = seed + COLOR_SALT)
        val phase = frame.time.phase(style.period)
        val offset = unitOf(x = 0, seed = seed)
        val stroke = Stroke(width = style.lineWidth.toPx())
        for (ring in 0 until count) {
            val fraction = rippleFractionOf(phase = phase, index = ring, count = count, offset = offset)
            val radius = style.easing.transform(fraction) * reach
            if (radius <= 0f) continue
            val fade = 1f - fraction
            val color = colors.cyclic(index = ring + shift, fallback = frame.palette.ink)
            if (style.fillAlpha > 0f) {
                drawCircle(color = color, radius = radius, center = center, alpha = style.fillAlpha * fade)
            }
            drawCircle(color = color, radius = radius, center = center, alpha = style.ringAlpha * fade, style = stroke)
        }
    }

    private fun DrawScope.originOf(): Offset = Offset(x = size.width * style.originX, y = size.height * style.originY)
}
