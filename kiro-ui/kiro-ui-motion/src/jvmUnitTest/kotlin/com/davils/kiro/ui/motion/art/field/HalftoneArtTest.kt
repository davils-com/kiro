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

package com.davils.kiro.ui.motion.art.field

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runComposeUiTest
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.ThemeProvider
import com.davils.kiro.ui.motion.fixture.ArtCase
import com.davils.kiro.ui.motion.fixture.Render
import com.davils.kiro.ui.motion.fixture.artChecks
import com.davils.kiro.ui.motion.fixture.colorCount
import com.davils.kiro.ui.motion.fixture.extremeChecks
import com.davils.kiro.ui.motion.fixture.fingerprint
import com.davils.kiro.ui.motion.fixture.print
import com.davils.kiro.ui.motion.fixture.render
import com.davils.kiro.ui.motion.input.ArtInputs
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldStartWith

private const val POINTER_X: Float = 160f
private const val POINTER_Y: Float = 100f
private const val ANGLE: Float = 40f
private const val FEW: Int = 8

@OptIn(ExperimentalTestApi::class)
class HalftoneArtTest : FunSpec({
    val theme = Theme.light()
    val style = defaultHalftoneArtStyle(theme)
    val base = HalftoneArt().print()

    artChecks(
        ArtCase(
            name = "halftone",
            art = HalftoneArt(),
            changed = HalftoneArt(style = style.copy(angle = ANGLE)),
            recolored = HalftoneArt(style = style.copy(colors = listOf(Color.Red, Color.Blue))),
        ),
    )

    test("draws every pattern and shape") {
        val prints = HalftonePattern.entries.flatMap { pattern ->
            HalftoneShape.entries.map { shape ->
                val image = HalftoneArt(style.copy(pattern = pattern, shape = shape)).render()
                image.colorCount() shouldBeGreaterThan FEW
                image.fingerprint()
            }
        }

        prints.toSet() shouldHaveSize HalftonePattern.entries.size * HalftoneShape.entries.size
    }

    test("sends out a ripple from the pointer") {
        val inputs = ArtInputs.None.copy(pointer = Offset(x = POINTER_X, y = POINTER_Y))

        HalftoneArt().print(Render(inputs = inputs)) shouldNotBe base
    }

    test("falls back to a still wave without wavelengths and speeds") {
        val still = style.copy(wavelengths = emptyList(), speeds = emptyList(), waves = 0)

        HalftoneArt(still).print() shouldNotBe base
    }

    test("takes its style from the components of the theme") {
        val custom = theme.copy(
            components = theme.components.with(HalftoneArtStyleKey) { _, _ ->
                HalftoneArtStyles { current -> defaultHalftoneArtStyle(current).copy(angle = ANGLE) }
            },
        )

        HalftoneArt().print(Render(theme = custom)) shouldNotBe base
    }

    test("resolves its style in a composition") {
        var resolved: HalftoneArtStyle? = null
        runComposeUiTest {
            setContent { ThemeProvider(theme = theme) { resolved = halftoneArtStyle() } }
        }

        resolved shouldBe style
    }

    test("is a value") {
        val art = HalftoneArt(style = style)

        art shouldBe HalftoneArt(style = style)
        art.hashCode() shouldBe HalftoneArt(style = style).hashCode()
        art.copy() shouldBe art
        art.copy(style = null) shouldBe HalftoneArt()
        art.isStatic shouldBe false
        art.toString() shouldStartWith "HalftoneArt(style=HalftoneArtStyle(colors=null"
        HalftoneArt().toString() shouldBe "HalftoneArt(style=null)"
        style.copy() shouldBe style
        style.copy().hashCode() shouldBe style.hashCode()
        style.copy(angle = ANGLE) shouldNotBe style
    }

    extremeChecks(name = "HalftoneArt", style = style) { variant -> HalftoneArt(style = variant) }
})
