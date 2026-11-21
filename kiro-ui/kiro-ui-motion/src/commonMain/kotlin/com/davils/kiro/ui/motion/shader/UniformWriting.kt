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

package com.davils.kiro.ui.motion.shader

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.davils.kiro.ui.motion.art.ArtFrame

internal fun List<Uniform>.bindingOf(): UniformBinding = UniformBinding { sink, frame, scope ->
    for (uniform in this) uniform.write(sink = sink, frame = frame, scope = scope)
}

internal fun Uniform.write(sink: UniformSink, frame: ArtFrame, scope: DrawScope) {
    when (this) {
        is FloatUniform -> sink.float(name = name, x = value)
        is Vec2Uniform -> sink.float2(name = name, x = x, y = y)
        is ColorUniform -> sink.color(name = name, color = color)
        is TimeUniform -> sink.float(name = name, x = frame.time.loopOf(period))
        is SizeUniform -> sink.float2(name = name, x = scope.size.width, y = scope.size.height)
        is PointerUniform -> sink.pointer(name = name, pointer = frame.inputs.pointer)
        is ProgressUniform -> sink.float(name = name, x = frame.inputs.progress ?: missing)
    }
}

internal fun UniformSink.pointer(name: String, pointer: Offset?) {
    if (pointer == null) return float3(name = name, x = 0f, y = 0f, z = 0f)
    float3(name = name, x = pointer.x, y = pointer.y, z = 1f)
}
