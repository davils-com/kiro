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

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

private const val LAST: Float = 4f
private const val MANY: Int = 40
private const val BIG: Int = 200

private val six: FloatArray = FloatArray(size = 6) { index -> index + 1f }

private fun UniformBuffer.fill(last: Float = LAST): UniformBuffer = apply {
    float(name = "a", x = 1f)
    float2(name = "b", x = 1f, y = 2f)
    float3(name = "c", x = 1f, y = 2f, z = last - 1f)
    float4(name = "d", x = 1f, y = 2f, z = last - 1f, w = last)
    floats(name = "e", values = six)
}

class UniformBufferTest : FunSpec({
    test("writes every value with its arity in order") {
        val sink = RecordingSink()
        val buffer = UniformBuffer().fill()

        buffer.write(sink = sink)

        buffer.count shouldBe six.size - 1
        sink.calls shouldBe listOf(
            "a=1.0",
            "b=1.0,2.0",
            "c=1.0,2.0,3.0",
            "d=1.0,2.0,3.0,4.0",
            "e=1.0,2.0,3.0,4.0,5.0,6.0",
        )
    }

    test("grows beyond its first capacity and clears") {
        val buffer = UniformBuffer()
        repeat(times = MANY) { index -> buffer.float4(name = "u$index", x = 1f, y = 2f, z = LAST, w = index.toFloat()) }
        buffer.floats(name = "big", values = FloatArray(size = BIG) { index -> index.toFloat() })
        val sink = RecordingSink()

        buffer.write(sink = sink)
        sink.calls shouldHaveSize MANY + 1
        sink.calls[MANY - 1] shouldBe "u39=1.0,2.0,4.0,39.0"

        buffer.clear()
        buffer.count shouldBe 0
        val empty = RecordingSink()
        buffer.write(sink = empty)
        empty.calls shouldBe emptyList()
    }
})
