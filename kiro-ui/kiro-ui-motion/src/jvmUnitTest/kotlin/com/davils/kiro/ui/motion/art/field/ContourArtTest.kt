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
import com.davils.kiro.ui.motion.fixture.extremeChecks
import com.davils.kiro.ui.motion.fixture.print
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldStartWith

private const val FEW_LEVELS: Int = 5

@OptIn(ExperimentalTestApi::class)
class ContourArtTest : FunSpec({
    val theme = Theme.light()
    val style = defaultContourArtStyle(theme)
    val base = ContourArt().print()

    artChecks(
        ArtCase(
            name = "contour",
            art = ContourArt(),
            changed = ContourArt(style = style.copy(levels = FEW_LEVELS)),
            recolored = ContourArt(style = style.copy(colors = listOf(Color.Red, Color.Blue))),
        ),
    )

    test("draws without bands and without index lines") {
        ContourArt(style.copy(bandAlpha = 0f)).print() shouldNotBe base
        ContourArt(style.copy(indexEvery = 0)).print() shouldNotBe base
    }

    test("takes its style from the components of the theme") {
        val custom = theme.copy(
            components = theme.components.with(ContourArtStyleKey) { _, _ ->
                ContourArtStyles { current -> defaultContourArtStyle(current).copy(levels = FEW_LEVELS) }
            },
        )

        ContourArt().print(Render(theme = custom)) shouldNotBe base
    }

    test("resolves its style in a composition") {
        var resolved: ContourArtStyle? = null
        runComposeUiTest {
            setContent { ThemeProvider(theme = theme) { resolved = contourArtStyle() } }
        }

        resolved shouldBe style
    }

    test("is a value") {
        val art = ContourArt(style = style)

        art shouldBe ContourArt(style = style)
        art.hashCode() shouldBe ContourArt(style = style).hashCode()
        art.copy() shouldBe art
        art.copy(style = null) shouldBe ContourArt()
        art.isStatic shouldBe false
        art.toString() shouldStartWith "ContourArt(style=ContourArtStyle(colors=null"
        ContourArt().toString() shouldBe "ContourArt(style=null)"
        style.copy() shouldBe style
        style.copy().hashCode() shouldBe style.hashCode()
        style.copy(levels = FEW_LEVELS) shouldNotBe style
    }

    extremeChecks(name = "ContourArt", style = style) { variant -> ContourArt(style = variant) }
})
