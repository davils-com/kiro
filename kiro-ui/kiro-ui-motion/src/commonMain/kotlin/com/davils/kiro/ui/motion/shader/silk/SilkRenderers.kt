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

import androidx.compose.ui.graphics.Color
import com.davils.kiro.ui.motion.art.ArtFrame
import com.davils.kiro.ui.motion.art.ArtRenderer
import com.davils.kiro.ui.motion.noise.rangeOf
import com.davils.kiro.ui.motion.shader.UniformBinding
import com.davils.kiro.ui.motion.shader.color
import com.davils.kiro.ui.motion.shader.loopOf
import com.davils.kiro.ui.motion.shader.pointer
import com.davils.kiro.ui.motion.shader.shaderRendererOf
import kotlin.math.PI

private const val DEGREES: Float = 180f
private const val SEED_TURN: Float = 12f
private const val SHIFT: Float = PI.toFloat()
private const val SALT_ANGLE: Int = 19
private const val SALT_SHIFT: Int = 23

internal fun silkRendererOf(style: SilkArtStyle): ArtRenderer = shaderRendererOf(
    source = SILK_SOURCE,
    backend = style.backend,
    binding = silkBinding(style = style),
    fallback = { SilkFallback(style = style) },
)

internal fun SilkArtStyle.groundOf(frame: ArtFrame): Color = background ?: frame.palette.background

internal fun SilkArtStyle.colorOf(frame: ArtFrame): Color = color ?: frame.palette.accent(index = 1)

internal fun SilkArtStyle.shadowOf(frame: ArtFrame): Color = shadow ?: frame.palette.shade

internal fun SilkArtStyle.sheenOf(frame: ArtFrame): Color = sheen ?: frame.palette.highlight

internal fun SilkArtStyle.radiansOf(frame: ArtFrame): Float {
    val turn = rangeOf(from = -SEED_TURN, to = SEED_TURN, x = 0, seed = frame.seed.value + SALT_ANGLE)
    return (angle + turn) / DEGREES * PI.toFloat()
}

private fun silkBinding(style: SilkArtStyle): UniformBinding = UniformBinding { sink, frame, scope ->
    val size = scope.size
    sink.float2(name = "size", x = size.width, y = size.height)
    sink.float(name = "time", x = frame.time.loopOf(style.period))
    sink.color(name = "ground", color = style.groundOf(frame))
    sink.color(name = "tint", color = style.colorOf(frame))
    sink.color(name = "shadow", color = style.shadowOf(frame).copy(alpha = style.shadowAlpha))
    sink.color(name = "sheen", color = style.sheenOf(frame).copy(alpha = style.sheenAlpha))
    sink.float(name = "intensity", x = style.intensity)
    sink.float(name = "scale", x = style.scale * size.maxDimension)
    sink.float(name = "warp", x = style.warp)
    sink.float(name = "relief", x = style.relief)
    sink.float(name = "shine", x = style.shine)
    sink.float(name = "angle", x = style.radiansOf(frame))
    sink.float(name = "shift", x = rangeOf(from = -SHIFT, to = SHIFT, x = 0, seed = frame.seed.value + SALT_SHIFT))
    sink.pointer(name = "pointer", pointer = frame.inputs.pointer)
    sink.float(name = "pointerLight", x = style.pointerLight)
    sink.float(name = "dither", x = style.dither)
}
