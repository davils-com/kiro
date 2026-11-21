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

import androidx.compose.ui.graphics.drawscope.DrawScope
import com.davils.kiro.ui.motion.art.ArtFrame
import com.davils.kiro.ui.motion.art.ArtRenderer

internal class ShaderRenderer(private val source: String, private val binding: UniformBinding) : ArtRenderer {
    private val uniforms: UniformBuffer = UniformBuffer()

    override fun DrawScope.drawFrame(frame: ArtFrame) {
        if (size.width <= 0f || size.height <= 0f) return
        uniforms.clear()
        binding.bind(sink = uniforms, frame = frame, scope = this)
        drawShader(source = source, uniforms = uniforms)
    }
}
