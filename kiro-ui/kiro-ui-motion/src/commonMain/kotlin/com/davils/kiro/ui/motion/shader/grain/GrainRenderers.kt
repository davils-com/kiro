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

import androidx.compose.ui.graphics.Color
import com.davils.kiro.ui.motion.art.ArtFrame
import com.davils.kiro.ui.motion.art.ArtRenderer
import com.davils.kiro.ui.motion.noise.unitOf
import com.davils.kiro.ui.motion.shader.UniformBinding
import com.davils.kiro.ui.motion.shader.color
import com.davils.kiro.ui.motion.shader.shaderRendererOf
import kotlin.math.floor

private const val SPAN: Float = 4096f
private const val CYCLE: Int = 997

internal fun grainRendererOf(style: GrainArtStyle): ArtRenderer = shaderRendererOf(
    source = GRAIN_SOURCE,
    backend = style.backend,
    binding = grainBinding(style = style),
    fallback = { GrainFallback(style = style) },
)

internal fun GrainArtStyle.patternOf(frame: ArtFrame): Int {
    val period = refresh?.takeIf { duration -> duration.isPositive() } ?: return 0
    val turns = floor(frame.time.phase(period)).toInt()
    return turns.mod(CYCLE)
}

internal fun GrainArtStyle.shadeOf(frame: ArtFrame): Color = shade ?: frame.palette.shade

internal fun GrainArtStyle.toneOf(frame: ArtFrame): Color = tone ?: frame.palette.ink

private fun grainBinding(style: GrainArtStyle): UniformBinding = UniformBinding { sink, frame, scope ->
    val pattern = style.patternOf(frame)
    val seed = frame.seed.value
    sink.float(name = "cell", x = with(scope) { style.cell.toPx() })
    val x = unitOf(x = pattern, seed = seed) * SPAN
    val y = unitOf(x = pattern, y = 1, seed = seed) * SPAN
    sink.float2(name = "offset", x = x, y = y)
    sink.float(name = "amount", x = style.amount)
    sink.float(name = "balance", x = style.balance)
    sink.color(name = "shade", color = style.shadeOf(frame))
    sink.color(name = "tone", color = style.toneOf(frame))
}
