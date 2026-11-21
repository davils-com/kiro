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

internal class RecordingSink : UniformSink {
    val calls: MutableList<String> = mutableListOf()

    override fun float(name: String, x: Float) {
        calls += "$name=$x"
    }

    override fun float2(name: String, x: Float, y: Float) {
        calls += "$name=$x,$y"
    }

    override fun float3(name: String, x: Float, y: Float, z: Float) {
        calls += "$name=$x,$y,$z"
    }

    override fun float4(name: String, x: Float, y: Float, z: Float, w: Float) {
        calls += "$name=$x,$y,$z,$w"
    }

    override fun floats(name: String, values: FloatArray) {
        calls += "$name=${values.joinToString(separator = ",")}"
    }
}
