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
import androidx.compose.ui.geometry.Size
import com.davils.kiro.ui.motion.art.ArtFrame
import com.davils.kiro.ui.motion.art.ArtRenderer
import com.davils.kiro.ui.motion.shader.UniformBinding
import com.davils.kiro.ui.motion.shader.color
import com.davils.kiro.ui.motion.shader.putColor
import com.davils.kiro.ui.motion.shader.shaderRendererOf
import com.davils.kiro.ui.motion.shader.tintsOf

private const val COMPONENTS: Int = 3
private const val CHANNELS: Int = 4

internal fun meshGradientRendererOf(style: MeshGradientArtStyle): ArtRenderer = shaderRendererOf(
    source = MESH_GRADIENT_SOURCE,
    backend = style.backend,
    binding = meshGradientBinding(style = style),
    fallback = { MeshGradientFallback(style = style) },
)

internal fun MeshAnchors.placeFor(style: MeshGradientArtStyle, frame: ArtFrame, size: Size): Int {
    val count = anchorCountOf(style)
    val pointer = frame.inputs.pointer
    val relative = pointer?.let { offset -> Offset(x = offset.x / size.width, y = offset.y / size.height) }
    val phase = frame.time.phase(style.period)
    place(count = count, phase = phase, seed = frame.seed.value, style = style, pointer = relative)
    return count
}

private fun meshGradientBinding(style: MeshGradientArtStyle): UniformBinding {
    val anchors = MeshAnchors()
    val positions = FloatArray(MAX_ANCHORS * COMPONENTS)
    val tints = FloatArray(MAX_ANCHORS * CHANNELS)
    return UniformBinding { sink, frame, scope ->
        val size = scope.size
        val count = anchors.placeFor(style = style, frame = frame, size = size)
        val reach = style.radius * size.maxDimension
        val colors = frame.palette.tintsOf(style.colors)
        for (index in 0 until MAX_ANCHORS) {
            positions[index * COMPONENTS] = anchors.x[index] * size.width
            positions[index * COMPONENTS + 1] = anchors.y[index] * size.height
            positions[index * COMPONENTS + 2] = (reach * anchors.scale[index]).coerceAtLeast(minimumValue = 1f)
            tints.putColor(index = index, color = colors[index % colors.size])
        }
        sink.color(name = "ground", color = style.background ?: frame.palette.background)
        sink.float(name = "count", x = count.toFloat())
        sink.float(name = "softness", x = style.softness)
        sink.float(name = "intensity", x = style.intensity)
        sink.float(name = "dither", x = style.dither)
        sink.floats(name = "anchors", values = positions)
        sink.floats(name = "tints", values = tints)
    }
}
