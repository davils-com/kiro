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

package com.davils.kiro.ui.motion.art.grid

import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.Density
import com.davils.kiro.ui.motion.fixture.textMeasurer
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs

private const val CAPACITY: Int = 4
private const val DENSE: Float = 2f
private const val LARGER: Float = 1.5f
private const val MEASURED_THRICE: Int = 3

class GlyphCacheTest : FunSpec({
    val measurer = textMeasurer()
    val measure: (Int) -> TextLayoutResult = { index -> measurer.measure(text = index.toString()) }

    test("measures a glyph once while the density stays the same") {
        val cache = GlyphCache(capacity = CAPACITY)
        val first = cache.glyph(index = 1, density = Density(1f), measure = measure)

        cache.glyph(index = 1, density = Density(1f), measure = measure) shouldBeSameInstanceAs first
        cache.capacity shouldBe CAPACITY
        cache.measured shouldBe 1
    }

    test("measures again when the density or the font scale changes") {
        val cache = GlyphCache(capacity = CAPACITY)
        cache.glyph(index = 0, density = Density(1f), measure = measure)
        cache.glyph(index = 0, density = Density(DENSE), measure = measure)
        cache.glyph(index = 0, density = Density(density = DENSE, fontScale = LARGER), measure = measure)

        cache.measured shouldBe MEASURED_THRICE
    }

    test("holds no glyphs without a capacity") {
        GlyphCache(capacity = -1).capacity shouldBe 0
    }
})
