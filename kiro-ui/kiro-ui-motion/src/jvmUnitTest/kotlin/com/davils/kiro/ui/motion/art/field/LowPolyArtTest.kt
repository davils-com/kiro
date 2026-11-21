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
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.ThemeProvider
import com.davils.kiro.ui.motion.fixture.ArtCase
import com.davils.kiro.ui.motion.fixture.Render
import com.davils.kiro.ui.motion.fixture.ValueCase
import com.davils.kiro.ui.motion.fixture.artChecks
import com.davils.kiro.ui.motion.fixture.colorCount
import com.davils.kiro.ui.motion.fixture.drawEmpty
import com.davils.kiro.ui.motion.fixture.extremeChecks
import com.davils.kiro.ui.motion.fixture.print
import com.davils.kiro.ui.motion.fixture.render
import com.davils.kiro.ui.motion.fixture.valueChecks
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

private const val FEW: Int = 8

@OptIn(ExperimentalTestApi::class)
class LowPolyArtTest : FunSpec({
    val theme = Theme.light()
    val style = defaultLowPolyArtStyle(theme)
    val base = LowPolyArt().print()

    artChecks(
        ArtCase(
            name = "lowpoly",
            art = LowPolyArt(),
            changed = LowPolyArt(style = style.copy(cell = 10.dp, edgeAlpha = 0f)),
            recolored = LowPolyArt(
                style = style.copy(
                    colors = listOf(Color.Red, Color.Blue),
                    background = Color.Black,
                    edge = Color.Green,
                ),
            ),
        ),
    )

    valueChecks(
        ValueCase(
            name = "LowPolyArt",
            value = LowPolyArt(style),
            copy = LowPolyArt(style).copy(),
            changed = LowPolyArt(),
        ),
    )
    valueChecks(
        ValueCase(
            name = "LowPolyArtStyle",
            value = style,
            copy = style.copy(),
            changed = style.copy(relief = 0f),
        ),
    )

    test("draws a fine mesh in several batches") {
        val image = LowPolyArt(style.copy(cell = 1.dp)).render()

        image.colorCount() shouldBeGreaterThan FEW
    }

    test("shades the facets by the light above them") {
        LowPolyArt(style.copy(contrast = 0f)).print() shouldNotBe base
    }

    test("is never static and draws nothing into an empty area") {
        LowPolyArt().isStatic shouldBe false
        LowPolyArt().toString() shouldBe "LowPolyArt(style=null)"
        LowPolyArt().drawEmpty()
    }

    test("takes its style from the components of the theme") {
        val custom = theme.copy(
            components = theme.components.with(LowPolyArtStyleKey) { _, _ ->
                LowPolyArtStyles { current ->
                    defaultLowPolyArtStyle(current).copy(cell = 10.dp, edgeAlpha = 0f)
                }
            },
        )

        LowPolyArt().print(Render(theme = custom)) shouldNotBe base
        LowPolyArtStyles.Default.resolve(theme) shouldBe style
    }

    test("resolves its style in a composition") {
        var resolved: LowPolyArtStyle? = null
        runComposeUiTest {
            setContent { ThemeProvider(theme = theme) { resolved = lowPolyArtStyle() } }
            waitForIdle()
        }

        resolved shouldBe style
    }

    extremeChecks(name = "LowPolyArt", style = style) { variant -> LowPolyArt(style = variant) }
})
