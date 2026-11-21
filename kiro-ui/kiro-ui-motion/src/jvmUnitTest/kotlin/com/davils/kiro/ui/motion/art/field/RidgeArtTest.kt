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
import com.davils.kiro.ui.motion.fixture.print
import com.davils.kiro.ui.motion.fixture.render
import com.davils.kiro.ui.motion.fixture.save
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldStartWith

private const val FEW_LINES: Int = 9
private const val BELOW: Float = 3f
private const val WIDE_WIDTH: Int = 1200
private const val WIDE_HEIGHT: Int = 600

@OptIn(ExperimentalTestApi::class)
class RidgeArtTest : FunSpec({
    val theme = Theme.light()
    val style = defaultRidgeArtStyle(theme)
    val base = RidgeArt().print()

    artChecks(
        ArtCase(
            name = "ridge",
            art = RidgeArt(),
            changed = RidgeArt(style = style.copy(lines = FEW_LINES)),
            recolored = RidgeArt(style = style.copy(colors = listOf(Color.Red, Color.Blue), background = Color.Black)),
        ),
    )

    test("runs its colors in every direction") {
        val prints = RidgeGradient.entries.map { gradient ->
            RidgeArt(style.copy(gradient = gradient)).print()
        }

        prints.toSet() shouldHaveSize RidgeGradient.entries.size
    }

    test("lets the lines behind show without a fill") {
        RidgeArt(style.copy(hasFill = false)).print() shouldNotBe base
    }

    test("draws a single line and skips lines below the area") {
        val single = RidgeArt(style.copy(lines = 1))

        single.render().colorCount() shouldBeGreaterThan 2
        RidgeArt(style.copy(bottom = BELOW)).print() shouldNotBe base
    }

    test("draws smooth lines in a wide band") {
        val wide = RidgeArt().render(Render(width = WIDE_WIDTH, height = WIDE_HEIGHT))
        wide.save(name = "ridge-wide")

        wide.colorCount() shouldBeGreaterThan FEW_LINES
    }

    test("takes its style from the components of the theme") {
        val custom = theme.copy(
            components = theme.components.with(RidgeArtStyleKey) { _, _ ->
                RidgeArtStyles { current -> defaultRidgeArtStyle(current).copy(lines = FEW_LINES) }
            },
        )

        RidgeArt().print(Render(theme = custom)) shouldNotBe base
    }

    test("resolves its style in a composition") {
        var resolved: RidgeArtStyle? = null
        runComposeUiTest {
            setContent { ThemeProvider(theme = theme) { resolved = ridgeArtStyle() } }
        }

        resolved shouldBe style
    }

    test("is a value") {
        val art = RidgeArt(style = style)

        art shouldBe RidgeArt(style = style)
        art.hashCode() shouldBe RidgeArt(style = style).hashCode()
        art.copy() shouldBe art
        art.copy(style = null) shouldBe RidgeArt()
        art.isStatic shouldBe false
        art.toString() shouldStartWith "RidgeArt(style=RidgeArtStyle(colors=null, background=null"
        RidgeArt().toString() shouldBe "RidgeArt(style=null)"
        style.copy() shouldBe style
        style.copy().hashCode() shouldBe style.hashCode()
        style.copy(lines = FEW_LINES) shouldNotBe style
        style.copy(colors = listOf(Color.Red)).toString() shouldStartWith "RidgeArtStyle(colors=[Color("
    }

    extremeChecks(name = "RidgeArt", style = style) { variant -> RidgeArt(style = variant) }
})
