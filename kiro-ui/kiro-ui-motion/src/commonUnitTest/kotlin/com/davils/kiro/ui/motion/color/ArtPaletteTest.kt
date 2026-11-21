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
import com.davils.kiro.ui.core.style.ComponentStyles
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldStartWith

private const val MIN_ACCENTS: Int = 5
private const val RAMP_STEPS: Int = 3
private const val WHEEL_COUNT: Int = 6

private val presets: List<ArtPalettes> = listOf(
    ArtPalettes.Refined,
    ArtPalettes.Expressive,
    ArtPalettes.Monochrome,
    ArtPalettes.Vivid,
    ArtPalettes.Pastel,
    ArtPalettes.Duotone,
    ArtPalettes.Night,
)

class ArtPaletteTest : FunSpec({
    test("every preset gives several accents in both themes") {
        listOf(Theme.light(), Theme.dark()).forEach { theme ->
            presets.forEach { preset ->
                val palette = preset.resolve(theme)
                (palette.accents.size >= MIN_ACCENTS) shouldBe true
                palette.background shouldNotBe palette.ink
            }
        }
    }

    test("picks accents around its list and falls back to the ink") {
        val palette = ArtPalettes.Refined.resolve(Theme.light())

        palette.accent(index = palette.accents.size) shouldBe palette.accents.first()
        palette.accent(index = -1) shouldBe palette.accents.last()
        palette.copy(accents = emptyList()).accent(index = 3) shouldBe palette.ink
    }

    test("follows the palette registered in the theme") {
        val light = Theme.light()
        val theme = light.copy(components = ComponentStyles.Default.with(ArtPaletteKey) { _, _ -> ArtPalettes.Night })

        artPaletteOf(theme) shouldBe ArtPalettes.Night.resolve(theme)
        artPaletteOf(light) shouldBe ArtPalettes.Refined.resolve(light)
    }

    test("builds ramps and wheels") {
        rampOf(start = Color.Black, end = Color.White, steps = RAMP_STEPS) shouldHaveSize RAMP_STEPS
        wheelOf(hue = 300.0, lightness = 0.7, chroma = 0.2, count = WHEEL_COUNT) shouldHaveSize WHEEL_COUNT
        shouldThrow<IllegalArgumentException> { rampOf(start = Color.Black, end = Color.White, steps = 1) }
    }

    test("is a value") {
        val palette = ArtPalettes.Refined.resolve(Theme.light())

        palette.copy() shouldBe palette
        palette.copy().hashCode() shouldBe palette.hashCode()
        palette.copy(ink = Color.Red) shouldNotBe palette
        palette.toString() shouldStartWith "ArtPalette("
    }
})
