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

private const val INITIAL: Int = 16
private const val SCALAR: Int = 1
private const val VEC2: Int = 2
private const val VEC3: Int = 3
private const val VEC4: Int = 4

internal class UniformBuffer : UniformSink {
    private val names: MutableList<String> = mutableListOf()
    private var lengths: IntArray = IntArray(INITIAL)
    private var values: FloatArray = FloatArray(INITIAL)
    private var used: Int = 0

    val count: Int get() = names.size

    fun clear() {
        names.clear()
        used = 0
    }

    override fun float(name: String, x: Float) {
        val at = reserve(name = name, length = SCALAR)
        values[at] = x
    }

    override fun float2(name: String, x: Float, y: Float) {
        val at = reserve(name = name, length = VEC2)
        values[at] = x
        values[at + 1] = y
    }

    override fun float3(name: String, x: Float, y: Float, z: Float) {
        val at = reserve(name = name, length = VEC3)
        values[at] = x
        values[at + 1] = y
        values[at + 2] = z
    }

    override fun float4(name: String, x: Float, y: Float, z: Float, w: Float) {
        val at = reserve(name = name, length = VEC4)
        values[at] = x
        values[at + 1] = y
        values[at + 2] = z
        values[at + VEC3] = w
    }

    override fun floats(name: String, values: FloatArray) {
        val at = reserve(name = name, length = values.size)
        values.copyInto(destination = this.values, destinationOffset = at)
    }

    fun write(sink: UniformSink) {
        var offset = 0
        for (index in 0 until count) {
            val length = lengths[index]
            sink.writeOne(name = names[index], offset = offset, length = length)
            offset += length
        }
    }

    private fun UniformSink.writeOne(name: String, offset: Int, length: Int) {
        val v = values
        when (length) {
            SCALAR -> float(name = name, x = v[offset])
            VEC2 -> float2(name = name, x = v[offset], y = v[offset + 1])
            VEC3 -> float3(name = name, x = v[offset], y = v[offset + 1], z = v[offset + 2])
            VEC4 -> float4(name = name, x = v[offset], y = v[offset + 1], z = v[offset + 2], w = v[offset + VEC3])
        }
        if (length > VEC4 || length == 0) {
            floats(name = name, values = v.copyOfRange(fromIndex = offset, toIndex = offset + length))
        }
    }

    private fun reserve(name: String, length: Int): Int {
        if (count == lengths.size) lengths = lengths.copyOf(newSize = lengths.size * 2)
        if (used + length > values.size) values = values.copyOf(newSize = maxOf(a = values.size * 2, b = used + length))
        lengths[count] = length
        names += name
        val at = used
        used += length
        return at
    }
}
