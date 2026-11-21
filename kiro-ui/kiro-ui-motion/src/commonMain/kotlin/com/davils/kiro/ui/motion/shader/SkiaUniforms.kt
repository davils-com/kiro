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

@file:OptIn(DavilsInternal::class)

package com.davils.kiro.ui.motion.shader

import com.davils.kore.core.annotation.DavilsInternal
import com.davils.kore.core.annotation.DavilsPlatformBinding
import org.jetbrains.skia.RuntimeShaderBuilder

@DavilsPlatformBinding
internal class SkiaUniforms(private val builder: RuntimeShaderBuilder) : UniformSink {
    override fun float(name: String, x: Float) {
        builder.uniform(name = name, value = x)
    }

    override fun float2(name: String, x: Float, y: Float) {
        builder.uniform(name = name, value1 = x, value2 = y)
    }

    override fun float3(name: String, x: Float, y: Float, z: Float) {
        builder.uniform(name = name, value1 = x, value2 = y, value3 = z)
    }

    override fun float4(name: String, x: Float, y: Float, z: Float, w: Float) {
        builder.uniform(name = name, value1 = x, value2 = y, value3 = z, value4 = w)
    }

    override fun floats(name: String, values: FloatArray) {
        builder.uniform(name = name, value = values)
    }
}
