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
import io.kotest.matchers.shouldBe

private const val A: String = "a"
private const val B: String = "b"
private const val CAPACITY: Int = 4

class EffectCacheTest : FunSpec({
    test("compiles a source once and reuses the effect") {
        val recorder = EffectRecorder()
        val cache = recorder.cache(capacity = CAPACITY)

        cache.effectOf(source = A) shouldBe A
        cache.effectOf(source = A) shouldBe A

        recorder.compiled shouldBe listOf(A)
        cache.contains(source = A) shouldBe true
        cache.size shouldBe 1
    }

    test("remembers a source that does not compile") {
        val recorder = EffectRecorder()
        val cache = recorder.cache(capacity = CAPACITY)

        cache.effectOf(source = BROKEN) shouldBe null
        cache.effectOf(source = BROKEN) shouldBe null

        recorder.compiled shouldBe listOf(BROKEN)
    }

    test("releases the least recently used effect beyond its capacity") {
        val recorder = EffectRecorder()
        val cache = recorder.cache(capacity = 2)

        cache.effectOf(source = A)
        cache.effectOf(source = B)
        cache.effectOf(source = A)
        cache.effectOf(source = BROKEN)
        cache.effectOf(source = "c")
        cache.effectOf(source = "d")

        recorder.released shouldBe listOf(B, A)
        cache.size shouldBe 2
        cache.contains(source = A) shouldBe false
        cache.contains(source = BROKEN) shouldBe false
        cache.contains(source = "d") shouldBe true
    }

    test("keeps at least one effect and releases everything when cleared") {
        val recorder = EffectRecorder()
        val cache = recorder.cache(capacity = 0)

        cache.effectOf(source = A)
        cache.effectOf(source = B)
        cache.size shouldBe 1
        cache.clear()

        recorder.released shouldBe listOf(A, B)
        cache.size shouldBe 0
    }
})
