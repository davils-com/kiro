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

package com.davils.kiro.ui.motion.color

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import com.davils.kiro.ui.core.color.mix
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs

private const val STEPS: Int = 4

private val purple = Color(red = 0.5f, green = 0.2f, blue = 0.9f)
private val violet = Color(red = 0.7f, green = 0.3f, blue = 0.9f)
private val lime = Color(red = 0.6f, green = 0.9f, blue = 0.1f)

class ColorGradientTest : FunSpec({
    test("returns the only color of a list of one") {
        gradientAt(colors = listOf(purple), fraction = 0.3f) shouldBe purple
    }

    test("starts and ends on the first and the last color") {
        val colors = listOf(purple, violet, lime)

        gradientAt(colors = colors, fraction = -1f) shouldBe blend(start = purple, end = violet, amount = 0f)
        gradientAt(colors = colors, fraction = 2f) shouldBe blend(start = violet, end = lime, amount = 1f)
    }

    test("blends near hues along the hue circle and far hues straight through the color space") {
        blend(start = purple, end = violet, amount = 0.5f) shouldBe purple.mix(other = violet, amount = 0.5f)
        blend(start = purple, end = lime, amount = 0.5f) shouldBe lerp(start = purple, stop = lime, fraction = 0.5f)
    }

    test("keeps its steps while the colors stay the same") {
        val cache = ColorGradient()
        val first = cache.of(colors = listOf(purple, lime), steps = STEPS)

        first shouldHaveSize STEPS
        cache.of(colors = listOf(purple, lime), steps = STEPS) shouldBeSameInstanceAs first
        val start = blend(start = purple, end = violet, amount = 0f)
        cache.of(colors = listOf(purple, violet), steps = STEPS).first() shouldBe start
        cache.of(colors = listOf(purple, violet), steps = 0) shouldContainExactly listOf(start)
    }
})
