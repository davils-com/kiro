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

package com.davils.kiro.ui.motion.shader.mesh

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.davils.kiro.ui.motion.art.ArtFrame
import com.davils.kiro.ui.motion.art.ArtRenderer
import com.davils.kiro.ui.motion.shader.tintsOf
import kotlin.math.sqrt

private const val MIDDLE: Float = 0.45f
private const val MIDDLE_ALPHA: Float = 0.4f
private const val REACH: Float = 1.8f

internal class MeshGradientFallback(private val style: MeshGradientArtStyle) : ArtRenderer {
    private val anchors = MeshAnchors()

    override fun DrawScope.drawFrame(frame: ArtFrame) {
        if (size.minDimension <= 0f) return
        drawRect(color = style.background ?: frame.palette.background)
        val count = anchors.placeFor(style = style, frame = frame, size = size)
        val colors = frame.palette.tintsOf(style.colors)
        val softness = style.softness.coerceAtLeast(minimumValue = Float.MIN_VALUE)
        val reach = REACH * style.radius * size.maxDimension / sqrt(softness)
        val alpha = style.intensity.coerceIn(minimumValue = 0f, maximumValue = 1f)
        for (index in 0 until count) {
            val tint = colors[index % colors.size]
            val center = Offset(x = anchors.x[index] * size.width, y = anchors.y[index] * size.height)
            val radius = (reach * anchors.scale[index]).coerceAtLeast(minimumValue = 1f)
            val brush = Brush.radialGradient(
                colorStops = arrayOf(
                    0f to tint.copy(alpha = tint.alpha * alpha),
                    MIDDLE to tint.copy(alpha = tint.alpha * alpha * MIDDLE_ALPHA),
                    1f to tint.copy(alpha = 0f),
                ),
                center = center,
                radius = radius,
            )
            drawCircle(brush = brush, radius = radius, center = center)
        }
    }
}
