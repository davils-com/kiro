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
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val HALF: Float = 0.5f
private const val INDEX: Int = 3

private val palette = ArtPalette(
    background = Color.White,
    ink = Color.Black,
    accents = listOf(Color.Red, Color.Green),
    highlight = Color.White,
    shade = Color.Black,
)

class ColorPicksTest : FunSpec({
    test("takes colors around the list and falls back without colors") {
        val colors = listOf(Color.Red, Color.Green)

        colors.cyclic(index = INDEX, fallback = Color.Black) shouldBe Color.Green
        colors.cyclic(index = -1, fallback = Color.Black) shouldBe Color.Green
        emptyList<Color>().cyclic(index = 0, fallback = Color.Black) shouldBe Color.Black
    }

    test("bands the colors over a fraction and falls back without colors") {
        val colors = listOf(Color.Red, Color.Blue)

        colors.band(fraction = 0f, fallback = Color.Black) shouldBe Color.Red
        colors.band(fraction = 1f, fallback = Color.Black) shouldBe Color.Blue
        colors.band(fraction = 2f, fallback = Color.Black) shouldBe Color.Blue
        emptyList<Color>().band(fraction = HALF, fallback = Color.Black) shouldBe Color.Black
    }

    test("takes the accents of the palette unless colors are given, and the ink when none are left") {
        palette.colorsOr(colors = null) shouldBe listOf(Color.Red, Color.Green)
        palette.colorsOr(colors = listOf(Color.Blue)) shouldBe listOf(Color.Blue)
        palette.colorsOr(colors = emptyList()) shouldBe listOf(Color.Black)
    }

    test("picks the value of the tone of the theme") {
        Theme.light().darkAware(light = 1, dark = 2) shouldBe 1
        Theme.dark().darkAware(light = 1, dark = 2) shouldBe 2
    }
})
